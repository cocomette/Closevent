package com.closevent.closevent.service;

import com.closevent.closevent.LoginActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Henri on 09/03/2016.
 */
public class User {
    public String id;
    public String name;
    public String picture_url;
    public List<String> event_list;

    public User(String id, String name, String url) {
        this.id = id;
        this.name = name;
        this.picture_url = url;
        this.event_list = new ArrayList<>();
    }

    public void register() {
        Call<User> req = LoginActivity.api.createUser(this);
        req.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> req, Response<User> response) {
                try {
                    System.out.println("User created: " + response.body().id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<User> req, Throwable t) {
                System.out.println(t);
            }
        });
    }
}

