package com.example.forrestsu.microchat.model.callback;

public interface CountDownCallback {
    void timeChanged(long latestTime);
    void countDownFinished();
}
