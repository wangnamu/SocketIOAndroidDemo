package com.ufo.socketioandroiddemo.message.repository;

import com.ufo.socketioandroiddemo.message.model.ChatBean;
import com.ufo.socketioandroiddemo.message.model.ChatMessageBean;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by tjpld on 2017/5/9.
 */

public class ChatMessageRepository {

    private static final ChatMessageRepository instance = new ChatMessageRepository();

    public static ChatMessageRepository getInstance() {
        return instance;
    }

    private ChatMessageRepository() {
    }

    public void createChatMessage(List<ChatMessageBean> chatMessageBeans) {





    }


    public void updateChatMessage(ChatMessageBean chatMessageBean) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ChatMessageBean> result = realm.where(ChatMessageBean.class)
                .equalTo("SID",chatMessageBean.getSID()).findAll();
        if (result.size() > 0) {
            chatMessageBean.setLocalTime(result.first().getLocalTime());
            realm.beginTransaction();
            realm.insertOrUpdate(chatMessageBean);
            realm.commitTransaction();
        }
    }


    public void createOrUpdateChat(ChatBean chatBean) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ChatBean> result = realm.where(ChatBean.class).equalTo("SID", chatBean.getSID()).findAll();
        if (result.size() > 0) {
            chatBean.setDisplayInRecently(result.first().getDisplayInRecently());
            realm.beginTransaction();
            realm.insertOrUpdate(chatBean);
            realm.commitTransaction();
        } else {
            realm.beginTransaction();
            realm.insert(chatBean);
            realm.commitTransaction();
        }
    }


    public RealmResults<ChatBean> getChat() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ChatBean> beans = realm.where(ChatBean.class)
                .equalTo("DisplayInRecently", true)
                .findAllSorted("Time", Sort.DESCENDING);
        return beans;
    }


    public RealmResults<ChatMessageBean> getChatMessage() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ChatMessageBean> beans = realm.where(ChatMessageBean.class).findAllSorted("Time", Sort.DESCENDING);
        return beans;
    }


    public RealmResults<ChatBean> getContact() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ChatBean> beans = realm.where(ChatBean.class).findAllSorted("Name", Sort.ASCENDING);
        return beans;
    }


    public RealmResults<ChatMessageBean> getChatMessageByChatID(String chatID) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ChatMessageBean> beans = realm.where(ChatMessageBean.class)
                .equalTo("ChatID",chatID)
                .findAllSorted("LocalTime",Sort.ASCENDING);
        return beans;
    }


}
