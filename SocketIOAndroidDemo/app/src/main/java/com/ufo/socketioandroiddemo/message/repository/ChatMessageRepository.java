package com.ufo.socketioandroiddemo.message.repository;

/**
 * Created by tjpld on 2017/5/9.
 */

public class ChatMessageRepository {
    private static final ChatMessageRepository ourInstance = new ChatMessageRepository();

    public static ChatMessageRepository getInstance() {
        return ourInstance;
    }

    private ChatMessageRepository() {
    }
}
