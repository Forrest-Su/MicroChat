package com.example.forrestsu.microchat.model;

import android.os.CountDownTimer;

import com.example.forrestsu.microchat.model.callback.CountDownCallback;


public class CountDownModel {

    private CountDownTimer countDownTimer;

    /**
     * 开始倒计时
     * @param millisInFuture 预设时间
     * @param countDownInterval 每次减去的时间
     * @param callback 回调
     */
    public void startCountDown(long millisInFuture, long countDownInterval, final CountDownCallback callback) {
        countDownTimer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                callback.timeChanged(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                callback.countDownFinished();
            }
        };

        countDownTimer.start();
    }

    /**
     * 取消倒计时
     */
    public void cancelCountDown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
