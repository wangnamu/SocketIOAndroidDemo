package com.ufo.socketioandroiddemo.message.model;


import android.content.Context;

import com.ufo.socketioandroiddemo.login.UserInfoRepository;

/**
 * Created by tjpld on 2017/5/9.
 */

public class ChatMessageModel {

    private String SID;// 主键

    private String SenderID;// 发送人ID
    private String Title;// 标题
    private String Body;//内容
    private long Time;// 时间
    private String MessageType;// 消息类型(文字、图片、文件、链接、音频、视频、表情等)
    /*--------custom--------*/
    private String NickName;// 真实姓名
    private String HeadPortrait;// 头像
    private String ChatID;// 会话ID
    private String Thumbnail;//缩略图
    private String Original;//原图

    private int SendStatusType;// 发送状态

    /*--------local--------*/
    private long LocalTime;


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

    public String getChatID() {
        return ChatID;
    }

    public void setChatID(String chatID) {
        ChatID = chatID;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public String getOriginal() {
        return Original;
    }

    public void setOriginal(String original) {
        Original = original;
    }

    public int getSendStatusType() {
        return SendStatusType;
    }

    public void setSendStatusType(int sendStatusType) {
        SendStatusType = sendStatusType;
    }

    public long getLocalTime() {
        return LocalTime;
    }

    public void setLocalTime(long localTime) {
        LocalTime = localTime;
    }


    public boolean isHost(Context context) {

        String currentUserID = UserInfoRepository.getInstance().currentUser(context).getSID();
        if (currentUserID.equals(this.SenderID))
            return true;
        return false;

    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
