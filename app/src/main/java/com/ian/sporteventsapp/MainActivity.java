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
import com.ian.sporteventsapp.service.EventService;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView eventListView = findViewById(R.id.events_list_view);
        EventAdapter eventAdapter = new EventAdapter(this, EventService.getEvents());
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
        return super.onContextItemSelected(item);
    }
}
