package com.panshen.test;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageLayout im_layout;
    int width;
    int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        im_layout = findViewById(R.id.imlayout);

        width = displayMetrics.widthPixels;
        //写死了长宽比 如果后台不传固定分辨率图片的话 ， 就需要吧图片分辨率先传过来
        height = (int) (width * 1.7f);
        im_layout.setImgBg(width, height);

        ArrayList<PointSimple> points = new ArrayList<>();

        points.add(new PointSimple((int) ConvertUtils.convertWidth(78, displayMetrics.widthPixels),
                (int) ConvertUtils.convertHeight(83, height)));

        points.add(new PointSimple((int) ConvertUtils.convertWidth(400, displayMetrics.widthPixels),
                (int) ConvertUtils.convertHeight(84, height)));

        points.add(new PointSimple((int) ConvertUtils.convertWidth(77, displayMetrics.widthPixels),
                (int) ConvertUtils.convertHeight(416, height)));

        points.add(new PointSimple((int) ConvertUtils.convertWidth(400, displayMetrics.widthPixels),
                (int) ConvertUtils.convertHeight(416, height)));

//        points.add(new PointSimple((int) ConvertUtils.convertWidth(204, displayMetrics.widthPixels),
//                (int) ConvertUtils.convertHeight(200, height)));
//
//        points.add(new PointSimple((int) ConvertUtils.convertWidth(464, displayMetrics.widthPixels),
//                (int) ConvertUtils.convertHeight(160, height)));
//
//        points.add(new PointSimple((int) ConvertUtils.convertWidth(420, displayMetrics.widthPixels),
//                (int) ConvertUtils.convertHeight(210, height)));
//
//        points.add(new PointSimple((int) ConvertUtils.convertWidth(309, displayMetrics.widthPixels),
//                (int) ConvertUtils.convertHeight(277, height)));
//
//        points.add(new PointSimple((int) ConvertUtils.convertWidth(76, displayMetrics.widthPixels),
//                (int) ConvertUtils.convertHeight(421, height)));
//
//        points.add(new PointSimple((int) ConvertUtils.convertWidth(111, displayMetrics.widthPixels),
//                (int) ConvertUtils.convertHeight(564, height)));
//
//        points.add(new PointSimple((int) ConvertUtils.convertWidth(477, displayMetrics.widthPixels),
//                (int) ConvertUtils.convertHeight(588, height)));

        im_layout.addPoints(points);
        im_layout.resetImg(BitmapFactory.decodeResource(getResources(), R.mipmap.zzh_twly));

    }
}
