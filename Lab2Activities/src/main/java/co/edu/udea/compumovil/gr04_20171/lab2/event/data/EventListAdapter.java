package co.edu.udea.compumovil.gr04_20171.lab2.event.data;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import co.edu.udea.compumovil.gr04_20171.lab2.R;

/**
 * Created by juangui on 12/03/17.
 */

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder>
                                implements View.OnClickListener{

    private ArrayList<Event> events;
    private View.OnClickListener listener;

    public EventListAdapter(ArrayList<Event> datos) {
        this.events = datos;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        EventViewHolder evh = new EventViewHolder(view);
        view.setOnClickListener(this);
        return evh;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView eventName;
        TextView eventDescription;
        ImageView eventImage;
        ImageButton eventLocation;
        ImageButton eventInformation;


        EventViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            eventName = (TextView) itemView.findViewById(R.id.event_name);
            eventDescription = (TextView) itemView.findViewById(R.id.event_description);
            //eventImage = (ImageButton) itemView.findViewById(R.id.event_location);
            //eventInformation = (ImageButton) itemView.findViewById(R.id.event_information);
        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        holder.eventName.setText(events.get(position).getName());
        holder.eventDescription.setText(events.get(position).getDescription());
        //holder.eventImage.setImageResource(R.drawable.ic_menu_manage);
        //holder.eventInformation.setImageResource(R.drawable.ic_menu_manage);
        //holder.eventLocation.setImageResource(R.drawable.ic_menu_manage);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
