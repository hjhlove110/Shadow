package net.lucode.hackware.magicindicator.buildins.commonnavigator.titles;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import net.lucode.hackware.magicindicator.buildins.ArgbEvaluatorHolder;


/**
 * 两种颜色过渡的指示器标题
 * 博客: http://hackware.lucode.net
 * Created by hackware on 2016/6/26.
 */
public class ColorTransitionPagerTitleView extends SimplePagerTitleView {

    private int selectBgColor = 0;
    private static int colorTrance = Color.parseColor("#00ffffff");
    public ColorTransitionPagerTitleView(Context context) {
        super(context);

    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        int color = ArgbEvaluatorHolder.eval(leavePercent, mSelectedColor, mNormalColor);
        setTextColor(color);
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        int color = ArgbEvaluatorHolder.eval(enterPercent, mNormalColor, mSelectedColor);
        setTextColor(color);
    }

    @Override
    public void onSelected(int index, int totalCount) {

        if (selectBgColor == 0) return;
        setBackgroundColor(selectBgColor);
    }

    @Override
    public void onDeselected(int index, int totalCount) {
        if (selectBgColor == 0) return;
        setBackgroundColor(colorTrance);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawable = getCompoundDrawables();
        //得到drawableLeft设置的drawable对象
        Drawable leftDrawable = drawable[0];
        if (leftDrawable != null) {
            //得到leftdrawable 的宽度
            int leftDrawableWidth = leftDrawable.getIntrinsicWidth();
            //得到drawable与text之间的间距
            int drawablePadding = getCompoundDrawablePadding();
            //得到文本的宽度
            int textWidth = (int) getPaint().measureText(getText().toString().trim());

            int bodyWidth = leftDrawableWidth + drawablePadding + textWidth;
            canvas.save();
            //将内容在X轴方向平移
            canvas.translate((getWidth() - bodyWidth) / 2, 0);
        }

        super.onDraw(canvas);
    }

    public void setSelectBgColor(int selectBgColor) {
        this.selectBgColor = selectBgColor;
    }
}
