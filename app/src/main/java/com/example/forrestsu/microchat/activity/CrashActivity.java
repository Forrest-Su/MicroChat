package com.example.forrestsu.microchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.forrestsu.microchat.ActivityManager;
import com.example.forrestsu.microchat.R;
import com.example.forrestsu.microchat.base.BaseActivity;
import com.example.forrestsu.microchat.beans.CrashInfo;
import com.example.forrestsu.microchat.presenter.BmobDataPresenter;
import com.example.forrestsu.microchat.utils.AppUtils;
import com.example.forrestsu.microchat.view.BmobDataView;

public class CrashActivity extends BaseActivity implements BmobDataView {

    private static final String TAG = "CrashActivity";

    private BmobDataPresenter bmobDataPresenter;

    private CrashInfo crashInfo = new CrashInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);

        initView();

        initData();

        //上传数据
        bmobDataPresenter.save(crashInfo);
    }

    //初始化view
    public void initView() {
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.button) {
                    ActivityManager.getInstance().finishAll();
                }
            }
        });
    }

    //初始化data
    public void initData() {
        bmobDataPresenter = new BmobDataPresenter();
        bmobDataPresenter.attachView(this);

        Intent intent = getIntent();
        Throwable ex = (Throwable) intent.getSerializableExtra("throwable");

        crashInfo.setCause(ex.getMessage());
        crashInfo.setLocalizedMessage(ex.getLocalizedMessage());
        crashInfo.setBrand(AppUtils.getBrand());
        crashInfo.setModel(AppUtils.getModel());
        crashInfo.setSdk(AppUtils.getSDK());
        crashInfo.setId(AppUtils.getId());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        bmobDataPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onManipulateBmobDataSuccess() {
        //
    }
}
