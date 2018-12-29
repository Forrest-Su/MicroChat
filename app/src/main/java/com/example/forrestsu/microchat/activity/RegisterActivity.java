package com.example.forrestsu.microchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.forrestsu.microchat.ActivityManager;
import com.example.forrestsu.microchat.R;
import com.example.forrestsu.microchat.base.BaseActivity;
import com.example.forrestsu.microchat.presenter.CountDownPresenter;
import com.example.forrestsu.microchat.presenter.GetVerificationCodePresenter;
import com.example.forrestsu.microchat.presenter.RegisterPresenter;
import com.example.forrestsu.microchat.utils.AppUtils;
import com.example.forrestsu.microchat.view.CountDownView;
import com.example.forrestsu.microchat.view.GetVerificationCodeView;
import com.example.forrestsu.microchat.view.RegisterView;


public class RegisterActivity extends BaseActivity implements View.OnClickListener, RegisterView, GetVerificationCodeView, CountDownView {

    private static final String TAG = "RegisterActivity";

    private Toolbar toolbar;
    private ProgressBar progressBar;
    private EditText phoneNumEt, passwordET, verificationCodeET;
    private Button getVerificationCodeBT, registerBT, loginBT;

    private GetVerificationCodePresenter getCodePresenter;
    private CountDownPresenter countDownPresenter;
    private RegisterPresenter registerPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        initData();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        getCodePresenter.detachView();
        countDownPresenter.detachView();
        registerPresenter.detachView();

        //活动被销毁时取消倒计时，将取消放在解绑之后可以不用执行cancelCountDown()方法
        countDownPresenter.cancelCountTime();
    }


    //初始化view
    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        phoneNumEt = (EditText) findViewById(R.id.et_cellPhoneNum);
        passwordET = (EditText) findViewById(R.id.et_password);
        verificationCodeET = (EditText) findViewById(R.id.et_verificationCode);

        getVerificationCodeBT = (Button) findViewById(R.id.bt_getVerificationCode);
        getVerificationCodeBT.setOnClickListener(this);
        registerBT = (Button) findViewById(R.id.bt_register);
        registerBT.setOnClickListener(this);
        loginBT = (Button) findViewById(R.id.bt_login);
        loginBT.setOnClickListener(this);
    }


    //初始化data
    public void initData() {
        getCodePresenter = new GetVerificationCodePresenter();
        getCodePresenter.attachView(this);
        countDownPresenter = new CountDownPresenter();
        countDownPresenter.attachView(this);
        registerPresenter = new RegisterPresenter();
        registerPresenter.attachView(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_getVerificationCode:
                if (AppUtils.getNetworkInfo(RegisterActivity.this) == 1) {
                    getCodePresenter.getVerificationCode(phoneNumEt.getText().toString());
                }
                break;
            case R.id.bt_register:
                if (AppUtils.getNetworkInfo(RegisterActivity.this) == 1) {
                    registerPresenter.register(
                            phoneNumEt.getText().toString(),
                            passwordET.getText().toString(),
                            verificationCodeET.getText().toString());
                }
                break;
            case R.id.bt_login:
                Intent toLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(toLogin);
                break;
            default:
                break;
        }
    }


    @Override //设置获取验证码Button能否点击
    public void setGetCodeBTClickable(final Boolean clickable) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getVerificationCodeBT.setClickable(clickable);
            }
        });
    }

    @Override //设置注册Button能否点击
    public void setRegisterBTClickable(Boolean clickable) {
        registerBT.setClickable(clickable);
    }

    @Override //获取验证码成功
    public void getCodeSuccess() {
        //开始倒计时
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                countDownPresenter.startCountDown(60 * 1000, 1000);
            }
        });
    }

    @Override //注册成功
    public void registerSuccess() {
        ActivityManager.finishAllElse(this);
        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override //刷新倒计时
    public void refreshTime(long latestTime) {
        //String seconds = latestTime / 1000 + "s";
        //getVerificationCodeBT.setText(seconds);
        getVerificationCodeBT.setText(latestTime / 1000 + "s");
    }

    @Override //倒计时结束
    public void countDownFinished() {
        getVerificationCodeBT.setText(getString(R.string.get_verification_code));
        getVerificationCodeBT.setClickable(true);
    }

    @Override //取消倒计时
    public void cancelCountDown() {
        getVerificationCodeBT.setText(getString(R.string.get_verification_code));
        getVerificationCodeBT.setClickable(true);
    }

    @Override //显示加载状态
    public void showLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override //隐藏加载状态
    public void hideLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
