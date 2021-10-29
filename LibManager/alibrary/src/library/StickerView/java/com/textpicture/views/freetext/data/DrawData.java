package com.textpicture.views.freetext.data;

import com.textpicture.views.freetext.annotation.Description;
import com.textpicture.views.freetext.annotation.Img;

import java.io.Serializable;
import java.util.ArrayList;



/**
 * Created by zhaolei on 2017/10/11.
 */

public class DrawData implements Serializable {

    public ArrayList<LineData> backLayers;

    public ArrayList<LineData> foreLayers;

    public ArrayList<LayerData> layers;

    public float width;

    public float height;

    @Description(name = "图片名称",cls = Img.class)
    public String bgImg;

    @Description(name = "颜色")
    public String bgColor;

    public String fontStyle;

    public ShaderParam shaderParam;

//    public AniData aniData;

    public int aniType;


}
