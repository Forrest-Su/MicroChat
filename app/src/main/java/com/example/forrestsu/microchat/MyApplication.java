package com.example.forrestsu.microchat;

import android.app.Application;

import com.example.forrestsu.microchat.bmob.MyMessageHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import cn.bmob.newim.BmobIM;

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    private static MyApplication INSTANCE;

    public static MyApplication INSTANCE() {
        return INSTANCE;
    }

    private void setInstance(MyApplication app) {
        setMyApplication(app);
    }

    private static void setMyApplication(MyApplication a) {
        MyApplication.INSTANCE = a;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initBmob();
    }

    //初始化BmobNewIM SDK
    private void initBmob() {
        setInstance(this);
        //初始化IM SDK，并注册消息接收器
        //判断只有主进程运行的时候才进行初始化，避免资源浪费
        //初始化方法包含了DataSDK的初始化步骤，故无需再初始化DataSDK
        if (getApplicationInfo().packageName.equals(getMyProcessName())){
            BmobIM.init(this);
            BmobIM.registerDefaultMessageHandler(new MyMessageHandler(this));
        }
    }

    /**
     * 获取当前运行的进程名
     * @return 进程名
     */
    public static String getMyProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
