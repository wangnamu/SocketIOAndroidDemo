package com.ufo.socketioandroiddemo.login;

/**
 * Created by tjpld on 2017/5/5.
 */

public class UserInfoBean {

    private String SID;// 主键
    private String UserName;// 用户名
    private String PassWord;// 密码
    private String NickName;// 昵称
    private String HeadPortrait;// 头像
    private  long LoginTime;// 最近一次登录时间
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
