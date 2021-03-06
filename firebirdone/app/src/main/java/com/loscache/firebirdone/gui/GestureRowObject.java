package com.loscache.firebirdone.gui;

/**
 * Created by ClaudioSaccella on 4/11/2017.
 */

public class GestureRowObject {

    private String title;
    private String description;
    private int image;

    public GestureRowObject(String title, String description, int image){
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public int getImage() {
        return image;
    }
}
