package com.panshen.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class PosterView extends FrameLayout implements PicFrameImageView.OnSingleTapListener {

    /**
     * PicFrameView which holds all PicFrameImageView and DecorateView
     */
    private PicFrameView containerView;

    /**
     * the bottom layer View
     */
    private ImageView bottomLayer;

    private ArrayList<Config> configs = new ArrayList<>();

    /**
     * PicFrameView which holds all PicFrameImageView and DecorateView res
     */
    private PicFrameImageView editingFrameImage;

    public PosterView(Context context) {
        super(context);
    }

    public PosterView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        bottomLayer = new ImageView(context);
        containerView = new PicFrameView(context);
        addView(bottomLayer);
        addView(containerView);

    }

    public PosterView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Config getConfig(int index) {
        return configs.get(index);
    }

    public void adjustView(int width, int height) {
        ViewGroup.LayoutParams lp = bottomLayer.getLayoutParams();
        lp.width = width;
        lp.height = height;

        bottomLayer.setLayoutParams(lp);
        containerView.setLayoutParams(lp);

    }

    public void setUpImg(Bitmap bitmap) {
        bottomLayer.setImageBitmap(bitmap);
    }

    public void addConfig(Config config) {

        if (config instanceof PicFrameConfig) {
            initImgConfig((PicFrameConfig) config);
        } else if (config instanceof ImageConfig) {
            initDecorateViews((ImageConfig) config);
        } else if (config instanceof TextConfig) {
            initTextViews((TextConfig) config);
        }

    }

    void initImgConfig(PicFrameConfig config) {
        PicFrameImageView picFrameImageView = new PicFrameImageView(getContext());
        config.setPicFrameImageView(picFrameImageView);
        picFrameImageView.setUpSingleTapListener(this);
        picFrameImageView.setEnableBorder(config.isEnableBorder());
        configs.add(config);

        if (config.getCoverRes() != null)
            picFrameImageView.setCoverRes(config.getCoverRes());

        containerView.addView(picFrameImageView);
        adjustView(picFrameImageView, config);
    }

    void initDecorateViews(ImageConfig config) {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(config.getImgRes());
        containerView.addView(imageView);
        configs.add(config);
        adjustView(imageView, config);
    }

    void initTextViews(TextConfig config) {
        TextView textView = new TextView(getContext());
        textView.setText(config.getTextContent());
        textView.setTextColor(config.getTextColor());
        containerView.addView(textView);
        configs.add(config);
    }

    /**
     * 使用资源的宽高重置View的宽高
     */
    void adjustView(View view, Config config) {
        view.getLayoutParams().width = config.getRightTop().x - config.getLeftTop().x;
        view.getLayoutParams().height = config.getLeftBottom().y - config.getLeftTop().y;
    }

    public PicFrameImageView getEditingPicFrameView() {
        return editingFrameImage;
    }

    @Override
    public void onTap(PicFrameImageView picFrameImageView) {
        editingFrameImage = picFrameImageView;
        EventBus.getDefault().post(new Event());
    }

}
