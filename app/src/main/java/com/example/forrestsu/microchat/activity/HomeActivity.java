package com.example.forrestsu.microchat.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.forrestsu.microchat.R;
import com.example.forrestsu.microchat.adapter.MyFragmentAdapter;
import com.example.forrestsu.microchat.base.BaseActivity;
import com.example.forrestsu.microchat.beans.User;
import com.example.forrestsu.microchat.event.RefreshEvent;
import com.example.forrestsu.microchat.presenter.ConnectIMServicePresenter;
import com.example.forrestsu.microchat.utils.IMMLeaks;
import com.example.forrestsu.microchat.view.ConnectIMServiceView;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.listener.ConnectStatusChangeListener;
import cn.bmob.newim.notification.BmobNotificationManager;
import cn.bmob.v3.BmobUser;


public class HomeActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener, ConnectIMServiceView {

    private static final String TAG = "HomeActivity";

    private static final int PAGE_CHAT = 0;
    private static final int PAGE_CONTACTS = 1;
    private static final int PAGE_ME = 2;

    private Toolbar toolbar;
    private ProgressBar progressBar;
    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private RadioButton chatRB, contactsRB, meRB;

    private MyFragmentAdapter myFragmentAdapter;

    private ConnectIMServicePresenter connectIMPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Log.i(TAG, "onCreate: 当前连接状态：" + BmobIM.getInstance().getCurrentStatus().getMsg());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Logger.d("测试，全局");

        test();

        //test2();

        initView();
        initData();
        //登录成功、注册成功或处于登录状态重新打开应用后执行连接IM服务器的操作
        connectIMPresenter.connectIMService();

        //监听连接状态，可通过BmobIM.getInstance().getCurrentStatus()来获取当前的长连接状态
        BmobIM.getInstance().setOnConnectStatusChangeListener(new ConnectStatusChangeListener() {
            @Override
            public void onChange(ConnectionStatus status) {
//                Log.i(TAG, "onChange:当前连接状态：" + BmobIM.getInstance().getCurrentStatus().getMsg());
                EventBus.getDefault().post(new RefreshEvent());
            }
        });

        //解决leancanary提示InputMethodManager内存泄露的问题
        IMMLeaks.fixFocusedViewLeak(getApplication());

        EventBus.getDefault().register(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        //每次进来应用都检查会话和好友请求的情况
        //checkRedPoint();
        //进入应用后，通知栏应取消
        BmobNotificationManager.getInstance(this).cancelNotification();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        connectIMPresenter.detachView();
        //清理导致内存泄露的资源
        BmobIM.getInstance().clear();
        EventBus.getDefault().unregister(this);
    }

    //初始化view
    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ActionBar actionBar = getSupportActionBar();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        myFragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(myFragmentAdapter);
        viewPager.setCurrentItem(PAGE_CHAT);
        viewPager.addOnPageChangeListener(this);

        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(this);

        chatRB = (RadioButton) findViewById(R.id.rb_chat);
        contactsRB = (RadioButton) findViewById(R.id.rb_contacts);
        meRB = (RadioButton) findViewById(R.id.rb_me);

        chatRB.setChecked(true);
    }

    //初始化data
    public void initData() {
        connectIMPresenter = new ConnectIMServicePresenter();
        connectIMPresenter.attachView(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_chat:
                viewPager.setCurrentItem(PAGE_CHAT);
                break;
            case R.id.rb_contacts:
                viewPager.setCurrentItem(PAGE_CONTACTS);
                break;
            case R.id.rb_me:
                viewPager.setCurrentItem(PAGE_ME);
                break;
        }
    }

    @Override //OnPageChangeListener的回调
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override //OnPageChangeListener的回调
    public void onPageSelected(int position) {
    }

    @Override //OnPageChangeListener的回调
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (state == 2) {
            switch (viewPager.getCurrentItem()) {
                case PAGE_CHAT:
                    chatRB.setChecked(true);
                    break;
                case PAGE_CONTACTS:
                    contactsRB.setChecked(true);
                    break;
                case PAGE_ME:
                    meRB.setChecked(true);
                    break;
            }
        }
    }

    @Override //显示加载状态
    public void showLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override //隐藏加载状态
    public void hideLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override //连接IM服务器成功
    public void connectIMSuccess() {
//        Log.i(TAG, "connectIMSuccess: 当前连接状态" + BmobIM.getInstance().getCurrentStatus().getMsg());
        final User user = BmobUser.getCurrentUser(User.class);
        BmobIM.getInstance().
                updateUserInfo(new BmobIMUserInfo(user.getObjectId(),
                        user.getUsername(), user.getAvatar()));
        EventBus.getDefault().post(new RefreshEvent());
    }

    @Override //连接IM服务器失败
    public void connectIMFailure() {
        //
    }

    /**
     * 注册消息接收事件
     *
     * @param event
     */
    //通知有在线消息接收
    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        //checkRedPoint();
        BmobIMMessage msg = event.getMessage();
    }


    //Test
    public void test() {
        String str = null;
        str.equals("test");
    }

    //Test2
    public void test2() {
        for (int i = 0; i < 100; i++) {
            int current = i;
            Log.i(TAG, "test2: 测试2：" + current);
        }
    }
}
