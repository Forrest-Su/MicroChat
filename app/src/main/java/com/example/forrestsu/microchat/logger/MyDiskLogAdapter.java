package com.example.forrestsu.microchat.logger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;

import static com.example.forrestsu.microchat.logger.LogUtils.checkNotNull;


public class MyDiskLogAdapter extends DiskLogAdapter {

    @NonNull
    private final FormatStrategy formatStrategy;

    public MyDiskLogAdapter() {
        formatStrategy = MyCsvFormatStrategy.newBuilder().build();
    }

    public MyDiskLogAdapter(String diskPath) {
        formatStrategy = MyCsvFormatStrategy.newBuilder().build(diskPath);
    }

    public MyDiskLogAdapter(@NonNull FormatStrategy formatStrategy) {
        this.formatStrategy = checkNotNull(formatStrategy);
    }

    @Override public boolean isLoggable(int priority, @Nullable String tag) {
        return true;
    }

    @Override public void log(int priority, @Nullable String tag, @NonNull String message) {
        formatStrategy.log(priority, tag, message);
    }
}
