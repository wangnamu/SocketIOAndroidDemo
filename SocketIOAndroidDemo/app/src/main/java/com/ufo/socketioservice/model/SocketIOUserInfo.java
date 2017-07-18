package com.ufo.socketioservice.model;

/**
 * Created by tjpld on 2017/5/1.
 */

public class SocketIOUserInfo {

    private String SID;// 主键
    private String UserName;// 用户名
    private String NickName;// 真实姓名
    private long LoginTime;// 最近一次登录时间
    private String DeviceType;// 设备类型
    private String DeviceToken;// 设备证书
    private Boolean CheckStatus;// 检查是否已经在其它移动设备上登录过

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

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public long getLoginTime() {
        return LoginTime;
    }

    public void setLoginTime(long loginTime) {
        LoginTime = loginTime;
    }

    public String getDeviceType() {
        return DeviceType;
    }

    public void setDeviceType(String deviceType) {
        DeviceType = deviceType;
    }

    public String getDeviceToken() {
        return DeviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        DeviceToken = deviceToken;
    }

    public Boolean getCheckStatus() {
        return CheckStatus;
    }

    public void setCheckStatus(Boolean checkStatus) {
        CheckStatus = checkStatus;
    }


}
