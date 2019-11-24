package com.ian.sporteventsapp.repository;

import com.ian.sporteventsapp.model.Event;

import java.util.List;

public class EventRepository
{
    private static int KEY = 3;

    private List<Event> events;

    public EventRepository(List<Event> events)
    {
        this.events = events;
    }

    public List<Event> getEvents()
    {
        return events;
    }

    public void setEvents(List<Event> events)
    {
        this.events = events;
    }

    public void addEvent(Event event)
    {
        KEY += 1;
        event.setId(KEY);
        events.add(event);
    }

    public void removeEventByIndex(int index)
    {
        events.remove(index);
    }

    public Event getEventByIndex(int index)
    {
        return events.get(index);
    }
}
