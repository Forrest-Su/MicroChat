package com.example.forrestsu.microchat.base;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.forrestsu.microchat.ActivityManager;


public class BaseActivity extends AppCompatActivity implements BaseView {

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        //活动被创建时会被添加到list中统一管理
        ActivityManager.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        //当活动被销毁时会从list中被移除
        ActivityManager.getInstance().removeActivity(this);
        super.onDestroy();

    }

    @Override
    public void showToast(final String msg) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void showLoading() {
        //
    }

    @Override
    public void hideLoading() {
        //
    }

    @Override
    public void showError() {
        //
    }

    @Override
    public Context getContext() {
        return BaseActivity.this;
    }


    @Override //权限请求回调
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            showToast("请授予权限，否则程序无法运行");
                            return;
                        }
                    }
                } else {
                    showToast("未知错误");
                    finish();
                }
                break;
            default:
                break;
        }
    }

}
