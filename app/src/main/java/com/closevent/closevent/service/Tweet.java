package com.closevent.closevent.service;

import com.closevent.closevent.service.User;

import java.util.Date;

/**
 * Created by Côme on 05/03/2016.
 */
public class Tweet {
    public int color;
    public String created_at;
    public String url;
    public User user;
    public String comment;
    public String id;


    public Tweet(int color, User user, String comment, String url) {
        this.color = color;
        this.user = user;
        this.comment = comment;
        this.url = url;
    }
}
