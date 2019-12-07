package com.ian.sporteventsapp.dao;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ian.sporteventsapp.model.Event;
import com.ian.sporteventsapp.service.EventService;
import com.ian.sporteventsapp.util.Converters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Event.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class EventRoomDatabase extends RoomDatabase
{
    public abstract EventDao eventDao();

    private static volatile EventRoomDatabase instance;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static EventRoomDatabase getInstance(final Context context)
    {
        if (instance == null)
        {
            synchronized (EventRoomDatabase.class)
            {
                if (instance == null)
                {
                    instance = Room.databaseBuilder(context.getApplicationContext(), EventRoomDatabase.class,
                            "event_database").addCallback(roomDatabaseCallback).build();
                }
            }
        }
        return instance;
    }

    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db)
        {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                EventDao eventDao = instance.eventDao();
                EventService.getEvents().forEach(eventDao::insert);
            });
        }
    };
}
