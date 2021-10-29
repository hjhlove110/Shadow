package net.lucode.hackware.magicindicator.custom.titles;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

/**
 * 带颜色渐变和缩放的指示器标题
 * 博客: http://hackware.lucode.net
 * Created by hackware on 2016/6/26.
 */
public class ScaleTransitionPagerTitleView extends ColorTransitionPagerTitleView {
    private float mMinScale = 0.8f;

    public ScaleTransitionPagerTitleView(Context context) {
        super(context);

    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        super.onEnter(index, totalCount, enterPercent, leftToRight);    // 实现颜色渐变
        setPivotY(getHeight());
        setPivotX(getWidth()/2);
        setScaleX(mMinScale + (1.0f - mMinScale) * enterPercent);
        setScaleY(mMinScale + (1.0f - mMinScale) * enterPercent);
        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        setGravity(Gravity.BOTTOM);
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        super.onLeave(index, totalCount, leavePercent, leftToRight);    // 实现颜色渐变
        setPivotY(getHeight());
        setPivotX(getWidth()/2);
        setScaleX(1.0f + (mMinScale - 1.0f) * leavePercent);
        setScaleY(1.0f + (mMinScale - 1.0f) * leavePercent);
        setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        setGravity(Gravity.BOTTOM);

    }

    public float getMinScale() {
        return mMinScale;
    }

    public void setMinScale(float minScale) {
        mMinScale = minScale;
    }

}
