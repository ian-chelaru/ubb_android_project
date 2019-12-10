package com.ian.sporteventsapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ian.sporteventsapp.entities.Event;

import java.util.List;

@Dao
public interface EventDao
{
    @Insert
    void insert(Event event);

    @Query("SELECT * FROM event_table")
    LiveData<List<Event>> getAll();

    @Delete
    void delete(Event event);

    @Update
    void update(Event event);

    @Query("DELETE FROM event_table")
    void deleteAll();
}
