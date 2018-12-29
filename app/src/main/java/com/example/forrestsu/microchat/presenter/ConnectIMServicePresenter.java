package com.example.forrestsu.microchat.presenter;


import com.example.forrestsu.microchat.base.BasePresenter;
import com.example.forrestsu.microchat.model.ConnectIMServiceModel;
import com.example.forrestsu.microchat.model.callback.RequestCallback;
import com.example.forrestsu.microchat.view.ConnectIMServiceView;

public class ConnectIMServicePresenter extends BasePresenter<ConnectIMServiceView> {

    private static final String TAG = "ConnectIMServicePresent";

    public void connectIMService() {

        if (!isViewAttached()) {
            return;
        }

        //getIView().showLoading();
        ConnectIMServiceModel.connectIMService(new RequestCallback<String>() {
            @Override
            public void onSuccess(String msg) {
                if (isViewAttached()) {
                    getIView().connectIMSuccess();
                }
            }

            @Override
            public void onFailure(String msg) {
                if (isViewAttached()) {
                    getIView().connectIMFailure();
                    getIView().showToast(msg);
                }
            }

            @Override
            public void onError() {
                if (isViewAttached()) {
                    getIView().connectIMFailure();
                    getIView().showToast("连接IMService出错");
                }
            }

            @Override
            public void onComplete() {
                if (isViewAttached()) {
                    getIView().hideLoading();
                }
            }
        });
    }
}
