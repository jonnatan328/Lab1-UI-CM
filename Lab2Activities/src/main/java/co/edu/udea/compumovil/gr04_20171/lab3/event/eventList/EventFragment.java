package co.edu.udea.compumovil.gr04_20171.lab3.event.eventList;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import co.edu.udea.compumovil.gr04_20171.lab3.R;
import co.edu.udea.compumovil.gr04_20171.lab3.event.data.Event;
import co.edu.udea.compumovil.gr04_20171.lab3.event.data.EventContract;
import co.edu.udea.compumovil.gr04_20171.lab3.event.data.EventDbHelper;
import co.edu.udea.compumovil.gr04_20171.lab3.event.detailEvent.DetailEventActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link EventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventFragment extends Fragment {


    public  ArrayList<Event> events;
    private RecyclerView recyclerView;
    private EventDbHelper eventDbHelper;




//    private OnFragmentInteractionListener mListener;

    public EventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventFragment newInstance() {
        EventFragment fragment = new EventFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);

        eventDbHelper = new EventDbHelper(getActivity());

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        try {
            initializeData();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        initializeAdapter();
        getActivity().setTitle("Eventos");
        return view;
    }

    public void initializeData() throws ParseException {
        Cursor cursor = eventDbHelper.getAllEvents();
        int sizeCursor = cursor.getCount();
        events = new ArrayList<Event>(sizeCursor);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Event eventDb = null;
            String pictureUri = cursor.getString(cursor.getColumnIndex(EventContract.EventEntry.PICTURE_URI));
            String name = cursor.getString(cursor.getColumnIndex(EventContract.EventEntry.NAME));
            String description = cursor.getString(cursor.getColumnIndex(EventContract.EventEntry.DESCRIPTION));
            int rating = cursor.getInt(cursor.getColumnIndex(EventContract.EventEntry.RATING));
            String personInCharge = cursor.getString(cursor.getColumnIndex(EventContract.EventEntry.PERSON_IN_CHARGE));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            date = dateFormat.parse(cursor.getString(cursor.getColumnIndex(EventContract.EventEntry.DATE)));
            String location = cursor.getString(cursor.getColumnIndex(EventContract.EventEntry.LOCATION));
            eventDb = new Event(pictureUri, name, description, rating, personInCharge, date, location);

            //add the item
            events.add(eventDb);
            cursor.moveToNext();
        }
        cursor.close();
    }

    public void initializeAdapter() {
        final EventListAdapter eventListAdapter = new EventListAdapter(events);
        eventListAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("DemoRecView", "Pulsado el elemento " + recyclerView.getChildAdapterPosition(v));
                int currentId = recyclerView.getChildAdapterPosition(v) + 1;
                showDetailScreen(currentId);
            }
        });
        recyclerView.setAdapter(eventListAdapter);

    }

    private void showDetailScreen(int currentId) {
        Intent intent = new Intent(getActivity(), DetailEventActivity.class);
        Log.d("id antes de enviar", String.valueOf(currentId));
        intent.putExtra("idEvent", String.valueOf(currentId));
        intent.putExtra("nameEvent", "Besaton");
        startActivity(intent);
    }

    public void update() throws ParseException {
        initializeData();
        initializeAdapter();
    }

    /*
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
*/


    /*
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
*/
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    /*public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/


}
