package com.ufo.socketioandroiddemo.message.api;

import com.ufo.model.ResultModel;
import com.ufo.socketioandroiddemo.login.UserInfoBean;
import com.ufo.socketioandroiddemo.message.model.ChatMessageModel;
import com.ufo.socketioandroiddemo.message.model.ChatModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by tjpld on 2017/5/11.
 */

public interface MessageAPI {

    @GET("chatMessageList")
    Call<List<ChatMessageModel>> chatMessageListSyc
            (@Query("userID") String userID,
             @Query("last") long last,
             @Query("current") long current);

    @GET("chatList")
    Call<List<ChatModel>> chatListSyc
            (@Query("userID") String userID,
             @Query("last") long last,
             @Query("current") long current);

}
