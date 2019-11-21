package com.ian.sporteventsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity
{
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
}
