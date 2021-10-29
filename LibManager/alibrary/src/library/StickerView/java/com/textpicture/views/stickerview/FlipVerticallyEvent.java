package com.textpicture.views.stickerview;

/**
 * Des:竖直翻转
 * Author:任俊家
 * Date:2018/6/21 10:32
 */
public class FlipVerticallyEvent extends AbstractFlipEvent {

  @Override
  @StickerView.Flip protected int getFlipDirection() {
    return StickerView.FLIP_VERTICALLY;
  }
}
