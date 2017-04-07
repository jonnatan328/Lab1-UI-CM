package co.edu.udea.compumovil.gr04_20171.lab3.event.addEdit;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import co.edu.udea.compumovil.gr04_20171.lab3.R;
import co.edu.udea.compumovil.gr04_20171.lab3.event.data.Event;
import co.edu.udea.compumovil.gr04_20171.lab3.event.data.EventDbHelper;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddEventFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEventFragment extends Fragment {
    private EventDbHelper mEventDbHelper;

    private Button mSaveButton;
    private ImageView mEventImage;
    private TextInputEditText mEventName;
    private TextInputEditText mEventPersonInCharge;
    private TextInputEditText mEventDate;
    private TextInputEditText mEventLocation;
    private TextInputEditText mEventDescription;
    private TextInputLayout mNameLabel;
    private TextInputLayout mPersonInChargeLabel;
    private TextInputLayout mDateLabel;
    private TextInputLayout mLocationLabel;
    private TextInputLayout mDescriptionLabel;

    private String name = null;
    private String personInCharge = null;
    private Date date = null;
    private String location = null;
    private String description = null;



    private OnFragmentInteractionListener mListener;

    public AddEventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment AddEventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddEventFragment newInstance() {
        AddEventFragment fragment = new AddEventFragment();
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
        View root = inflater.inflate(R.layout.fragment_add_event, container, false);
        mEventDbHelper = new EventDbHelper(getActivity());

        mSaveButton = (Button) root.findViewById(R.id.addEventButton);
        mEventImage = (ImageView) root.findViewById(R.id.event_image);
        mEventName = (TextInputEditText) root.findViewById(R.id.et_event_name);
        mEventPersonInCharge = (TextInputEditText) root.findViewById(R.id.et_event_person_in_charge);
        mEventDate = (TextInputEditText) root.findViewById(R.id.et_event_date);
        mEventLocation = (TextInputEditText) root.findViewById(R.id.et_event_location);
        mEventDescription = (TextInputEditText) root.findViewById(R.id.et_event_description);
        mNameLabel = (TextInputLayout) root.findViewById(R.id.til_event_name);
        mPersonInChargeLabel = (TextInputLayout) root.findViewById(R.id.til_event_person_in_charge);
        mDateLabel = (TextInputLayout) root.findViewById(R.id.til_event_date);
        mLocationLabel = (TextInputLayout) root.findViewById(R.id.til_event_location);
        mDescriptionLabel = (TextInputLayout) root.findViewById(R.id.til_event_description);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                try {
                    addEvent();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        return root;
    }

    private void addEvent() throws ParseException {
        boolean error = false;
        name = mEventName.getText().toString();
        personInCharge = mEventPersonInCharge.getText().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = new Date();
        date = dateFormat.parse(mEventDate.getText().toString());
        location = mEventLocation.getText().toString();
        description = mEventDescription.getText().toString();


        if (TextUtils.isEmpty(name)) {
            mNameLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(personInCharge)) {
            mPersonInChargeLabel.setError(getString(R.string.field_error));
            error = true;
        }


        if (TextUtils.isEmpty(location)) {
            mLocationLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if (TextUtils.isEmpty(description)) {
            mDescriptionLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (error) {
            return;
        }

        Event event = new Event("image", name, description, 3,  personInCharge, date, location);
        Log.d("event", event.getName());
        Log.d("event", event.getPictureUri());
        Log.d("event", event.getDescription());
        Log.d("event", String.valueOf(event.getRating()));
        Log.d("event", event.getPersonInCharge());
        Log.d("event", String.valueOf(event.getDate()));
        Log.d("event", event.getLocation());
        Long sa = mEventDbHelper.saveEvent(event);
      /*  if(sa > 0){
            Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
        }
        else {
           Toast.makeText(Toast.makeText(getActivity(), "No se pudo crear el evento", Toast.LENGTH_SHORT).show();
        }*/
        Log.d("guardó", String.valueOf(sa));

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



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
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
