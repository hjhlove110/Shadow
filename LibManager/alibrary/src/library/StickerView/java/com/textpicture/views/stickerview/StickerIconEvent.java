package com.textpicture.views.stickerview;

import android.view.MotionEvent;

/**
 * Des:图片角标事件
 * Author:任俊家
 * Date:2018/6/21 10:32
 */

public interface StickerIconEvent {
  void onActionDown(StickerView stickerView, MotionEvent event);

  void onActionMove(StickerView stickerView, MotionEvent event);

  void onActionUp(StickerView stickerView, MotionEvent event);
}
