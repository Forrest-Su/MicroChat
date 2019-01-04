package com.example.forrestsu.microchat.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import java.util.HashMap;
import java.util.Map;

public class AppUtils {

    public static int getNetworkInfo(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        //此处一定要判空，因为关闭网络连接后是无法获取NetworkInfo对象的
        if (networkInfo != null && networkInfo.isConnected()) {
            switch (networkInfo.getSubtype()) {
                case 14:
                    break;
                //case ...
                default:
                    break;
            }
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 获取品牌
     * @return
     */
    public static String getBrand() {
        return Build.BRAND;
    }

    /**
     *获取设备型号
     * @return
     */
    public static String getModel() {
        return Build.MODEL;
    }

    /**
     *获取固件id
     * @return
     */
    public static String getId() {
        return Build.ID;
    }

    /**
     *获取SDK_INT
     * @return
     */
    public static int getSDK() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取relaease
     * @return
     */
    public static String getRelease() {
        return Build.VERSION.RELEASE;
    }


    /**
     * 获取设备信息
     * @param context
     * @return
     */
    public static Map<String, String> getDeviceInfo(Context context) {
        //存储设备信息
        Map<String, String> deviceInfoMap = new HashMap<>();

        deviceInfoMap.put("brand", getBrand());  //品牌
        deviceInfoMap.put("model", getModel()); //型号
        deviceInfoMap.put("id", getId()); //固件版本号
        deviceInfoMap.put("release", getRelease()); //Android版本
        deviceInfoMap.put("sdk", String.valueOf(getSDK())); //SDK

        //获取versionName,versionCode
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                int versionCode = pi.versionCode;
                deviceInfoMap.put("versionName", versionName);
                deviceInfoMap.put("versionCode", String.valueOf(versionCode));
            }
        } catch (PackageManager.NameNotFoundException e) {
//            log.error("an error occured when collect package info", e);
        }
//        //获取所有系统信息
//        Field[] fields = Build.class.getDeclaredFields();
//        for (Field field : fields) {
//            try {
//                field.setAccessible(true);
//                paramsMap.put(field.getName(), field.get(null).toString());
//            } catch (Exception e) {
////                log.error("an error occured when collect crash info", e);
//            }
//        }
        return deviceInfoMap;
    }
}
