package com.ufo.socketioservice;

import java.util.HashSet;

/**
 * Created by tjpld on 2017/5/1.
 */

public class SocketIOMessage {

    private String SID;// 主键
    private String SenderID;// 发送人ID
    private HashSet<String> ReceiverIDs;// 接收人ID
    private String Title;// 标题
    private String Body;//内容
    private long Time;// 时间
    private String MessageType;// 消息类型(文字、图片、文件、链接、音频、视频、表情等)
    private Boolean IsAlert;// 提醒
    private String Category;// 针对ios10和androidN的快捷回复功能

    private String OthersType;
    private Object Others;// 自定义对象

    public String getSID() {
        return SID;
    }

    public void setSID(String SID) {
        this.SID = SID;
    }

    public String getSenderID() {
        return SenderID;
    }

    public void setSenderID(String senderID) {
        SenderID = senderID;
    }

    public HashSet<String> getReceiverIDs() {
        return ReceiverIDs;
    }

    public void setReceiverIDs(HashSet<String> receiverIDs) {
        ReceiverIDs = receiverIDs;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public long getTime() {
        return Time;
    }

    public void setTime(long time) {
        Time = time;
    }

    public String getMessageType() {
        return MessageType;
    }

    public void setMessageType(String messageType) {
        MessageType = messageType;
    }

    public Boolean getAlert() {
        return IsAlert;
    }

    public void setAlert(Boolean alert) {
        IsAlert = alert;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getOthersType() {
        return OthersType;
    }

    public void setOthersType(String othersType) {
        OthersType = othersType;
    }

    public Object getOthers() {
        return Others;
    }

    public void setOthers(Object others) {
        Others = others;
    }


}
