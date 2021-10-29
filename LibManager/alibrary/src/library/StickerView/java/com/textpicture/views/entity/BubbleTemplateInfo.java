package com.textpicture.views.entity;

import com.textpicture.views.freetext.data.DrawData;

import java.io.Serializable;

/**
 * Des:气泡模板
 * Author:任俊家
 * Date:2018/6/14 17:52
 */
public class BubbleTemplateInfo implements Serializable {
    private String bubbleResIdStr;
    private boolean needVip;
    //这些数据需要本地来添加非必须的
    //处理一些属性的时候再赋值
    //字体
    private String typefaceName;
    //字体
    private String typefaceUrl;
    //字体下标
    private int typefacePosition;
    //大小
    private float textSize;

    //文本内容
    private String content;
    //气泡方向 false--左 true--右
    private boolean isLeft;
    //文本排版
    private int gravity;

    //新版颜色设置
    private int colorType;
    //颜色
    private String textColor;
    //描边的颜色
    private String shadowLayerColor;
    //文本颜色的下标
    private int textColorPosition = 0;
    //特效
    private DrawData drawData;
    //投影效果
    private TextShadowStyleInfo textShadowStyleInfo;
    //排版方式
    private boolean isVertical;

    public BubbleTemplateInfo() {
    }

    public String getTypefaceUrl() {
        return typefaceUrl == null ? "" : typefaceUrl;
    }

    public void setTypefaceUrl(String typefaceUrl) {
        this.typefaceUrl = typefaceUrl;
    }

    public BubbleTemplateInfo(String bubbleResIdStr, boolean needVip) {
        this.bubbleResIdStr = bubbleResIdStr;
        this.needVip = needVip;
    }

    public String getBubbleResIdStr() {
        return bubbleResIdStr;
    }

    public void setBubbleResIdStr(String bubbleResIdStr) {
        this.bubbleResIdStr = bubbleResIdStr;
    }

    public boolean isNeedVip() {
        return needVip;
    }

    public void setNeedVip(boolean needVip) {
        this.needVip = needVip;
    }

    public String getTypefaceName() {
        return typefaceName;
    }

    public void setTypefaceName(String typefaceName) {
        this.typefaceName = typefaceName;
    }

    public int getTypefacePosition() {
        return typefacePosition;
    }

    public void setTypefacePosition(int typefacePosition) {
        this.typefacePosition = typefacePosition;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public String getShadowLayerColor() {
        return shadowLayerColor;
    }

    public void setShadowLayerColor(String shadowLayerColor) {
        this.shadowLayerColor = shadowLayerColor;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTextColorPosition() {
        return textColorPosition;
    }

    public void setTextColorPosition(int textColorPosition) {
        this.textColorPosition = textColorPosition;
    }

    public boolean isLeft() {
        return isLeft;
    }

    public void setLeft(boolean left) {
        isLeft = left;
    }

    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public int getColorType() {
        return colorType;
    }

    public void setColorType(int colorType) {
        this.colorType = colorType;
    }

    public DrawData getDrawData() {
        return drawData;
    }

    public void setDrawData(DrawData drawData) {
        this.drawData = drawData;
    }

    public TextShadowStyleInfo getTextShadowStyleInfo() {
        return textShadowStyleInfo;
    }

    public void setTextShadowStyleInfo(TextShadowStyleInfo textShadowStyleInfo) {
        this.textShadowStyleInfo = textShadowStyleInfo;
    }

    public boolean isVertical() {
        return isVertical;
    }

    public void setVertical(boolean vertical) {
        isVertical = vertical;
    }
}

