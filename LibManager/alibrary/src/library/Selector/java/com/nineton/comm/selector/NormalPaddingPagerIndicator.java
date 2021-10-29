package com.nineton.comm.selector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.dresses.library.utils.ExtKt;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.model.PositionData;

import java.util.List;

/**
 * @author HUAN
 * @date
 */
public class NormalPaddingPagerIndicator extends View implements IPagerIndicator {
    private int mVerticalPadding;
    private int mHorizontalPadding;
    private int mFillColor;
    private float mRoundRadius;
    private Interpolator mStartInterpolator = new LinearInterpolator();
    private Interpolator mEndInterpolator = new LinearInterpolator();

    private List<PositionData> mPositionDataList;
    private Paint mPaint;

    private RectF mRect = new RectF();
    private boolean mRoundRadiusSet;

    public NormalPaddingPagerIndicator(Context context) {
        super(context);
        init(context);
    }

    public Paint getmPaint() {
        return mPaint;
    }

    private void init(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mVerticalPadding = UIUtil.dip2px(context, 6);
        mHorizontalPadding = UIUtil.dip2px(context, 10);
    }

    /**
     * 画指示器默认背景 2020-01-07
     */
    private void drawBackground(Canvas canvas) {
        for (int index = 0; index < mPositionDataList.size(); index++) {
            mPaint.setColor(Color.parseColor("#F5F5F5"));
            PositionData current = FragmentContainerHelper.getImitativePositionData(mPositionDataList, index);
            RectF mRect = new RectF();
            mRect.left = current.mLeft;
            mRect.right = current.mRight;
            mRect.top = current.mContentTop - mVerticalPadding;
            mRect.bottom = current.mContentBottom + mVerticalPadding;
            canvas.drawRoundRect(mRect, mRoundRadius, mRoundRadius, mPaint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        drawBackground(canvas);
        mPaint.setColor(mFillColor);
        canvas.drawRoundRect(mRect, mRoundRadius, mRoundRadius, mPaint);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mPositionDataList == null || mPositionDataList.isEmpty()) {
            return;
        }

        // 计算锚点位置
        PositionData current = FragmentContainerHelper.getImitativePositionData(mPositionDataList, position);
        PositionData next = FragmentContainerHelper.getImitativePositionData(mPositionDataList, position + 1);
        if (position == 0) {//修改第一个位置显示不完整的bug
            mRect.left = current.mLeft;
            mRect.right = current.mRight;
        } else {
            mRect.left = current.mLeft - mHorizontalPadding + (next.mContentLeft - current.mContentLeft) * mEndInterpolator.getInterpolation(positionOffset);
            mRect.right = current.mRight + mHorizontalPadding + (next.mContentRight - current.mContentRight) * mStartInterpolator.getInterpolation(positionOffset);
        }
        mRect.top = current.mContentTop - mVerticalPadding;
        mRect.bottom = current.mContentBottom + mVerticalPadding;

        if (!mRoundRadiusSet) {
            mRoundRadius = mRect.height() / 2;
        }

        invalidate();
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onPositionDataProvide(List<PositionData> dataList) {
        mPositionDataList = dataList;
    }

    public Paint getPaint() {
        return mPaint;
    }

    public int getVerticalPadding() {
        return mVerticalPadding;
    }

    public void setVerticalPadding(int verticalPadding) {
        mVerticalPadding = verticalPadding;
    }

    public int getHorizontalPadding() {
        return mHorizontalPadding;
    }

    public void setHorizontalPadding(int horizontalPadding) {
        mHorizontalPadding = horizontalPadding;
    }

    public int getFillColor() {
        return mFillColor;
    }

    public void setFillColor(int fillColor) {
        mFillColor = fillColor;
    }

    public float getRoundRadius() {
        return mRoundRadius;
    }

    public void setRoundRadius(float roundRadius) {
        mRoundRadius = roundRadius;
        mRoundRadiusSet = true;
    }

    public Interpolator getStartInterpolator() {
        return mStartInterpolator;
    }

    public void setStartInterpolator(Interpolator startInterpolator) {
        mStartInterpolator = startInterpolator;
        if (mStartInterpolator == null) {
            mStartInterpolator = new LinearInterpolator();
        }
    }

    public Interpolator getEndInterpolator() {
        return mEndInterpolator;
    }

    public void setEndInterpolator(Interpolator endInterpolator) {
        mEndInterpolator = endInterpolator;
        if (mEndInterpolator == null) {
            mEndInterpolator = new LinearInterpolator();
        }
    }
}
