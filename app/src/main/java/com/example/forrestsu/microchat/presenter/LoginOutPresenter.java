package com.example.forrestsu.microchat.presenter;

import com.example.forrestsu.microchat.base.BasePresenter;
import com.example.forrestsu.microchat.model.LoginOutModel;
import com.example.forrestsu.microchat.view.LoginOutView;

public class LoginOutPresenter extends BasePresenter<LoginOutView> {

    private static final String TAG = "LoginOutPresenter";

    public void loginOut() {
        if (!isViewAttached()) {
            return;
        }
        LoginOutModel.loginOut();
        if (isViewAttached()) {
            getIView().loginOut();
        }
    }
}
