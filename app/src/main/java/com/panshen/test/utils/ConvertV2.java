package com.panshen.test.utils;

import android.util.DisplayMetrics;

import java.math.BigDecimal;

public class ConvertV2 {

    private float refWidth = 0;
    private float refHeight = 0;

    DisplayMetrics displayMetrics = new DisplayMetrics();

    public DisplayMetrics getDisplayMetrics() {
        return displayMetrics;
    }

    public void setDisplayMetrics(DisplayMetrics displayMetrics) {
        this.displayMetrics = displayMetrics;
    }

    public ConvertV2() {
    }

    /**
     * @param reference_width  the real width of bitmap
     * @param reference_height the real height of bitmap
     * @param displayMetrics   the displayMetrics  of screen
     */
    public ConvertV2(float reference_width, float reference_height, DisplayMetrics displayMetrics) {
        this.refWidth = reference_width;
        this.refHeight = reference_height;
        this.displayMetrics = displayMetrics;
    }

    public float convertWidth(int inPixle) {
        float ratio = displayMetrics.widthPixels / refWidth;
        return inPixle * ratio;
    }

    public float convertHeight(int inPixle,int useingHeight) {
        float ratio = useingHeight / refHeight;
        return inPixle * ratio;
    }

    public int reverseWidth(int pixleInScreen, int screenWidth) {
        float ratio = (float) screenWidth / refWidth;
        return (int) (pixleInScreen / RHU(ratio));
    }

    public int reverseHeight(int pixleInScreen, int screenHeight) {
        float ratio = (float) screenHeight / refHeight;
        return (int) (pixleInScreen / RHU(ratio));
    }

    private float RHU(float ratio) {
        BigDecimal bd = new BigDecimal(ratio);
        return bd.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
    }


    public float getRefWidth() {
        return refWidth;
    }

    public void setRefWidth(int refWidth) {
        this.refWidth = refWidth;
    }

    public float getRefHeight() {
        return refHeight;
    }

    public void setRefHeight(int refHeight) {
        this.refHeight = refHeight;
    }


}
