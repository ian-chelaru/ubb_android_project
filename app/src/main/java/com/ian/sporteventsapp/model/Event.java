package com.ian.sporteventsapp.model;


import java.time.LocalDate;
import java.time.LocalTime;

public class Event
{
    private int id;
    private String name;
    private String location;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public Event()
    {
    }

    public Event(int id, String name, String location, LocalDate date, LocalTime startTime, LocalTime endTime)
    {
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
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

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
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
}
