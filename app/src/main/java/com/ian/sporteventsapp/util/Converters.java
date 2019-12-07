package com.ian.sporteventsapp.util;

import androidx.room.TypeConverter;

import java.time.LocalTime;

public class Converters
{
    @TypeConverter
    public static LocalTime fromString(String time)
    {
        return LocalTime.parse(time);
    }

    @TypeConverter
    public static String localTimeToString(LocalTime localTime)
    {
        return localTime.toString();
    }
}
