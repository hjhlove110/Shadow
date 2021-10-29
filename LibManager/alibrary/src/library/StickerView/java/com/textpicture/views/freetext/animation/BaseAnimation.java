package com.textpicture.views.freetext.animation;

import android.animation.ValueAnimator;
import android.widget.TextView;

/**
 * Created by zhaolei on 2017/12/4.
 */

public abstract class BaseAnimation extends ValueAnimator implements ICanvasTransform, ValueAnimator.AnimatorUpdateListener{
    protected TextView tv;


    public BaseAnimation(TextView tv){
        this.tv = tv;
        setFloatValues(0,1);
        addUpdateListener(this);
    }


    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        tv.postInvalidate();
    }
}
