package com.closevent.closevent.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Henri on 08/03/2016.
 */
public interface CloseventApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////
    // USERS
    @PUT("users")
    Call<User> createUser(@Body User user);

    @GET("user/{user_id}")
    Call<User> getUser(@Path("user_id") String user_id);

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // EVENTS
    @GET("events")
    Call<List<Event>> getEvents();

    @GET("user/{user_id}/events")
    Call<List<Event>> getUserEvents(@Path("user_id") String user_id);

    @PUT("events")
    Call<Event> createEvent(@Body Event event);

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TWEETS
    @PUT("medias/{event_id}")
    Call<Tweet> createTweet(@Path("event_id") String event_id, @Body Post post);

}