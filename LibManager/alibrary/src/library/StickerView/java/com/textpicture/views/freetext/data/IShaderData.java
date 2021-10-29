package com.textpicture.views.freetext.data;

import java.io.Serializable;

/**
 * Created by zhaolei on 2017/10/18.
 */

public interface IShaderData extends Serializable {

    ShaderParam toShaderParam();
}
