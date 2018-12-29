package com.example.forrestsu.microchat.presenter;


import com.example.forrestsu.microchat.base.BasePresenter;
import com.example.forrestsu.microchat.model.CountDownModel;
import com.example.forrestsu.microchat.model.callback.CountDownCallback;
import com.example.forrestsu.microchat.view.CountDownView;

public class CountDownPresenter extends BasePresenter<CountDownView> {

    private static final String TAG = "CountDownPresenter";

    private CountDownModel countDownModel;

    /**
     * 启动倒计时
     * @param millisInFuture 预设时间
     * @param countDownInterval 每次减去的时间
     */
    public void startCountDown(long millisInFuture, long countDownInterval) {
        countDownModel = new CountDownModel();
        countDownModel.startCountDown(millisInFuture, countDownInterval, new CountDownCallback() {
            @Override
            public void timeChanged(long latestTime) {
                if (isViewAttached()) {
                    getIView().refreshTime(latestTime);
                }
            }

            @Override
            public void countDownFinished() {
                if (isViewAttached()) {
                    getIView().countDownFinished();
                }
            }
        });
    }

    /**
     * 取消倒计时
     */
    public void cancelCountTime() {
        //注意，此处一定要判空
        if (countDownModel != null) {
            countDownModel.cancelCountDown();
            if (isViewAttached()) {
                getIView().cancelCountDown();
            }
        }
    }
}
