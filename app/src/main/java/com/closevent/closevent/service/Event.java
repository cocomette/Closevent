package com.closevent.closevent.service;

import com.closevent.closevent.EventFragment;
import com.closevent.closevent.LoginActivity;

import java.util.Date;
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
    public String address;
    public String password;
    public boolean is_private;
    public int radius;
    public List<Float> position;
    public List<String> main_thread;
    public List<String> org_thread;

    public Event(String id, String name, Date dateDebut, Date dateFin, String address, boolean evPrivate) {
        this.id = id;
        this.name = name;
        this.start_date = dateDebut;
        this.end_date = dateFin;
        this.address = address;
        this.is_private = evPrivate;
    }

    public Event(){
        this.id= "undefined";
        this.name = "undefined";
        this.start_date = null;
        this.end_date = null;
        this.address = "undefined";
        this.is_private = true;
    }

    public static void updateAllEvents() {

        Call<List<Event>> req = LoginActivity.api.getEvents();
        req.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> req, Response<List<Event>> response) {
                try {
                    if( ! response.body().isEmpty() && EventFragment.mAdapter != null ) {
                        EventFragment.mAdapter.clear();
                        for( Event e:response.body() ) {
                            EventFragment.mAdapter.add(e);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(response.errorBody());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> req, Throwable t) {
                System.out.println(t);
            }
        });
    }

    public static void updateUserEvents(String user_id) {

        Call<List<Event>> req = LoginActivity.api.getUserEvents(user_id);
        req.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> req, Response<List<Event>> response) {
                try {
                    if(response.body() != null) {
                        EventFragment.mAdapter.clear();
                        if( ! response.body().isEmpty() && EventFragment.mAdapter != null ) {
                            for( Event e:response.body() ) {
                                EventFragment.mAdapter.add(e);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> req, Throwable t) {
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
