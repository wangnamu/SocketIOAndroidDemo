package com.ufo.socketioandroiddemo.message.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tjpld on 2017/5/9.
 */


public class ChatBean extends RealmObject {

    @PrimaryKey
    private String SID;

    private String Users;
    private String Name;
    private String Img;
    private long Time;
    private long CreateTime;
    private String Body;
    private String ChatType;

    //custom in client
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


}


