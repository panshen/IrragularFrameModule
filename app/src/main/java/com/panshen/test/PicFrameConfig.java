package com.panshen.test;

import android.graphics.Bitmap;

public class PicFrameConfig extends Config {

    private Bitmap coverBitmap = null;
    private Bitmap imgBitmap = null;
    private Integer imgRes = null;
    private Integer coverRes = null;
    private boolean enableBorder = false;

    public PicFrameConfig() {

    }

    public boolean isEnableBorder() {
        return enableBorder;
    }

    public void setEnableBorder(boolean enableBorder) {
        this.enableBorder = enableBorder;
    }

    public Bitmap getCoverBitmap() {
        return coverBitmap;
    }

    public void setCoverBitmap(Bitmap coverBitmap) {
        this.coverBitmap = coverBitmap;
    }

    public Integer getCoverRes() {
        return coverRes;
    }

    public void setCoverRes(Integer coverRes) {
        this.coverRes = coverRes;
    }

    private PicFrameImageView picFrameImageView;

    public PicFrameImageView getPicFrameImageView() {
        return picFrameImageView;
    }

    public void setPicFrameImageView(PicFrameImageView picFrameImageView) {
        this.picFrameImageView = picFrameImageView;
    }

    public Bitmap getImgBitmap() {
        return imgBitmap;
    }

    public void setImgBitmap(Bitmap imgBitmap) {
        this.imgBitmap = imgBitmap;
    }

    public Integer getImgRes() {
        return imgRes;
    }

    public void setImgRes(Integer imgRes) {
        this.imgRes = imgRes;
    }

}
