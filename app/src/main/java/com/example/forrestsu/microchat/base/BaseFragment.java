package com.example.forrestsu.microchat.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public abstract class BaseFragment extends Fragment implements BaseView {

public abstract int getContentViewId();

protected abstract void initAllMembersView(Bundle saveInstanceState);

protected Context mContext;
protected View mRootView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState ) {
        mRootView = inflater.inflate(getContentViewId(), container, false);
        this.mContext = getActivity();
        initAllMembersView(saveInstanceState);
        return mRootView;
    }

    @Override
    public void showToast(final String msg) {
        checkActivityAttached();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void showLoading() {
        //checkActivityAttached();
    }

    @Override
    public void hideLoading() {
        //checkActivityAttached();
    }

    @Override
    public void showError() {
        //checkActivityAttached();
    }

    //检查是否与Activity连接
    protected boolean isAttachedContext() {
        return getActivity() != null;
    }

    //检查是否与Activity连接
    public void checkActivityAttached() {
        if (getActivity() == null) {
            throw new ActivityNotAttachedException();
        }
    }

    public static class ActivityNotAttachedException extends RuntimeException {
        public ActivityNotAttachedException() {
            super("Fragment has disconnected from Activity ! - -.");
        }
    }
}
