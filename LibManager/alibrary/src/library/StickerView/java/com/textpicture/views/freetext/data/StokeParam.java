package com.textpicture.views.freetext.data;

import android.graphics.Paint;

import com.textpicture.views.freetext.annotation.Description;

import java.io.Serializable;


public class StokeParam implements Serializable {

    @Description(name = "描边宽度")
    public float width;

    @Description(name = "边角锐度" , cls = Paint.Join.class)
    public String join;

}