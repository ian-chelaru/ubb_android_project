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
        EventRoomDatabase.databaseWriteExecutor.execute(() -> eventDao.insert(event));
    }

    public void delete(Event event)
    {
        EventRoomDatabase.databaseWriteExecutor.execute(() -> eventDao.delete(event));
    }

    public void update(Event event)
    {
        EventRoomDatabase.databaseWriteExecutor.execute(() -> eventDao.update(event));
    }
}
