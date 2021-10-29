package com.textpicture.views.stickerview;

import android.view.MotionEvent;

/**
 * Des:镜像翻转图片
 * Author:任俊家
 * Date:2018/6/21 10:31
 */
public abstract class AbstractFlipEvent implements StickerIconEvent {

  @Override
  public void onActionDown(StickerView stickerView, MotionEvent event) {

  }

  @Override
  public void onActionMove(StickerView stickerView, MotionEvent event) {

  }

  @Override
  public void onActionUp(StickerView stickerView, MotionEvent event) {
    stickerView.flipCurrentSticker(getFlipDirection());
  }

  @StickerView.Flip protected abstract int getFlipDirection();
}
