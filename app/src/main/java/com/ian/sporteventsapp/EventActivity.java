package com.ian.sporteventsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ian.sporteventsapp.model.Event;

import java.time.LocalTime;

public class EventActivity extends AppCompatActivity
{
    private EditText nameEditText;
    private EditText locationEditText;
    private EditText startTimeEditText;
    private EditText endTimeEditText;
    private EditText descriptionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        nameEditText = findViewById(R.id.name_edit_text);
        locationEditText = findViewById(R.id.location_edit_text);
        startTimeEditText = findViewById(R.id.start_time_edit_text);
        endTimeEditText = findViewById(R.id.end_time_edit_text);
        descriptionEditText = findViewById(R.id.description_edit_text);

        fillFields();
    }

    private void fillFields()
    {
        Intent intent = getIntent();

        nameEditText.setText(intent.getStringExtra("event_name"));
        locationEditText.setText(intent.getStringExtra("event_location"));
        startTimeEditText.setText(intent.getStringExtra("event_start_time"));
        endTimeEditText.setText(intent.getStringExtra("event_end_time"));
        descriptionEditText.setText(intent.getStringExtra("event_description"));
    }

    public void saveAction(View view)
    {
        String eventName = nameEditText.getText().toString();
        String eventLocation = locationEditText.getText().toString();
        String eventStartTime = startTimeEditText.getText().toString();
        String eventEndTime = endTimeEditText.getText().toString();
        String eventDescription = descriptionEditText.getText().toString();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("new_event_name", eventName);
        resultIntent.putExtra("new_event_location", eventLocation);
        resultIntent.putExtra("new_event_start_time", eventStartTime);
        resultIntent.putExtra("new_event_end_time", eventEndTime);
        resultIntent.putExtra("new_event_description", eventDescription);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    public void cancelAction(View view)
    {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
