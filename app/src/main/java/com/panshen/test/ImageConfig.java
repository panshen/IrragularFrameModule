package com.panshen.test;

import android.graphics.Bitmap;

public class ImageConfig extends Config {

    private Bitmap imageBitmap = null;
    private int imgRes = 0;

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

}
