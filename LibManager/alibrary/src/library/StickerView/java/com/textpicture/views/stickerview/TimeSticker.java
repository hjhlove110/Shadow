package com.textpicture.views.stickerview;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

/**
 * Des:事件贴纸
 * Author:任俊家
 * Date:2018/6/21 10:34
 */
public class TimeSticker extends Sticker {

    private Drawable drawable;
    private Rect realBounds;

    public TimeSticker(Drawable drawable) {
        this.drawable = drawable;
        realBounds = new Rect(0, 0, getWidth(), getHeight());
    }

    @NonNull
    @Override
    public Drawable getDrawable() {
        return drawable;
    }

    @Override
    public TimeSticker setDrawable(@NonNull Drawable drawable) {
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
    public TimeSticker setAlpha(@IntRange(from = 0, to = 255) int alpha) {
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
