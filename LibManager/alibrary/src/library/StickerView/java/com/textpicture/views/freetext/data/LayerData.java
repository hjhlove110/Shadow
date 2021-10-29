package com.textpicture.views.freetext.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zhaolei on 2017/10/10.
 */

public class LayerData implements Serializable {

    public static final int TYPE_MULTI = 2;
    public static final int TYPE_IMG = 1;
    public static final int TYPE_TXT = 0;

    public LayerData(){

    }


    /**
     * type: "img" , "txt","multi"*/
    public int type;

    public String name;

    public ArrayList<LayerData> layerDatas;

    public float offsetX,offsetY,degree,scale;

    public IndexParam<String> imgs;

    public IndexParam<String> colors;

    public PaintParam paintParam;

    public DispatchDrawParam drawParam;

    public static class DispatchDrawParam implements Serializable {
        public ClipParam clipParam;
        public OffsetParam offsetParam;
    }

    public static class PaintParam implements Serializable {
        public float relativeSize;
        public String color;
        public IndexParam<String> colors;
        public String font;
        public String fontStyle;
        public String style;
        public StokeParam stokeParam;
        public BlurParam blurParam;
        public ShaderParam shaderParam;
        public ShadowParam shadowParam;
    }







}
