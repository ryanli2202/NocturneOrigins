package com.wraith.nocturneorigins.gui;

public enum SkinType {
    CLASSIC("classic"),
    SLIM("slim");

    private final String name;

    SkinType(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
