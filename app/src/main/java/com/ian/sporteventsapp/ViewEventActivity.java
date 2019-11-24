package com.ian.sporteventsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ViewEventActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

        fillFields();
    }

    private void fillFields()
    {
        Intent intent = getIntent();

        ((TextView) findViewById(R.id.name_view)).setText(intent.getStringExtra("event_name"));
        ((TextView) findViewById(R.id.location_view)).setText(intent.getStringExtra("event_location"));
        ((TextView) findViewById(R.id.start_time_view)).setText(intent.getStringExtra("event_start_time"));
        ((TextView) findViewById(R.id.end_time_view)).setText(intent.getStringExtra("event_end_time"));
        ((TextView) findViewById(R.id.description_view)).setText(intent.getStringExtra("event_description"));
    }
}
