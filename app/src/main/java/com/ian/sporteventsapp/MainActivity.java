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

    private EventAdapter eventAdapter;
    private EventRepository eventRepository;

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
            case R.id.remove_event:
                System.out.println("Remove item");
                eventRepository.removeEventByIndex(info.position);
                eventAdapter.notifyDataSetChanged();
                break;
            case R.id.update_event:
                System.out.println("Update item");
                break;
        }

        return super.onContextItemSelected(item);
    }

    public void createNewEvent(View view)
    {
        Intent intent = new Intent(this, EventActivity.class);
        startActivityForResult(intent, CREATE_EVENT_REQUEST);
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

        assert eventStartTime != null;
        event.setStartTime(createLocalTimeFromString(eventStartTime));
        assert eventEndTime != null;
        event.setEndTime(createLocalTimeFromString(eventEndTime));

        return event;
    }

    private LocalTime createLocalTimeFromString(String time)
    {
        int colonIndex = time.indexOf(":");
        int hour = Integer.valueOf(time.substring(0, colonIndex));
        int minute = Integer.valueOf(time.substring(colonIndex + 1));
        return LocalTime.of(hour, minute);
    }
}
