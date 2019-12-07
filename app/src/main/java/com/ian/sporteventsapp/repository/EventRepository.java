package com.ian.sporteventsapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.ian.sporteventsapp.dao.EventDao;
import com.ian.sporteventsapp.dao.EventRoomDatabase;
import com.ian.sporteventsapp.model.Event;

import java.util.List;

public class EventRepository
{
    private EventDao eventDao;
    private LiveData<List<Event>> events;

    public EventRepository(Application application)
    {
        EventRoomDatabase db = EventRoomDatabase.getInstance(application);
        eventDao = db.eventDao();
        events = eventDao.getAll();
    }

    public LiveData<List<Event>> getAllEvents()
    {
        return events;
    }

    public void insert(Event event)
    {
        EventRoomDatabase.databaseWriteExecutor.execute(() ->
                eventDao.insert(event)
        );
    }

    //    private static int KEY = 3;
//
//    private List<Event> events;
//
//    public EventRepository(List<Event> events)
//    {
//        this.events = events;
//    }
//
//    public List<Event> getEvents()
//    {
//        return events;
//    }
//
//    public void setEvents(List<Event> events)
//    {
//        this.events = events;
//    }
//
//    public void addEvent(Event event)
//    {
//        KEY += 1;
//        event.setId(KEY);
//        events.add(event);
//    }
//
//    public void removeEventByIndex(int index)
//    {
//        events.remove(index);
//    }
//
//    public Event getEventByIndex(int index)
//    {
//        return events.get(index);
//    }
}
