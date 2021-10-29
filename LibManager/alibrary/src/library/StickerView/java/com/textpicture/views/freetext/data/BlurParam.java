package com.textpicture.views.freetext.data;

import android.graphics.BlurMaskFilter;

import com.textpicture.views.freetext.annotation.Description;

import java.io.Serializable;


public class BlurParam implements Serializable {

        @Description(name = "半径")
        public float radius;

        @Description(name = "模糊方式",cls = BlurMaskFilter.Blur.class)
        public String blur;
    }