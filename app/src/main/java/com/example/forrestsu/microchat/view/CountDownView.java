package com.example.forrestsu.microchat.view;

import com.example.forrestsu.microchat.base.BaseView;

public interface CountDownView extends BaseView {
    void refreshTime(long latestTime);
    void countDownFinished();
    void cancelCountDown();
}
