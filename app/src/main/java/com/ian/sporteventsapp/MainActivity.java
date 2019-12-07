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
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.ian.sporteventsapp.adapters.EventAdapter;
import com.ian.sporteventsapp.adapters.EventListAdapter;
import com.ian.sporteventsapp.model.Event;
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

        registerForContextMenu(eventRecyclerView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId() == R.id.event_recycler_view)
        {
            getMenuInflater().inflate(R.menu.event_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId())
        {
            case R.id.view_event:
//                viewEvent(info.position);
                System.out.println("VIEW EVENT");
                break;
            case R.id.update_event:
//                updateEvent(info.position);
                System.out.println("UPDATE EVENT");
                break;
            case R.id.remove_event:
//                removeEvent(info.position);
                System.out.println("REMOVE EVENT");
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

//    private void viewEvent(int index)
//    {
//        Event event = eventRepository.getEventByIndex(index);
//
//        Intent intent = new Intent(this, ViewEventActivity.class);
//        intent.putExtra("event_name", event.getName());
//        intent.putExtra("event_location", event.getLocation());
//        intent.putExtra("event_start_time", event.getStartTime().toString());
//        intent.putExtra("event_end_time", event.getEndTime().toString());
//        intent.putExtra("event_description", event.getDescription());
//        startActivity(intent);
//    }
//
//    private void updateEvent(int index)
//    {
//        eventToBeUpdated = eventRepository.getEventByIndex(index);
//
//        Intent intent = new Intent(this, EventActivity.class);
//        intent.putExtra("event_name", eventToBeUpdated.getName());
//        intent.putExtra("event_location", eventToBeUpdated.getLocation());
//        intent.putExtra("event_start_time", eventToBeUpdated.getStartTime().toString());
//        intent.putExtra("event_end_time", eventToBeUpdated.getEndTime().toString());
//        intent.putExtra("event_description", eventToBeUpdated.getDescription());
//        startActivityForResult(intent, UPDATE_EVENT_REQUEST);
//    }
//
//    private void removeEvent(int index)
//    {
//        eventRepository.removeEventByIndex(index);
//        eventAdapter.notifyDataSetChanged();
//    }

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
