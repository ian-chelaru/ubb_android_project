package com.ian.sporteventsapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ian.sporteventsapp.model.Event;
import com.ian.sporteventsapp.repository.EventRepository;

import java.util.List;

public class EventViewModel extends AndroidViewModel
{
    private EventRepository eventRepository;

    private LiveData<List<Event>> events;

    public EventViewModel(@NonNull Application application)
    {
        super(application);
        eventRepository = new EventRepository(application);
        events = eventRepository.getAllEvents();
    }

    public LiveData<List<Event>> getAllEvents()
    {
        return events;
    }

    public void insert(Event event)
    {
        eventRepository.insert(event);
    }
}
