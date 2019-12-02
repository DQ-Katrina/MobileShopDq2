package com.example.Katrina.mobileshopdq.activity;

import android.view.View;
import android.widget.TextView;

import com.example.Katrina.mobileshopdq.R;
import com.example.Katrina.mobileshopdq.commom.BaseActivity;

public class MyOrderActivity extends BaseActivity {

    TextView tvTitle;

    @Override
    public int getContentViewId() { return R.layout.activity_my_order; }

    @Override
    protected void initView() {
        super.initView();
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText("我的订单");


        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
    }
    void close(){ finish();}
}
