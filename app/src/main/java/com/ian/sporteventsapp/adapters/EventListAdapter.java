package com.ian.sporteventsapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ian.sporteventsapp.R;
import com.ian.sporteventsapp.model.Event;

import java.util.List;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder>
{
    private final LayoutInflater inflater;
    private List<Event> events;

    public EventListAdapter(Context context)
    {
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View eventView = inflater.inflate(R.layout.event_item, parent, false);
        return new EventViewHolder(eventView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position)
    {
        if (events != null)
        {
            holder.bindViewHolder(events.get(position));
        }
        else
        {
            holder.nameView.setText(R.string.no_events);
        }
    }

    @Override
    public int getItemCount()
    {
        if (events != null)
        {
            return events.size();
        }
        return 0;
    }

    public void setEvents(List<Event> events)
    {
        this.events = events;
        notifyDataSetChanged();
    }

    class EventViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView nameView;
        private final TextView timeView;
        private final TextView locationView;

        private EventViewHolder(@NonNull View itemView)
        {
            super(itemView);
            nameView = itemView.findViewById(R.id.event_name);
            timeView = itemView.findViewById(R.id.event_time);
            locationView = itemView.findViewById(R.id.event_location);
        }

        private void bindViewHolder(Event event)
        {
            String time = event.getStartTime().toString() + ":" + event.getEndTime().toString();

            nameView.setText(event.getName());
            timeView.setText(time);
            locationView.setText(event.getLocation());
        }
    }
}
