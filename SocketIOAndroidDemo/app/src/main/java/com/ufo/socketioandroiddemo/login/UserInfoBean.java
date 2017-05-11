package com.ufo.socketioandroiddemo.login;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tjpld on 2017/5/5.
 */

public class UserInfoBean {

    @SerializedName(value = "SID", alternate = {"sid"})
    private String SID;// 主键
    @SerializedName(value = "UserName", alternate = {"userName"})
    private String UserName;// 用户名
    @SerializedName(value = "PassWord", alternate = {"passWord"})
    private String PassWord;// 密码
    @SerializedName(value = "NickName", alternate = {"nickName"})
    private String NickName;// 昵称
    @SerializedName(value = "HeadPortrait", alternate = {"headPortrait"})
    private String HeadPortrait;// 头像
    @SerializedName(value = "LoginTime", alternate = {"loginTime"})
    private  long LoginTime;// 最近一次登录时间
    @SerializedName(value = "InUse", alternate = {"inUse"})
    private Boolean InUse;// 正在使用


    public String getSID() {
        return SID;
    }

    public void setSID(String SID) {
        this.SID = SID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getHeadPortrait() {
        return HeadPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        HeadPortrait = headPortrait;
    }

    public long getLoginTime() {
        return LoginTime;
    }

    public void setLoginTime(long loginTime) {
        LoginTime = loginTime;
    }

    public Boolean getInUse() {
        return InUse;
    }

    public void setInUse(Boolean inUse) {
        InUse = inUse;
    }


}
