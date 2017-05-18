package com.ufo.socketioandroiddemo.message.api;

import com.ufo.socketioandroiddemo.message.model.ChatMessageModel;
import com.ufo.socketioandroiddemo.message.model.ChatModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;


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


    @FormUrlEncoded
    @POST("sendText")
    Observable<ChatMessageModel> sendTextAsyc
            (@Field("chatID") String chatID,
             @Field("body") String body,
             @Field("messageID") String messageID,
             @Field("senderID") String senderID);
}
