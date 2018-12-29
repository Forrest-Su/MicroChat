package com.example.forrestsu.microchat.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtil {

    private static final String TAG = "PermissionUtil";

    /**
     * 请求权限
     * @param activity 请求权限的活动
     * @param permissions 需要请求的权限数组
     */
    public static void requestPermission(Activity activity, String[] permissions) {
        List<String> permissionList = new ArrayList<String>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }
        //String newPermissions[] = permissionList.toArray(new String[permissionList.size()]);
        if (!permissionList.isEmpty()) {
            String newPermissions[] = new String[permissionList.size()];
            for (int i = 0; i < permissionList.size(); i++) {
                newPermissions[i] = permissionList.get(i);
            }
            ActivityCompat.requestPermissions(activity, newPermissions, 1);
        }
    }
}
