package com.ufo.socketioandroiddemo.message.repository;

import com.ufo.socketioandroiddemo.message.model.ChatBean;
import com.ufo.socketioandroiddemo.message.model.ChatMessageBean;
import com.ufo.socketioandroiddemo.message.model.ChatTypeEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

        List<ChatMessageBean> chatMessageList = new ArrayList<>();

        Map<String, List<ChatMessageBean>> map = group(chatMessageBeans, new GroupBy<String>() {
            @Override
            public String groupby(Object obj) {
                ChatMessageBean c = (ChatMessageBean) obj;
                return c.getChatID();
            }
        });


        for (Map.Entry<String, List<ChatMessageBean>> entry : map.entrySet()) {

            List<ChatMessageBean> list = entry.getValue();
            Collections.sort(list, new Comparator<ChatMessageBean>() {
                @Override
                public int compare(ChatMessageBean c1, ChatMessageBean c2) {
                    if (c1.getTime() > c2.getTime()) {
                        return -1;
                    } else if (c1.getTime() < c2.getTime()) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            });

            chatMessageList.add(list.get(0));

        }


        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        for (ChatMessageBean chatMessageItem : chatMessageList) {
            RealmResults<ChatBean> chatBeanResult = realm.where(ChatBean.class).equalTo("SID", chatMessageItem.getChatID()).findAll();
            if (chatBeanResult.size() > 0) {

                ChatBean chatBean = chatBeanResult.first();

                if (chatBean.getChatType().equals(ChatTypeEnum.Single)) {
                    chatBean.setBody(chatMessageItem.getBody());
                    chatBean.setTime(chatMessageItem.getTime());
                    chatBean.setDisplayInRecently(true);
                } else if (chatBean.getChatType().equals(ChatTypeEnum.Group)) {
                    chatBean.setBody(String.format("%s:%s", chatMessageItem.getNickName(), chatMessageItem.getBody()));
                    chatBean.setTime(chatMessageItem.getTime());
                    chatBean.setDisplayInRecently(true);
                }

            } else {

            }
        }

        realm.insertOrUpdate(chatMessageList);

        realm.commitTransaction();

    }


    public void updateChatMessage(ChatMessageBean chatMessageBean) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ChatMessageBean> result = realm.where(ChatMessageBean.class)
                .equalTo("SID", chatMessageBean.getSID()).findAll();
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
                .equalTo("ChatID", chatID)
                .findAllSorted("LocalTime", Sort.ASCENDING);
        return beans;
    }


    /**
     * list 分组
     *
     * @param colls
     * @param gb
     * @param <T>
     * @param <D>
     * @return
     */
    private static final <T extends Comparable<T>, D> Map<T, List<D>> group(Collection<D> colls, GroupBy<T> gb) {
        if (colls == null || colls.isEmpty()) {
            return null;
        }
        if (gb == null) {
            return null;
        }
        Iterator<D> iter = colls.iterator();
        Map<T, List<D>> map = new HashMap<>();
        while (iter.hasNext()) {
            D d = iter.next();
            T t = gb.groupby(d);
            if (map.containsKey(t)) {
                map.get(t).add(d);
            } else {
                List<D> list = new ArrayList<D>();
                list.add(d);
                map.put(t, list);
            }
        }
        return map;
    }

    private interface GroupBy<T> {
        T groupby(Object obj);
    }


}
