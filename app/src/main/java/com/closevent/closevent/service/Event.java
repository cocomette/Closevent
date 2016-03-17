package com.closevent.closevent.service;

import com.closevent.closevent.EventAdapter;
import com.closevent.closevent.EventFragment;
import com.closevent.closevent.LoginActivity;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Côme on 29/02/2016.
 */
public class Event {
    public String id;
    public String name;
    public String user_id;
    public Date start_date;
    public Date end_date;
    public String password;
    public boolean is_private;
    public String address;
    public List<Float> position;
    public int radius;
    public List<String> main_thread;
    public List<String> org_thread;

    public Event(String name, String user_id, Date start_date, Date end_date, boolean is_private,
                 String address, List<Float> position, int radius) {
        this.name = name;
        this.user_id = user_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.is_private = is_private;
        this.address = address;
        this.position = position;
        this.radius = radius;
    }

    public Event(){
        this.id= "undefined";
        this.name = "undefined";
        this.start_date = null;
        this.end_date = null;
        this.address = "undefined";
        this.is_private = true;
    }

    public void save() {
        Call<Event> req = LoginActivity.api.createEvent(this);
        req.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> req, Response<Event> response) {
                try {
                    if( response.body() != null ) {
                        System.out.println("Event created: " + response.body().id);
                    } else {
                        System.out.println("Err: " + response.errorBody().string());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Event> req, Throwable t) {
                System.out.println(t);
            }
        });
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateDebut() {
        return start_date.toString();
    }

    public void setDateDebut(Date dateDebut) {
        this.start_date = dateDebut;
    }

    public String getDateFin() {
        return end_date.toString();
    }

    public void setDateFin(Date dateFin) {
        this.end_date = dateFin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isEvPrivate() {
        return is_private;
    }

    public void setEvPrivate(boolean is_private) {
        this.is_private = is_private;
    }
}
