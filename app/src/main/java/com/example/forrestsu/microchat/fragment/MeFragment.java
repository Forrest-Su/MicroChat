package com.example.forrestsu.microchat.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.forrestsu.microchat.ActivityManager;
import com.example.forrestsu.microchat.R;
import com.example.forrestsu.microchat.activity.MainActivity;
import com.example.forrestsu.microchat.base.BaseFragment;
import com.example.forrestsu.microchat.presenter.LoginOutPresenter;
import com.example.forrestsu.microchat.presenter.MePresenter;
import com.example.forrestsu.microchat.view.LoginOutView;
import com.example.forrestsu.microchat.view.MeView;

public class MeFragment extends BaseFragment implements View.OnClickListener, MeView, LoginOutView {

    private static final String TAG = "MeFragment";

    private MePresenter mePresenter;
    private LoginOutPresenter loginOutPresenter;

    private Button loginOutBT;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_me;
    }

    @Override
    public void initAllMembersView(Bundle saveInstanceState) {
        initView();
        initData();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mePresenter.detachView();
        loginOutPresenter.detachView();
    }

    //初始化view
    public void initView() {
        loginOutBT = (Button) mRootView.findViewById(R.id.bt_login_out);
        loginOutBT.setOnClickListener(this);
    }

    //初始化data
    public void initData() {
        mePresenter = new MePresenter();
        mePresenter.attachView(this);
        loginOutPresenter = new LoginOutPresenter();
        loginOutPresenter.attachView(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login_out:
                loginOutPresenter.loginOut();
                break;
            default:
                break;
        }
    }

    @Override
    public void loginOut() {
        Intent toMain = new Intent(getActivity(), MainActivity.class);
        startActivity(toMain);
        ActivityManager.getInstance().finishAll(); //MainActivity没有继承BaseActivity，所以不会被finish
    }

    @Override //显示加载状态
    public void showLoading() {
        //
    }

    @Override //隐藏加载状态
    public void hideLoading() {
        //
    }
}
