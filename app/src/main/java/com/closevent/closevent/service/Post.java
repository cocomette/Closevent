package com.closevent.closevent.service;

import com.closevent.closevent.LoginActivity;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Henri on 21/03/2016.
 */
public class Post {
    public String user_id;
    public String comment;
    public String url;


    public Post(String comment, String user_id) {
        this.user_id = user_id;
        this.comment = comment;
        this.url = "";
    }

    public void save(String event_id) {
        Call<Tweet> req = LoginActivity.api.createTweet(event_id, this);
        req.enqueue(new Callback<Tweet>() {
            @Override
            public void onResponse(Call<Tweet> req, Response<Tweet> response) {
                try {
                    if( response.body() != null ) {
                        System.out.println("Tweet created: " + response.body().id);
                    } else {
                        System.out.println("Err: " + response.errorBody().string());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Tweet> req, Throwable t) {
                System.out.println(t);
            }
        });

    }
}
