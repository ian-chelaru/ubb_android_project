package com.ian.sporteventsapp.rest.services;

import com.ian.sporteventsapp.rest.model.EventModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface EventRestService
{
    @POST("events")
    @Headers("Content-Type: application/json")
    Call<EventModel> insert(@Body EventModel eventModel);

    @GET("events")
    @Headers("Content-Type: application/json")
    Call<List<EventModel>> getAll();

    @PUT("events")
    @Headers("Content-Type: application/json")
    Call<Void> update(@Body EventModel eventModel);

    @HTTP(method = "DELETE", path = "events", hasBody = true)
    @Headers("Content-Type: application/json")
    Call<Void> delete(@Body EventModel eventModel);
}
