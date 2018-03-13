package com.panshen.test;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class PicFrameView extends FrameLayout {

    public PicFrameView(@NonNull Context context) {
        super(context);
    }

    public PicFrameView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PicFrameView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Config picFrameConfig = ((PosterView) getParent()).getConfig(i);

            child.layout(picFrameConfig.getLeftTop().x,
                    picFrameConfig.getLeftTop().y,
                    picFrameConfig.getLeftTop().x + child.getMeasuredWidth(),
                    picFrameConfig.getLeftTop().y + child.getMeasuredHeight());


        }
    }
}
