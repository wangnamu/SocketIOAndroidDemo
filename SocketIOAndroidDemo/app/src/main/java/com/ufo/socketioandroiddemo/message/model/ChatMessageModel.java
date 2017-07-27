package com.ufo.socketioandroiddemo.message.model;


import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.ufo.socketioandroiddemo.login.UserInfoRepository;

/**
 * Created by tjpld on 2017/5/9.
 */

public class ChatMessageModel implements Parcelable {

    @SerializedName(value = "SID", alternate = {"sid"})
    private String SID;// 主键
    @SerializedName(value = "SenderID", alternate = {"senderID"})
    private String SenderID;// 发送人ID
    @SerializedName(value = "SenderDeviceToken", alternate = {"senderDeviceToken"})
    private String SenderDeviceToken;// 发送人设备编号
    @SerializedName(value = "Title", alternate = {"title"})
    private String Title;// 标题
    @SerializedName(value = "Body", alternate = {"body"})
    private String Body;//内容
    @SerializedName(value = "Time", alternate = {"time"})
    private long Time;// 时间
    @SerializedName(value = "MessageType", alternate = {"messageType"})
    private String MessageType;// 消息类型(文字、图片、文件、链接、音频、视频、表情等)

    /*--------custom--------*/
    @SerializedName(value = "NickName", alternate = {"nickName"})
    private String NickName;// 真实姓名
    @SerializedName(value = "HeadPortrait", alternate = {"headPortrait"})
    private String HeadPortrait;// 头像
    @SerializedName(value = "ChatID", alternate = {"chatID"})
    private String ChatID;// 会话ID
    @SerializedName(value = "Thumbnail", alternate = {"thumbnail"})
    private String Thumbnail;//缩略图
    @SerializedName(value = "Original", alternate = {"original"})
    private String Original;//原图
    @SerializedName(value = "SendStatusType", alternate = {"sendStatusType"})
    private int SendStatusType;// 发送状态

    /*--------local--------*/
    @SerializedName(value = "LocalTime", alternate = {"localTime"})
    private long LocalTime;


    public ChatMessageModel() {
        this.SendStatusType = SendStatusTypeEnum.Sended;
    }

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

    public String getSenderDeviceToken() {
        return SenderDeviceToken;
    }

    public void setSenderDeviceToken(String senderDeviceToken) {
        SenderDeviceToken = senderDeviceToken;
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

        if (UserInfoRepository.getInstance().currentUser(context) != null) {
            String currentUserID = UserInfoRepository.getInstance().currentUser(context).getSID();
            if (currentUserID.equals(this.SenderID))
                return true;
        }
        return false;

    }

    public ChatMessageBean toBean() {
        ChatMessageBean bean = new ChatMessageBean();
        bean.setSID(getSID());
        bean.setSenderID(getSenderID());
        bean.setSenderDeviceToken(getSenderDeviceToken());
        bean.setTitle(getTitle());
        bean.setBody(getBody());
        bean.setTime(getTime());
        bean.setMessageType(getMessageType());
        bean.setNickName(getNickName());
        bean.setHeadPortrait(getHeadPortrait());
        bean.setChatID(getChatID());
        bean.setThumbnail(getThumbnail());
        bean.setOriginal(getOriginal());
        bean.setSendStatusType(getSendStatusType());
        bean.setLocalTime(getLocalTime());
        return bean;
    }

    public static ChatMessageModel fromBean(ChatMessageBean bean) {
        ChatMessageModel model = new ChatMessageModel();
        model.setSID(bean.getSID());
        model.setSenderID(bean.getSenderID());
        model.setSenderDeviceToken(bean.getSenderDeviceToken());
        model.setTitle(bean.getTitle());
        model.setBody(bean.getBody());
        model.setTime(bean.getTime());
        model.setMessageType(bean.getMessageType());
        model.setNickName(bean.getNickName());
        model.setHeadPortrait(bean.getHeadPortrait());
        model.setChatID(bean.getChatID());
        model.setThumbnail(bean.getThumbnail());
        model.setOriginal(bean.getOriginal());
        model.setSendStatusType(bean.getSendStatusType());
        model.setLocalTime(bean.getLocalTime());
        return model;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(SID);
        dest.writeString(SenderID);
        dest.writeString(SenderDeviceToken);
        dest.writeString(Title);
        dest.writeString(Body);
        dest.writeLong(Time);
        dest.writeString(MessageType);
        dest.writeString(NickName);
        dest.writeString(HeadPortrait);
        dest.writeString(ChatID);
        dest.writeString(Thumbnail);
        dest.writeString(Original);
        dest.writeInt(SendStatusType);
        dest.writeLong(LocalTime);
    }


    public static final Creator<ChatMessageModel> CREATOR = new Creator<ChatMessageModel>() {
        @Override
        public ChatMessageModel createFromParcel(Parcel source) {
            ChatMessageModel chatMessageModel = new ChatMessageModel();

            chatMessageModel.setSID(source.readString());
            chatMessageModel.setSenderID(source.readString());
            chatMessageModel.setSenderDeviceToken(source.readString());
            chatMessageModel.setTitle(source.readString());
            chatMessageModel.setBody(source.readString());
            chatMessageModel.setTime(source.readLong());
            chatMessageModel.setMessageType(source.readString());
            chatMessageModel.setNickName(source.readString());
            chatMessageModel.setHeadPortrait(source.readString());
            chatMessageModel.setChatID(source.readString());
            chatMessageModel.setThumbnail(source.readString());
            chatMessageModel.setOriginal(source.readString());
            chatMessageModel.setSendStatusType(source.readInt());
            chatMessageModel.setLocalTime(source.readLong());

            return chatMessageModel;
        }

        @Override
        public ChatMessageModel[] newArray(int size) {
            return new ChatMessageModel[size];
        }
    };


    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof ChatMessageModel)) {
            return false;
        }

        ChatMessageModel chatMessageModel = (ChatMessageModel) o;

        return SID.equals(chatMessageModel.getSID());
    }


}
