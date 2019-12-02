package com.example.Katrina.mobileshopdq.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.Katrina.mobileshopdq.R;
import com.example.Katrina.mobileshopdq.commom.BaseActivity;
import com.example.Katrina.mobileshopdq.http.ProgressDialogSubscriber;
import com.example.Katrina.mobileshopdq.http.entity.MemberEntity;
import com.example.Katrina.mobileshopdq.http.presenter.MemberPresenter;
import com.example.Katrina.mobileshopdq.utils.SystemConfig;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
    private EditText et_username;
    private EditText et_pwd;
    private Button bt_login;
    private ImageView iv_back;
    @Override
    public int getContentViewId() { return R.layout.activity_login; }
    void close(){
        finish();
    }
    void login(){
        String userName = et_username.getText().toString().trim();
        String pwd = et_pwd.getText().toString().trim();
        if(TextUtils.isEmpty(userName)){
            toastShort("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(pwd)){
            toastShort("请输入密码");
            return;
        }
        MemberPresenter.login(new ProgressDialogSubscriber<MemberEntity>(this) {
            @Override
            public void onNext(MemberEntity memberEntity) {
                SystemConfig.setLogin(true);
                toastLong("登录成功");
                SystemConfig.setLoginUserName(memberEntity.uname);
                SystemConfig.setLoginUserEmail(memberEntity.email);
                SystemConfig.setLoginUserHead(memberEntity.image);
                setResult(RESULT_OK);
                finish();
            }
        },userName,pwd);

    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        initView();

        et_username =findViewById(R.id.et_username);
        et_pwd = findViewById(R.id.et_pwd);

        bt_login = findViewById(R.id.bt_login);
        iv_back = findViewById(R.id.iv_back);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

}


