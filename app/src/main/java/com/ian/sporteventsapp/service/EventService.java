package com.ian.sporteventsapp.service;

import com.ian.sporteventsapp.model.Event;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EventService
{
    public static List<Event> getEvents()
    {
        List<Event> eventList = new ArrayList<>();

        Event event1 = new Event();
        event1.setId(1);
        event1.setName("Cycling");
        event1.setStartTime(LocalTime.of(16, 0));
        event1.setEndTime(LocalTime.of(17, 0));
        event1.setLocation("Location1");
        event1.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");

        Event event2 = new Event();
        event2.setId(2);
        event2.setName("Football");
        event2.setStartTime(LocalTime.of(20, 30));
        event2.setEndTime(LocalTime.of(22, 0));
        event2.setLocation("Location2");
        event2.setDescription("Description2");

        Event event3 = new Event();
        event3.setId(3);
        event3.setName("Basketball");
        event3.setStartTime(LocalTime.of(8, 15));
        event3.setEndTime(LocalTime.of(9, 0));
        event3.setLocation("Location3");
        event3.setDescription("Description3");

        eventList.add(event1);
        eventList.add(event2);
        eventList.add(event3);

        return eventList;
    }
}
