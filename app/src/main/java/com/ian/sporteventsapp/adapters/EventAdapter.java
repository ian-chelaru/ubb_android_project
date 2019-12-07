package com.ian.sporteventsapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.ian.sporteventsapp.R;
import com.ian.sporteventsapp.model.Event;

import java.time.LocalTime;
import java.util.List;

public class EventAdapter extends ArrayAdapter<Event>
{
    public EventAdapter(Context context, LiveData<List<Event>> events)
    {
        super(context, 0, events.getValue());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;

        View rowView = (convertView == null) ? inflater.inflate(R.layout.event_item, parent, false)
                : convertView;

        Event event = getItem(position);
        assert event != null;

        LocalTime startTime = event.getStartTime();
        LocalTime endTime = event.getEndTime();

        TextView eventTime = rowView.findViewById(R.id.event_time);

        String startTimeHour = convertTimeToString(startTime.getHour());
        String startTimeMinute = convertTimeToString(startTime.getMinute());

        String endTimeHour = convertTimeToString(endTime.getHour());
        String endTimeMinute = convertTimeToString(endTime.getMinute());

        String timeInterval = startTimeHour + ":" + startTimeMinute + "-" + endTimeHour +
                ":" + endTimeMinute;
        eventTime.setText(timeInterval);

        TextView eventName = rowView.findViewById(R.id.event_name);
        eventName.setText(event.getName());

        TextView eventLocation = rowView.findViewById(R.id.event_location);
        eventLocation.setText(event.getLocation());

        return rowView;
    }

    private String convertTimeToString(int time)
    {
        return (time > 9) ? (String.valueOf(time)) : "0" + time;
    }
}
