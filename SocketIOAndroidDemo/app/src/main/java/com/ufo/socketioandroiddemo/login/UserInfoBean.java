package com.ufo.socketioandroiddemo.login;

/**
 * Created by tjpld on 2017/5/5.
 */

public class UserInfoBean {

    private String sid;// 主键
    private String userName;// 用户名
    private String passWord;// 密码
    private String nickName;// 昵称
    private String headPortrait;// 头像
    private  long loginTime;// 最近一次登录时间
    private Boolean inUse;// 正在使用


    public String getSID() {
        return sid;
    }

    public void setSID(String sid) {
        this.sid = sid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }

    public Boolean getInUse() {
        return inUse;
    }

    public void setInUse(Boolean inUse) {
        this.inUse = inUse;
    }


}
