package com.ufo.socketioservice;

/**
 * Created by tjpld on 2017/5/1.
 */

public class SocketIONotify {

    private String UserID;//用户ID
    private String SourceDeviceType;//发送方设备类型
    private Object Others;//其它内容

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getSourceDeviceType() {
        return SourceDeviceType;
    }

    public void setSourceDeviceType(String sourceDeviceType) {
        SourceDeviceType = sourceDeviceType;
    }

    public Object getOthers() {
        return Others;
    }

    public void setOthers(Object others) {
        Others = others;
    }


}
