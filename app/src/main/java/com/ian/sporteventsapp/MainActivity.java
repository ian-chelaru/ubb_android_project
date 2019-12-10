package com.ian.sporteventsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.ian.sporteventsapp.adapters.EventListAdapter;
import com.ian.sporteventsapp.entities.Event;
import com.ian.sporteventsapp.viewmodel.EventViewModel;

import java.time.LocalTime;

public class MainActivity extends AppCompatActivity
{
    private static final int CREATE_EVENT_REQUEST = 1;
    private static final int UPDATE_EVENT_REQUEST = 2;

    private EventViewModel eventViewModel;
    private Event eventToBeUpdated;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView eventRecyclerView = findViewById(R.id.event_recycler_view);
        final EventListAdapter eventListAdapter = new EventListAdapter(this);
        eventRecyclerView.setAdapter(eventListAdapter);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        eventViewModel.getAllEvents().observe(this, eventListAdapter::setEvents
        );
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item)
    {
        Event eventSelected = eventViewModel.getEventByPosition(item.getGroupId());
        switch (item.getItemId())
        {
            case R.id.view_event:
                viewEvent(eventSelected);
                break;
            case R.id.update_event:
                updateEvent(eventSelected);
                break;
            case R.id.remove_event:
                eventViewModel.delete(eventSelected);
                break;
        }

        return super.onContextItemSelected(item);
    }

    public void createNewEvent(View view)
    {
        Intent intent = new Intent(this, EventActivity.class);
        intent.putExtra("event_name", getResources().getString(R.string.default_name));
        intent.putExtra("event_location", getResources().getString(R.string.default_location));
        intent.putExtra("event_start_time", getResources().getString(R.string.default_start_time));
        intent.putExtra("event_end_time", getResources().getString(R.string.default_end_time));
        intent.putExtra("event_description", getResources().getString(R.string.default_description));
        startActivityForResult(intent, CREATE_EVENT_REQUEST);
    }

    private void viewEvent(Event event)
    {
        Intent intent = new Intent(this, ViewEventActivity.class);
        intent.putExtra("event_name", event.getName());
        intent.putExtra("event_location", event.getLocation());
        intent.putExtra("event_start_time", event.getStartTime().toString());
        intent.putExtra("event_end_time", event.getEndTime().toString());
        intent.putExtra("event_description", event.getDescription());
        startActivity(intent);
    }

    private void updateEvent(Event event)
    {
        eventToBeUpdated = event;

        Intent intent = new Intent(this, EventActivity.class);
        intent.putExtra("event_name", eventToBeUpdated.getName());
        intent.putExtra("event_location", eventToBeUpdated.getLocation());
        intent.putExtra("event_start_time", eventToBeUpdated.getStartTime().toString());
        intent.putExtra("event_end_time", eventToBeUpdated.getEndTime().toString());
        intent.putExtra("event_description", eventToBeUpdated.getDescription());
        startActivityForResult(intent, UPDATE_EVENT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_EVENT_REQUEST)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                assert data != null;
                Event newEvent = createEventFromIntent(data);
                eventViewModel.insert(newEvent);
            }
        }
        else if (requestCode == UPDATE_EVENT_REQUEST)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                assert data != null;
                Event newEvent = createEventFromIntent(data);
                eventToBeUpdated.setName(newEvent.getName());
                eventToBeUpdated.setLocation(newEvent.getLocation());
                eventToBeUpdated.setStartTime(newEvent.getStartTime());
                eventToBeUpdated.setEndTime(newEvent.getEndTime());
                eventToBeUpdated.setDescription(newEvent.getDescription());
                eventViewModel.update(eventToBeUpdated);
            }
        }
    }

    private Event createEventFromIntent(Intent data)
    {
        String eventName = data.getStringExtra("new_event_name");
        String eventLocation = data.getStringExtra("new_event_location");
        String eventStartTime = data.getStringExtra("new_event_start_time");
        String eventEndTime = data.getStringExtra("new_event_end_time");
        String eventDescription = data.getStringExtra("new_event_description");

        Event event = new Event();
        event.setName(eventName);
        event.setLocation(eventLocation);
        event.setStartTime(LocalTime.parse(eventStartTime));
        event.setEndTime(LocalTime.parse(eventEndTime));
        event.setDescription(eventDescription);
        return event;
    }

}
