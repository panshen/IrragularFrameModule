package com.panshen.test.utils;

import java.math.BigDecimal;


public class ConvertUtils {

    public static final int REFERENCE_WIDTH = 480;
    public static final int REFERENCE_HEIGHT = 820;

    /**
     * @param inPixle 坐标
     * @param screenWidth View的宽度
     * @return
     */
    public static float convertWidth(int inPixle, int screenWidth) {
        float ratio = (float) screenWidth / REFERENCE_WIDTH;
        return inPixle * ratio;
    }

    /**
     *
     * @param inPixle 坐标
     * @param useingHeight 计算之后的View的高度
     * @return
     */
    public static float convertHeight(int inPixle, float useingHeight) {
        float ratio =  useingHeight / REFERENCE_HEIGHT;
        return inPixle * ratio;
    }

    public static int reverseWidth(int pixleInScreen, int screenWidth) {
        float ratio = (float) screenWidth / REFERENCE_WIDTH;
        return (int) (pixleInScreen / RHU(ratio));
    }

    public static int reverseHeight(int pixleInScreen, int screenHeight) {
        float ratio = (float) screenHeight / REFERENCE_HEIGHT;
        return (int) (pixleInScreen / RHU(ratio));
    }

    private static float RHU(float ratio) {
        BigDecimal bd = new BigDecimal(ratio);
        return bd.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
    }
}
