package com.example.forrestsu.microchat.presenter;

import com.example.forrestsu.microchat.base.BasePresenter;
import com.example.forrestsu.microchat.beans.Friend;
import com.example.forrestsu.microchat.model.GetAllContactsModel;
import com.example.forrestsu.microchat.model.callback.RequestCallback;
import com.example.forrestsu.microchat.view.ContactsView;

import java.util.List;

public class ContactsPresenter extends BasePresenter<ContactsView> {

    private static final String TAG = "ContactsPresenter";

    /**
     * 获取所有的联系人
     */
    public void getAllContacts() {

        if (!isViewAttached()) {
            return;
        }

        getIView().showLoading();

        GetAllContactsModel.getAllContacts(new RequestCallback<List<Friend>>() {
            @Override
            public void onSuccess(List<Friend> list) {
                if (isViewAttached()) {
                    getIView().getContactsSuccess(list);
                }
            }

            @Override
            public void onFailure(String msg) {
                getIView().getContactsFailure();
                getIView().showToast(msg);
            }

            @Override
            public void onError() {
                getIView().getContactsFailure();
                getIView().showToast("获取联系人失败");
            }

            @Override
            public void onComplete() {
                getIView().hideLoading();
            }
        });
    }
}
