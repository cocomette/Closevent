package com.closevent.closevent.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;

/**
 * Created by Henri on 08/03/2016.
 */
public interface CloseventAPI {

    @PUT("users")
    Call<ResUser> createUser(@Body User user);
}