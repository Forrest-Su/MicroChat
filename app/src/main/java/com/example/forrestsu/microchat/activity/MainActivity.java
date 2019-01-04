package com.example.forrestsu.microchat.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.forrestsu.microchat.ActivityManager;
import com.example.forrestsu.microchat.R;
import com.example.forrestsu.microchat.beans.User;
;
import cn.bmob.v3.BmobUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //MyApplication中初始化BmobNew IM SDK的方法包含了DataSDK的初始化步骤，故无需再初始化DataSDK
        //初始化Bmob SDK
        //Bmob.initialize(this, "959fb03388c65bd09c3c05049385935b");


//        if (BmobUser.isLogin()) {
//            Intent toHomeActivity = new Intent(MainActivity.this, HomeActivity.class);
//            startActivity(toHomeActivity);
//            finish();
//        } else {
//            Intent toLogin = new Intent(MainActivity.this, LoginActivity.class);
//            startActivity(toLogin);
//            finish();
//        }

        Intent intent = getIntent();
        boolean isExit = intent.getBooleanExtra("exit", false);
        if (isExit) {
            finish();
        }

        final User user = BmobUser.getCurrentUser(User.class);
        if (user != null && !TextUtils.isEmpty(user.getObjectId())) {
            Intent toHomeActivity = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(toHomeActivity);
            finish();
        } else {
            Intent toLogin = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(toLogin);
            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            boolean isExit = intent.getBooleanExtra("exit", false);
            if (isExit) {
                ActivityManager.getInstance().finishAll();
                finish();
            }
        }
    }
}
