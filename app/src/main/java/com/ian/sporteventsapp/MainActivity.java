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
import android.widget.Toast;

import com.ian.sporteventsapp.adapters.EventListAdapter;
import com.ian.sporteventsapp.entities.Event;
import com.ian.sporteventsapp.rest.model.EventModel;
import com.ian.sporteventsapp.rest.services.EventRestService;
import com.ian.sporteventsapp.util.EventConverter;
import com.ian.sporteventsapp.viewmodel.EventViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
{
    private static final int CREATE_EVENT_REQUEST = 1;
    private static final int UPDATE_EVENT_REQUEST = 2;

    private EventViewModel eventViewModel;
    private Event eventToBeUpdated;
    private EventRestService eventRestService;

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

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:8080/api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        eventRestService = retrofit.create(EventRestService.class);

        getAllServerEvents();
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
                deleteServerEvent(eventSelected);
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
        eventToBeUpdated = new Event();
        eventToBeUpdated.setId(event.getId());
        eventToBeUpdated.setName(event.getName());
        eventToBeUpdated.setLocation(event.getLocation());
        eventToBeUpdated.setStartTime(event.getStartTime());
        eventToBeUpdated.setEndTime(event.getEndTime());

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
                insertServerEvent(newEvent);
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
                updateServerEvent(eventToBeUpdated);
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

    private void insertServerEvent(Event event)
    {
        EventModel eventModel = EventConverter.convertEventToEventModel(event);
        eventModel.setId(0);
        eventRestService.insert(eventModel).enqueue(new Callback<EventModel>()
        {
            @Override
            public void onResponse(@NotNull Call<EventModel> call, @NotNull Response<EventModel> response)
            {
                if (response.isSuccessful())
                {
                    EventModel responseBody = response.body();
                    System.out.println(responseBody);
                    assert responseBody != null;
                    Event newEvent = EventConverter.convertEventModelToEvent(responseBody);
                    newEvent.setPersistedInServer(true);
                    eventViewModel.insert(newEvent);
                }
            }

            @Override
            public void onFailure(@NotNull Call<EventModel> call, @NotNull Throwable t)
            {
                event.setPersistedInServer(false);
                eventViewModel.insert(event);
                displayToastMessage("No internet connection. Data persisted locally.");
            }
        });
    }

    private void getAllServerEvents()
    {
        eventRestService.getAll().enqueue(new Callback<List<EventModel>>()
        {
            @Override
            public void onResponse(@NotNull Call<List<EventModel>> call, @NotNull Response<List<EventModel>> response)
            {
                if (response.isSuccessful())
                {
                    Objects.requireNonNull(eventViewModel.getAllEvents().getValue())
                            .stream()
                            .filter(event -> !event.isPersistedInServer())
                            .forEach(event -> insertServerEvent(event));

                    List<EventModel> responseBody = response.body();
                    assert responseBody != null;
                    eventViewModel.deleteAll();
                    responseBody.forEach(eventModel -> {
                        Event event = EventConverter.convertEventModelToEvent(eventModel);
                        event.setPersistedInServer(true);
                        eventViewModel.insert(event);
                    });
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<EventModel>> call, @NotNull Throwable t)
            {
                displayToastMessage("No internet connection. Local data displayed.");
            }
        });
    }

    private void updateServerEvent(Event event)
    {
        EventModel eventModel = EventConverter.convertEventToEventModel(event);
        eventRestService.update(eventModel).enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response)
            {
                if (response.isSuccessful())
                {
                    eventViewModel.update(event);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t)
            {
                displayToastMessage("No internet connection. Update operation not available.");
            }
        });
    }

    private void deleteServerEvent(Event event)
    {
        EventModel eventModel = EventConverter.convertEventToEventModel(event);
        eventRestService.delete(eventModel).enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response)
            {
                if (response.isSuccessful())
                {
                    eventViewModel.delete(event);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t)
            {
                displayToastMessage("No internet connection. Delete operation not available.");
            }
        });
    }

    private void displayToastMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}
