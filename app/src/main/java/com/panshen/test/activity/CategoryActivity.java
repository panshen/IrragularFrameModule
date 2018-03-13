package com.panshen.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.panshen.test.R;

/**
 * Created by Administrator on 2018/1/30.
 */

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        findViewById(R.id.bt_irrgular).setOnClickListener(this);
        findViewById(R.id.bt_rectangle).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_irrgular:
                startActivity(new Intent(this, IrregularActivity.class));
                break;
            case R.id.bt_rectangle:
                startActivity(new Intent(this, RectangleActivity.class));
                break;
        }
    }
}
