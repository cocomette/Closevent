package com.closevent.closevent;

/**
 * Created by Côme on 05/03/2016.
 */
public class Tweet {
    private int color;
    private String pseudo;
    private String text;



    public Tweet(int color, String pseudo, String text) {
        this.color = color;
        this.pseudo = pseudo;
        this.text = text;
    }

    public String getPseudo() {
        return pseudo;
    }

    public int getColor() {
        return color;
    }

    public String getText() {
        return text;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setText(String text) {
        this.text = text;
    }
}
