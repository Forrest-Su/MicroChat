package com.example.forrestsu.microchat.presenter;

import com.example.forrestsu.microchat.base.BasePresenter;
import com.example.forrestsu.microchat.model.BmobDataModel;
import com.example.forrestsu.microchat.model.callback.RequestCallback;
import com.example.forrestsu.microchat.view.BmobDataView;

import cn.bmob.v3.BmobObject;

public class BmobDataPresenter extends BasePresenter<BmobDataView> {

    private static final String TAG = "BmobDataPresenter";

    public void save(BmobObject object) {

        if (!isViewAttached()) {
            return;
        }

        BmobDataModel.save(object, new RequestCallback<String>() {
            @Override
            public void onSuccess(String data) {
                //
            }

            @Override
            public void onFailure(String msg) {
//                if (isViewAttached()) {
//                    getIView().showToast(msg);
//                }
            }

            @Override
            public void onError() {
                //
            }

            @Override
            public void onComplete() {
                //
            }
        });
    }

}
