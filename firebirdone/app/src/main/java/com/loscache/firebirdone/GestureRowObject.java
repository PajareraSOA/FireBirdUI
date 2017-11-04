package com.loscache.firebirdone;

/**
 * Created by ClaudioSaccella on 4/11/2017.
 */

public class GestureRowObject {

    private String title;
    private String description;

    public GestureRowObject(String title, String description){
        this.title = title;
        this.description = description;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }
}
