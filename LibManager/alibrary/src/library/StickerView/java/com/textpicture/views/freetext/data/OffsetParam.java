package com.textpicture.views.freetext.data;

import com.textpicture.views.freetext.annotation.Description;

import java.io.Serializable;


/**
 * Created by zhaolei on 2017/10/24.
 */

public class OffsetParam implements IDispatchDraw, Serializable {

    @Description(name = "位置信息，与偏移量一一对应")
    public float[] positions;

    @Description(name = "每个位置的偏移量")
    public float[] offsets;

    @Override
    public LayerData.DispatchDrawParam toDispatchDrawParam() {
        LayerData.DispatchDrawParam param = new LayerData.DispatchDrawParam();
        param.offsetParam = this;
        return param;
    }
}
