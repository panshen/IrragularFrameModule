package com.panshen.test.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.panshen.test.Event;
import com.panshen.test.GifSizeFilter;
import com.panshen.test.ImageConfig;
import com.panshen.test.PicFrameConfig;
import com.panshen.test.PosterView;
import com.panshen.test.R;
import com.panshen.test.TextConfig;
import com.panshen.test.utils.ConvertV2;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class IrregularActivity extends AppCompatActivity {
    PosterView posterLayout;

    int width;
    int height;
    private int REQUEST_CODE_CHOOSE = 0;

    ImageView ivCaptureView;
    Button btCapture;
    Bitmap bitmap = null;
    ConvertV2 convertV2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);

        ivCaptureView = findViewById(R.id.ivCaptureImageView);
        btCapture = findViewById(R.id.btCapture);
        btCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivCaptureView.setImageBitmap(getBitmapFromView(posterLayout));
            }
        });

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.single_irregular_bottom_frame);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        convertV2 = new ConvertV2(671f, 988f, displayMetrics);

        posterLayout = findViewById(R.id.imlayout);
        float ratio = (float) bitmap.getHeight() / (float) bitmap.getWidth();

        width = displayMetrics.widthPixels;
        height = (int) (width * ratio);

        posterLayout.adjustView(width, height);

        /**
         * add a PicFrameImageView using config
         */
        PicFrameConfig picFrameConfig1 = new PicFrameConfig();
        picFrameConfig1.setLeftTop(new Point((int) convertV2.convertWidth(77),
                (int) convertV2.convertHeight(191,height)));

        picFrameConfig1.setRightTop(new Point((int) convertV2.convertWidth(566),
                (int) convertV2.convertHeight(191,height)));

        picFrameConfig1.setLeftBottom(new Point((int) convertV2.convertWidth(77),
                (int) convertV2.convertHeight(728,height)));

        picFrameConfig1.setRightBottom(new Point((int) convertV2.convertWidth(566),
                (int) convertV2.convertHeight(728,height)));

        picFrameConfig1.setCoverRes(R.mipmap.single_irregular_frame);
        posterLayout.addConfig(picFrameConfig1);

        /**
         * add a ImageView using config
         */
        ImageConfig img = new ImageConfig();
        img.setLeftTop(new Point((int) convertV2.convertWidth(372),
                (int) convertV2.convertHeight(198,height)));

        img.setRightTop(new Point((int) convertV2.convertWidth(634),
                (int) convertV2.convertHeight(198,height)));

        img.setLeftBottom(new Point((int) convertV2.convertWidth(371),
                (int) convertV2.convertHeight(345,height)));

        img.setRightBottom(new Point((int) convertV2.convertWidth(633),
                (int) convertV2.convertHeight(345,height)));
        img.setImgRes(R.mipmap.single_irregular_decorate);
        posterLayout.addConfig(img);


        /**
         * add a TextView using config
         */
        TextConfig textConfig = new TextConfig();
        textConfig.setLeftTop(new Point((int) convertV2.convertWidth(121),
                (int) convertV2.convertHeight(769,height)));
        textConfig.setTextColor(Color.BLACK);
        textConfig.setTextContent(new Date().toLocaleString());
        posterLayout.addConfig(textConfig);

        /**
         * add a TextView using config
         */
        TextConfig textConfig2 = new TextConfig();
        textConfig2.setLeftTop(new Point((int) convertV2.convertWidth(121),
                (int) convertV2.convertHeight(812,height)));
        textConfig2.setTextColor(Color.BLACK);
        textConfig2.setTextContent("RunawaYy");
        posterLayout.addConfig(textConfig2);

        posterLayout.setUpImg(bitmap);


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
   public void onEvent(Event event) {

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            Matisse.from(IrregularActivity.this)
                                    .choose(MimeType.allOf())
                                    .countable(true)
                                    .capture(true)
                                    .captureStrategy(
                                            new CaptureStrategy(true, "com.zhihu.matisse.sample.fileprovider"))
                                    .maxSelectable(1)
                                    .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                                    .gridExpectedSize(
                                            getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                                    .thumbnailScale(0.85f)
                                    .imageEngine(new GlideEngine())
                                    .forResult(REQUEST_CODE_CHOOSE);

                        } else {
                            Toast.makeText(IrregularActivity.this, "primission denied", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            Log.e("TAG", Matisse.obtainResult(data).toString());
            if (Matisse.obtainResult(data).size() > 0) {
                Bitmap bitmap = null;
                try {
                    bitmap = getBitmapFormUri(Matisse.obtainResult(data).get(0));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                posterLayout.getEditingPicFrameView().setImageBitmap(bitmap);
            }

        }
    }

    public Bitmap getBitmapFormUri(Uri uri) throws FileNotFoundException, IOException {
        InputStream input = getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);

        if (input != null) {
            input.close();
        }
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1))
            return null;
        //图片分辨率以480x800为标准
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (originalWidth / ww);
        } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (originalHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //比例压缩
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = be;//设置缩放比例
        bitmapOptions.inDither = true;//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        input = getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);

        if (input != null) {
            input.close();
        }

        return compressImage(bitmap);//再进行质量压缩
    }

    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }


    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

}
