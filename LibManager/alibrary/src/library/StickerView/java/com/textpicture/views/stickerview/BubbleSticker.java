package com.textpicture.views.stickerview;

import  android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import com.textpicture.views.entity.BubbleTemplateInfo;


/**
 * Des:气泡
 * Author:任俊家
 * Date:2018/6/14 19:45
 */
public class BubbleSticker extends Sticker {

    private Drawable drawable;
    private Rect realBounds;
    private BubbleTemplateInfo bubbleTemplateInfo;

    public BubbleSticker(Drawable drawable, BubbleTemplateInfo bubbleTemplateInfo) {
        this.drawable = drawable;
        this.bubbleTemplateInfo = bubbleTemplateInfo;
        realBounds = new Rect(0, 0, getWidth(), getHeight());
    }

    @NonNull
    @Override
    public Drawable getDrawable() {
        return drawable;
    }

    public BubbleTemplateInfo getBubbleTemplateInfo() {
        return bubbleTemplateInfo;
    }

    @Override
    public BubbleSticker setDrawable(@NonNull Drawable drawable) {
        this.drawable = drawable;
        return this;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.save();
        canvas.concat(getMatrix());
        drawable.setBounds(realBounds);
        drawable.draw(canvas);
        canvas.restore();
    }

    @NonNull
    @Override
    public BubbleSticker setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        drawable.setAlpha(alpha);
        return this;
    }

    @Override
    public int getWidth() {
        return drawable.getIntrinsicWidth();
    }

    @Override
    public int getHeight() {
        return drawable.getIntrinsicHeight();
    }

    @Override
    public void release() {
        super.release();
        if (drawable != null) {
            drawable = null;
        }
    }

}
