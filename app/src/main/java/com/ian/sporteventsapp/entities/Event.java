package com.ian.sporteventsapp.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalTime;

@Entity(tableName = "event_table")
public class Event
{
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private String name;

    private String location;

    private LocalTime startTime;

    private LocalTime endTime;

    private String description;

    private boolean isPersistedInServer;

    public Event()
    {
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public LocalTime getStartTime()
    {
        return startTime;
    }

    public void setStartTime(LocalTime startTime)
    {
        this.startTime = startTime;
    }

    public LocalTime getEndTime()
    {
        return endTime;
    }

    public void setEndTime(LocalTime endTime)
    {
        this.endTime = endTime;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public boolean isPersistedInServer()
    {
        return isPersistedInServer;
    }

    public void setPersistedInServer(boolean persistedInServer)
    {
        isPersistedInServer = persistedInServer;
    }

    @Override
    public String toString()
    {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
