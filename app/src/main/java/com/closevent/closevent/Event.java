package com.closevent.closevent;

import java.util.Date;

/**
 * Created by Côme on 29/02/2016.
 */
public class Event {
    public String id;
    private String name;
    private String dateDebut;
    private String dateFin;
    private String address;
    private boolean evPrivate;

    public Event(String id, String name, String dateDebut, String dateFin, String address, boolean evPrivate) {
        this.id = id;
        this.name = name;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.address = address;
        this.evPrivate = evPrivate;
    }

    public Event(){
        this.id= "undefined";
        this.name = "undefined";
        this.dateDebut = "undefined";
        this.dateFin = "undefined";
        this.address = "undefined";
        this.evPrivate = true;
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
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isEvPrivate() {
        return evPrivate;
    }

    public void setEvPrivate(boolean evPrivate) {
        this.evPrivate = evPrivate;
    }
}
