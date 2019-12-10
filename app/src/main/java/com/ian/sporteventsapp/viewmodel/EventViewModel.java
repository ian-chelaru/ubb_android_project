package com.ian.sporteventsapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ian.sporteventsapp.entities.Event;
import com.ian.sporteventsapp.repository.EventRepository;

import java.util.List;
import java.util.Objects;

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

    public Event getEventByPosition(int position)
    {
        return Objects.requireNonNull(events.getValue()).get(position);
    }

    public LiveData<List<Event>> getAllEvents()
    {
        return events;
    }

    public void insert(Event event)
    {
        eventRepository.insert(event);
    }

    public void delete(Event event)
    {
        eventRepository.delete(event);
    }

    public void update(Event event)
    {
        eventRepository.update(event);
    }
}
