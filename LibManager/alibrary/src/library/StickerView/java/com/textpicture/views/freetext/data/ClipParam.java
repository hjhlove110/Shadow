package com.textpicture.views.freetext.data;

import com.textpicture.views.freetext.annotation.Description;

import java.io.Serializable;


/**
 * Created by zhaolei on 2017/10/24.
 */

public class ClipParam implements IDispatchDraw, Serializable {

    @Description(name = "间隔高度")
    public float span;

    @Override
    public LayerData.DispatchDrawParam toDispatchDrawParam() {
        LayerData.DispatchDrawParam param = new LayerData.DispatchDrawParam();
        param.clipParam = this;
        return param;
    }
}
