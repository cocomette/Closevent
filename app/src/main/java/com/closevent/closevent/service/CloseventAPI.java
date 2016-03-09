package com.closevent.closevent.service;

import com.closevent.closevent.service.Event;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.Call;

/**
 * Created by Henri on 08/03/2016.
 */
public interface CloseventAPI {

    @PUT("users")
    Call<ResPut> createUser(@Body User user);
}