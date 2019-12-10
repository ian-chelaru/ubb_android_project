package com.ian.sporteventsapp.util;

import com.ian.sporteventsapp.entities.Event;
import com.ian.sporteventsapp.rest.model.EventModel;

import java.time.LocalTime;

public class EventConverter
{
    public static EventModel convertEventToEventModel(Event event)
    {
        EventModel eventModel = new EventModel();
        eventModel.setId(event.getId());
        eventModel.setName(event.getName());
        eventModel.setLocation(event.getLocation());
        eventModel.setStartTime(event.getStartTime().toString());
        eventModel.setEndTime(event.getEndTime().toString());
        return  eventModel;
    }

    public static Event convertEventModelToEvent(EventModel eventModel)
    {
        Event event = new Event();
        event.setId(eventModel.getId());
        event.setName(eventModel.getName());
        event.setLocation(eventModel.getLocation());
        event.setStartTime(LocalTime.parse(eventModel.getStartTime()));
        event.setEndTime(LocalTime.parse(eventModel.getEndTime()));
        return event;
    }
}
