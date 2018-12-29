package com.example.forrestsu.microchat.view;

import com.example.forrestsu.microchat.base.BaseView;
import com.example.forrestsu.microchat.beans.Friend;

import java.util.List;

public interface ContactsView extends BaseView {
    void getContactsSuccess(List<Friend> list);
    void getContactsFailure();
}
