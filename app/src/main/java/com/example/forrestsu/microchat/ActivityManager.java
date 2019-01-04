package com.example.forrestsu.microchat;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/*
用于管理所有活动
提供一系列管理活动的方法
 */
public class ActivityManager {

    private static ActivityManager instance;

    //单例模式
    public static ActivityManager getInstance() {
        if (null == instance) {
            instance = new ActivityManager();
        }
        return instance;
    }

    //创建List用于暂存活动
    private List<Activity> activityList = new ArrayList<>();

    //方法：将活动添加到List
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    //方法：将活动从List中移除
    public void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    //方法：结束所有活动
    public void finishAll() {
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activityList.clear();
    }

    //结束除指定活动外的所有活动
    public void finishAllElse(Activity specifiedActivity) {
        for (Activity activity : activityList) {
            if (activity != specifiedActivity && !activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
