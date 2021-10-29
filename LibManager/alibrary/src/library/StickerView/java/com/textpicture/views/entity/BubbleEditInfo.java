package com.textpicture.views.entity;

import com.textpicture.views.stickerview.Sticker;

import java.util.List;

/**
 * @author HUAN
 * @date
 */
public class BubbleEditInfo {
    private String background_filePath;
    private List<Sticker> stickerList;

    public String getBackground_filePath() {
        return background_filePath;
    }

    public void setBackground_filePath(String background_filePath) {
        this.background_filePath = background_filePath;
    }

    public List<Sticker> getStickerList() {
        return stickerList;
    }

    public void setStickerList(List<Sticker> stickerList) {
        this.stickerList = stickerList;
    }
}
