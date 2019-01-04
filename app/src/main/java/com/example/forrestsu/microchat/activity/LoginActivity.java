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
import com.example.forrestsu.microchat.presenter.LoginPresenter;
import com.example.forrestsu.microchat.utils.AppUtils;
import com.example.forrestsu.microchat.view.LoginView;

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginView {

    private static final String TAG = "LoginActivity";

    private Toolbar toolbar;

    private ProgressBar progressBar;

    private EditText phoneNUmET, passwordET;
    private Button loginBT, registerBT;

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        loginPresenter.detachView();
    }


    //初始化view
    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        phoneNUmET = (EditText) findViewById(R.id.et_cellPhoneNum);
        passwordET = (EditText)findViewById(R.id.et_password);

        loginBT = (Button) findViewById(R.id.bt_login);
        loginBT.setOnClickListener(this);
        registerBT = (Button) findViewById(R.id.bt_register);
        registerBT.setOnClickListener(this);
    }

    //初始化data
    public void initData() {
        loginPresenter = new LoginPresenter();
        loginPresenter.attachView(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                if (AppUtils.getNetworkInfo(this) == 1) {
                    loginPresenter.login(phoneNUmET.getText().toString(), passwordET.getText().toString());
                }
                break;
            case R.id.bt_register:
                Intent toRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(toRegister);
                break;
            default:
                break;
        }
    }

    @Override
    public void loginSuccess() {
        ActivityManager.getInstance().finishAllElse(this);
        Intent toHome = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(toHome);
        finish();
    }

    @Override
    public void setLoginBTClickable(Boolean clickable) {
        loginBT.setClickable(clickable);
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

