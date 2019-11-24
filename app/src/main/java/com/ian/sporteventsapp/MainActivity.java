package com.ian.sporteventsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ian.sporteventsapp.adapters.EventAdapter;
import com.ian.sporteventsapp.model.Event;
import com.ian.sporteventsapp.repository.EventRepository;
import com.ian.sporteventsapp.service.EventService;

import java.time.LocalTime;

public class MainActivity extends AppCompatActivity
{
    private static final int CREATE_EVENT_REQUEST = 1;
    private static final int UPDATE_EVENT_REQUEST = 2;

    private EventAdapter eventAdapter;
    private EventRepository eventRepository;

    private Event eventToBeUpdated;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventRepository = new EventRepository(EventService.getEvents());

        ListView eventListView = findViewById(R.id.events_list_view);
        eventAdapter = new EventAdapter(this, eventRepository.getEvents());
        eventListView.setAdapter(eventAdapter);

        registerForContextMenu(eventListView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId() == R.id.events_list_view)
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
                System.out.println("View item");
                break;
            case R.id.update_event:
                System.out.println("Update item");
                updateEvent(info.position);
                break;
            case R.id.remove_event:
                System.out.println("Remove item");
                removeEvent(info.position);
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
        startActivityForResult(intent, CREATE_EVENT_REQUEST);
    }

    private void updateEvent(int index)
    {
        eventToBeUpdated = eventRepository.getEventByIndex(index);

        Intent intent = new Intent(this, EventActivity.class);
        intent.putExtra("event_name", eventToBeUpdated.getName());
        intent.putExtra("event_location", eventToBeUpdated.getLocation());
        intent.putExtra("event_start_time", eventToBeUpdated.getStartTime().toString());
        intent.putExtra("event_end_time", eventToBeUpdated.getEndTime().toString());
        startActivityForResult(intent, UPDATE_EVENT_REQUEST);
    }

    private void removeEvent(int index)
    {
        eventRepository.removeEventByIndex(index);
        eventAdapter.notifyDataSetChanged();
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
                eventRepository.addEvent(newEvent);
                eventAdapter.notifyDataSetChanged();
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
                eventAdapter.notifyDataSetChanged();
            }
        }
    }

    private Event createEventFromIntent(Intent data)
    {
        String eventName = data.getStringExtra("new_event_name");
        String eventLocation = data.getStringExtra("new_event_location");
        String eventStartTime = data.getStringExtra("new_event_start_time");
        String eventEndTime = data.getStringExtra("new_event_end_time");

        Event event = new Event();
        event.setName(eventName);
        event.setLocation(eventLocation);
        event.setStartTime(LocalTime.parse(eventStartTime));
        event.setEndTime(LocalTime.parse(eventEndTime));
        return event;
    }

}
