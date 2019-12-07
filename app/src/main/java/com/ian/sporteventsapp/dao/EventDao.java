package com.ian.sporteventsapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.ian.sporteventsapp.model.Event;

import java.util.List;

@Dao
public interface EventDao
{
    @Insert
    void insert(Event event);

    @Query("SELECT * from event_table")
    LiveData<List<Event>> getAll();
}
