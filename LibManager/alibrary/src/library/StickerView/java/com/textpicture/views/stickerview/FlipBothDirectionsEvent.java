package com.textpicture.views.stickerview;

/**
 * Des:水平和竖直翻转
 * Author:任俊家
 * Date:2018/6/21 10:31
 */

public class FlipBothDirectionsEvent extends AbstractFlipEvent {

  @Override
  @StickerView.Flip protected int getFlipDirection() {
    return StickerView.FLIP_VERTICALLY | StickerView.FLIP_HORIZONTALLY;
  }
}
