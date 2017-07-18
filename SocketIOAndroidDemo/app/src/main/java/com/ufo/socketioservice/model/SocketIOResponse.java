package com.ufo.socketioservice.model;

/**
 * Created by tjpld on 2017/7/18.
 */

public class SocketIOResponse {

    private Boolean IsSuccess;
    private String Message;

    public Boolean getIsSuccess() {
        return IsSuccess;
    }
    public void setIsSuccess(Boolean isSuccess) {
        IsSuccess = isSuccess;
    }
    public String getMessage() {
        return Message;
    }
    public void setMessage(String message) {
        Message = message;
    }

}
