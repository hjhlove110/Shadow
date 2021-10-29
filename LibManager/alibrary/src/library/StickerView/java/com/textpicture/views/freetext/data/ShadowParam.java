package com.textpicture.views.freetext.data;

import com.textpicture.views.freetext.annotation.Description;

import java.io.Serializable;


public class ShadowParam implements Serializable {

    @Description(name = "半径")
    public float radius;
    @Description(name = "水平偏移")
    public float x;
    @Description(name = "竖直偏移")
    public float y;
    @Description(name = "阴影颜色")
    public String color;


    public ShadowParam(){

    }


    public ShadowParam(float radius, float x, float y, String color){
        this.radius = radius;
        this.x = x;
        this.y = y;
        this.color = color;
    }
}