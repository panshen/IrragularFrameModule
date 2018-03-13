package com.panshen.test;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

@SuppressLint("AppCompatCustomView")
public class PicFrameImageView extends ImageView implements GestureDetector.OnGestureListener {
    private String TAG = "PicFrameImageView";
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    float lastEvent[] = {0, 0, 0, 0};
    float newRot, d;

    Shader hintBitmapShader;
    Bitmap sourceCoverBitmap;
    Bitmap scaledCoverBitmap;
    private Path borderPath = new Path();

    private GestureDetector gd = null;

    private Paint paint = new Paint();
    private OnSingleTapListener onSingleTapListener;


    private boolean enableBorder = false;

    public PicFrameImageView(Context context) {
        super(context);
        setClickable(true);
        setFocusable(true);
        paint.setAntiAlias(true);

        gd = new GestureDetector(getContext(), this);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.upload);
        hintBitmapShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
    }

    public void setCoverRes(int coverRes) {
        sourceCoverBitmap = BitmapFactory.decodeResource(getResources(), coverRes);
        invalidate();
    }

    public void setImgBitmap(Bitmap bitmap) {
        setImageBitmap(bitmap);
    }

    public void setImgRes(int resId) {
        setImageResource(resId);
    }

    public PicFrameImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PicFrameImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (scaledCoverBitmap == null && sourceCoverBitmap != null)
            scaledCoverBitmap = Bitmap.createScaledBitmap(sourceCoverBitmap, getMeasuredWidth(), getMeasuredHeight(), false);

        borderPath.moveTo(0, 0);
        borderPath.lineTo(getMeasuredWidth(), 0);
        borderPath.lineTo(getMeasuredWidth(), getMeasuredHeight());
        borderPath.lineTo(0, getMeasuredHeight());
        borderPath.lineTo(0, 0);
        borderPath.close();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        paint.setShader(hintBitmapShader);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);
        super.onDraw(canvas);

        paint.setShader(null);
        if (scaledCoverBitmap != null)
            canvas.drawBitmap(scaledCoverBitmap, 0, 0, paint);

        if (enableBorder) {
            paint.setStrokeWidth(20);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(borderPath, paint);
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        setScaleType(ImageView.ScaleType.MATRIX);
        float scale;

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                mode = DRAG;
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                }

                lastEvent = new float[4];
                lastEvent[0] = event.getX(0);
                lastEvent[1] = event.getX(1);
                lastEvent[2] = event.getY(0);
                lastEvent[3] = event.getY(1);
                d = rotation(event);

                break;

            case MotionEvent.ACTION_UP: //first finger lifted
            case MotionEvent.ACTION_POINTER_UP: //second finger lifted
                mode = NONE;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {
                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - start.x, event.getY()
                            - start.y);
                } else if (mode == ZOOM && event.getPointerCount() == 2) {
                    float newDist = spacing(event);
                    matrix.set(savedMatrix);
                    if (newDist > 10f) {
                        scale = newDist / oldDist;
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                    if (lastEvent != null) {
                        newRot = rotation(event);
                        float r = newRot - d;
                        matrix.postRotate(r, getMeasuredWidth() / 2,
                                getMeasuredHeight() / 2);
                    }
                }
                break;
        }

        setImageMatrix(matrix);

        gd.onTouchEvent(event);

        return true;
    }

    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if (onSingleTapListener != null) onSingleTapListener.onTap(this);
        return false;
    }

    public boolean isEnableBorder() {
        return enableBorder;
    }

    public void setEnableBorder(boolean enableBorder) {
        this.enableBorder = enableBorder;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    public void setUpSingleTapListener(OnSingleTapListener onSingleTapListener) {
        this.onSingleTapListener = onSingleTapListener;
    }

    public interface OnSingleTapListener {
        void onTap(PicFrameImageView v);
    }

}
