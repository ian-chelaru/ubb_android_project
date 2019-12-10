package com.ian.sporteventsapp.dao;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ian.sporteventsapp.entities.Event;
import com.ian.sporteventsapp.service.EventService;
import com.ian.sporteventsapp.util.Converters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Event.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class EventRoomDatabase extends RoomDatabase
{
    public abstract EventDao eventDao();

    private static volatile EventRoomDatabase instance;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE event_table "
                    +"ADD COLUMN 'isPersistedInServer' INTEGER NOT NULL DEFAULT 1");

        }
    };

    public static EventRoomDatabase getInstance(final Context context)
    {
        if (instance == null)
        {
            synchronized (EventRoomDatabase.class)
            {
                if (instance == null)
                {
                    instance = Room.databaseBuilder(context.getApplicationContext(), EventRoomDatabase.class,
                            "event_database").addCallback(roomDatabaseCallback)
                            .addMigrations(MIGRATION_1_2).build();
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
                EventService.getInitialEvents().forEach(eventDao::insert);
            });
        }
    };
}
