package com.ian.sporteventsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ian.sporteventsapp.model.Event;

import java.time.LocalTime;

public class EventActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
    }

    public void saveAction(View view)
    {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("new_event_name", "TRX");
        resultIntent.putExtra("new_event_location", "Location4");
        resultIntent.putExtra("new_event_start_time", "8:30");
        resultIntent.putExtra("new_event_end_time", "9:15");
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    public void cancelAction(View view)
    {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
