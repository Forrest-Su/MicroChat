package com.example.forrestsu.microchat.beans;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {

    private String nickName; //昵称
    private String realName; //真实姓名
    private String avatar; //头像

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
