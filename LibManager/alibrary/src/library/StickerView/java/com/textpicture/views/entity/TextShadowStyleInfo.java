package com.textpicture.views.entity;

import java.io.Serializable;

/**
 * Des:文字特效
 * Author:任俊家
 * Date:2018/9/14 18:03
 */
public class TextShadowStyleInfo implements Serializable {
    private float radius;
    private float offX;
    private float offY;
    private String shadowColor;
    private String textColor;

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getOffX() {
        return offX;
    }

    public void setOffX(float offX) {
        this.offX = offX;
    }

    public float getOffY() {
        return offY;
    }

    public void setOffY(float offY) {
        this.offY = offY;
    }

    public String getShadowColor() {
        return shadowColor;
    }

    public void setShadowColor(String shadowColor) {
        this.shadowColor = shadowColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }
}
