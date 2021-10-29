package com.textpicture.views.stickerview;

import android.view.MotionEvent;

/**
 * Des:缩放事件
 * Author:任俊家
 * Date:2018/6/21 10:34
 */

public class ZoomIconEvent implements StickerIconEvent {
    @Override
    public void onActionDown(StickerView stickerView, MotionEvent event) {

    }

    @Override
    public void onActionMove(StickerView stickerView, MotionEvent event) {
        stickerView.zoomAndRotateCurrentSticker(event);
    }

    @Override
    public void onActionUp(StickerView stickerView, MotionEvent event) {
        if (stickerView != null && stickerView.getCurrentSticker() != null && stickerView.getOnStickerOperationListener() != null) {
            stickerView.getOnStickerOperationListener()
                    .onStickerZoomFinished(stickerView.getCurrentSticker());
        }
    }
}
