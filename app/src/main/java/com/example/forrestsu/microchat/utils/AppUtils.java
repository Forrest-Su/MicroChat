package com.example.forrestsu.microchat.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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
}
