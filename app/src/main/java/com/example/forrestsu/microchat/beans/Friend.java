package com.example.forrestsu.microchat.beans;

import cn.bmob.v3.BmobObject;

public class Friend extends BmobObject {

    private User user;  //用户
    private User friendUser;  //用户的好友

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFriendUser() {
        return friendUser;
    }

    public void setFriendUser(User friendUser) {
        this.friendUser = friendUser;
    }
}
