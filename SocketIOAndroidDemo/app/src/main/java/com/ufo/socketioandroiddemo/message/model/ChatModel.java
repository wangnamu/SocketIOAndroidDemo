package com.ufo.socketioandroiddemo.message.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tjpld on 2017/5/9.
 */

public class ChatModel {

    @SerializedName(value = "SID", alternate = {"sid"})
    private String SID;
    @SerializedName(value = "Users", alternate = {"users"})
    private String Users;
    @SerializedName(value = "Name", alternate = {"name"})
    private String Name;
    @SerializedName(value = "Img", alternate = {"img"})
    private String Img;
    @SerializedName(value = "Time", alternate = {"time"})
    private long Time;
    @SerializedName(value = "CreateTime", alternate = {"createTime"})
    private long CreateTime;
    @SerializedName(value = "Body", alternate = {"body"})
    private String Body;
    @SerializedName(value = "ChatType", alternate = {"chatType"})
    private String ChatType;

    //custom in client
    @SerializedName(value = "DisplayInRecently", alternate = {"displayInRecently"})
    private Boolean DisplayInRecently;

    public String getSID() {
        return SID;
    }

    public void setSID(String SID) {
        this.SID = SID;
    }

    public String getUsers() {
        return Users;
    }

    public void setUsers(String users) {
        Users = users;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    public long getTime() {
        return Time;
    }

    public void setTime(long time) {
        Time = time;
    }

    public long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(long createTime) {
        CreateTime = createTime;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public String getChatType() {
        return ChatType;
    }

    public void setChatType(String chatType) {
        ChatType = chatType;
    }

    public Boolean getDisplayInRecently() {
        return DisplayInRecently;
    }

    public void setDisplayInRecently(Boolean displayInRecently) {
        DisplayInRecently = displayInRecently;
    }

    public ChatBean toBean(){

        ChatBean bean = new ChatBean();
        bean.setSID(getSID());
        bean.setUsers(getUsers());
        bean.setName(getName());
        bean.setImg(getImg());
        bean.setTime(getTime());
        bean.setCreateTime(getCreateTime());
        bean.setBody(getBody());
        bean.setChatType(getChatType());
        bean.setDisplayInRecently(getDisplayInRecently());
        return bean;
    }

    public static ChatModel fromBean(ChatBean bean){
        ChatModel model = new ChatModel();
        model.setSID(bean.getSID());
        model.setUsers(bean.getUsers());
        model.setName(bean.getName());
        model.setImg(bean.getImg());
        model.setTime(bean.getTime());
        model.setCreateTime(bean.getCreateTime());
        model.setBody(bean.getBody());
        model.setChatType(bean.getChatType());
        model.setDisplayInRecently(bean.getDisplayInRecently());
        return model;
    }


}
