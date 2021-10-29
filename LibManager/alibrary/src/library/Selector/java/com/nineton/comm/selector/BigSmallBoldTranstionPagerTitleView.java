package com.nineton.comm.selector;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

/**
 * Created by Administrator on 2018/6/25.
 * 选中的时候字体变粗变大
 * MagicIndicator  效果
 */

public class BigSmallBoldTranstionPagerTitleView extends ColorTransitionPagerTitleView {
    private int textSize = 17;
    private int bigTextSize = 19;
    public BigSmallBoldTranstionPagerTitleView(Context context) {
        super(context);
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        super.onEnter(index, totalCount, enterPercent, leftToRight);    // 实现颜色渐变
        setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, bigTextSize);
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        super.onLeave(index, totalCount, leavePercent, leftToRight);    // 实现颜色渐变
        setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setBigTextSize(int bigTextSize) {
        this.bigTextSize = bigTextSize;
    }
}
