package com.panshen.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;

public class ImageLayout extends FrameLayout implements Handler.Callback {
    FrameLayout layouPoints;
    ImageView imgBg;
    Context mContext;
    Rect rect;

    Handler handler = null;

    private static final int MOVE_MARKER = 0;

    private int viewIndex = -1;

    private int downX;
    private int downY;

    private int currentMoveX;
    private int currentMoveY;

    private int width;
    private int height;

    Bitmap bit;

    /**
     * 标记滑动方向
     * 保证在同一时间只能左右或上下滑动
     */
    int axis = -1;

    public ImageLayout(Context context) {
        super(context);
    }

    public ImageLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        View imgPointLayout = inflate(context, R.layout.layout_imgview_point, this);

        imgBg = imgPointLayout.findViewById(R.id.bgimg);
        layouPoints = imgPointLayout.findViewById(R.id.pointsContainer);
        rect = new Rect();
        handler = new Handler(Looper.getMainLooper(), this);
        bit = BitmapFactory.decodeResource(getResources(), R.mipmap.emoji);

    }

    public ImageLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //屏幕宽高
    public void setImgBg(int width, int height) {
        ViewGroup.LayoutParams lp = imgBg.getLayoutParams();
        lp.width = width;
        lp.height = height;

        imgBg.setLayoutParams(lp);
        layouPoints.setLayoutParams(lp);

        this.width = width;
        this.height = height;
    }

    public ImageView getImageView() {
        return imgBg;
    }

    public ImageView resetImg(Bitmap bitmap) {
        imgBg.setImageBitmap(bitmap);
        return imgBg;
    }

    public ArrayList<Point> getModifyedCoordinate() {
        ArrayList<Point> ModifyedCoordinate = new ArrayList<>();
        Point p = null;
        for (int i = 0; i < layouPoints.getChildCount(); i++) {
            View v = layouPoints.getChildAt(i);
            int x = (int) v.getX() + v.getWidth() / 2;
            int y = (int) v.getY() + v.getWidth() / 2;
            p = new Point();

            p.set(ConvertUtils.reverseWidth(x, width), ConvertUtils.reverseHeight(y, height));

            ModifyedCoordinate.add(p);
        }

        return ModifyedCoordinate;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int x = currentMoveX = (int) event.getX();
                int y = currentMoveY = (int) event.getY();

                View v1 = layouPoints.getChildAt(viewIndex);
                if (v1 == null || axis == -1) return true;

                if (axis == 0) {
                    if (x > downX) {
                        float nx = v1.getX() + 1;
                        v1.setX(nx);
                    } else {
                        float nx = v1.getX() - 1;
                        v1.setX(nx);
                    }
                } else {
                    if (y > downY) {
                        float ny = v1.getY() + 1;
                        v1.setY(ny);
                    } else {
                        float ny = v1.getY() - 1;
                        v1.setY(ny);
                    }
                }

                return true;
            case MotionEvent.ACTION_DOWN:
                resetPointsScale();
                handler.sendEmptyMessageDelayed(MOVE_MARKER, 100);

                downX = x = (int) event.getX();
                downY = y = (int) event.getY();

                for (int i = 0; i < layouPoints.getChildCount(); i++) {
                    View v = layouPoints.getChildAt(i);
                    v.getHitRect(rect);
                    if (rect.contains(x, y)) {
                        viewIndex = (int) v.getTag();
                        v.setScaleX(1.3f);
                        v.setScaleY(1.3f);
                    }
                }

                return true;
            case MotionEvent.ACTION_UP:
                axis = -1;
                resetPointsScale();
                return true;
        }

        return true;
    }

    void resetPointsScale() {
        for (int i = 0; i < layouPoints.getChildCount(); i++) {
            if (i == viewIndex) continue;
            View v = layouPoints.getChildAt(i);
            v.setScaleX(1);
            v.setScaleY(1);
        }
    }

    public void addPoints(ArrayList<PointSimple> points) {
        layouPoints.removeAllViews();
        for (int i = 0; i < points.size(); i++) {
            ImageView marker = new ImageView(mContext);
            LayoutParams layoutParams = new LayoutParams(bit.getWidth(), bit.getWidth());

            marker.setLayoutParams(layoutParams);
            marker.setTag(i);
            marker.setImageBitmap(bit);
            marker.setX(points.get(i).x - bit.getWidth() / 2);
            marker.setY(points.get(i).y - bit.getWidth() / 2);

            layouPoints.addView(marker);
        }

    }

    public void setPointsInvisible() {
        for (int i = 0; i < layouPoints.getChildCount(); i++) {
            View v = layouPoints.getChildAt(i);
            v.setVisibility(INVISIBLE);
        }
    }

    public int getPointSize() {
        return layouPoints == null ? 0 : layouPoints.getChildCount();
    }

    @Override
    public boolean handleMessage(Message msg) {
        int x = Math.abs(currentMoveX - downX);
        int y = Math.abs(currentMoveY - downY);
        if (x > y) {
            axis = 0;//横向滑动
        } else {
            axis = 1;//竖向滑动
        }
        return false;
    }
}
