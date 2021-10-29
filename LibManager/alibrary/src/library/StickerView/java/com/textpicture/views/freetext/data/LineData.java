package com.textpicture.views.freetext.data;

import android.graphics.Color;

import com.textpicture.views.freetext.annotation.Description;
import com.textpicture.views.freetext.annotation.Img;
import com.textpicture.views.freetext.linedrawer.Gravity;

import java.io.Serializable;


/**
 * Created by zhaolei on 2017/10/17.
 */

public class LineData implements Serializable {

    @Description(name = "相对高度")
    public float rh;

    @Description(name = "位置", cls = Gravity.class)
    public String gravity;

    @Description(name = "图片名称", cls = Img.class)
    public String bitmap;

    @Description(name = "颜色", cls = Color.class)
    public int color;
}
