package com.textpicture.views.stickerview;

import android.view.MotionEvent;

/**
 * Des:删除角标事件
 * Author:任俊家
 * Date:2018/6/21 10:31
 */

public class DeleteIconEvent implements StickerIconEvent {
  @Override
  public void onActionDown(StickerView stickerView, MotionEvent event) {

  }

  @Override
  public void onActionMove(StickerView stickerView, MotionEvent event) {

  }

  @Override
  public void onActionUp(StickerView stickerView, MotionEvent event) {
    stickerView.removeCurrentSticker();
  }
}
