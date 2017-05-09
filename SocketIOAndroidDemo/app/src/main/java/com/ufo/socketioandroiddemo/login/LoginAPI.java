package com.ufo.socketioandroiddemo.login;

import com.ufo.model.ResultModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by tjpld on 2017/5/9.
 */

public interface LoginAPI {
    @FormUrlEncoded
    @POST("login")
    Observable<ResultModel<UserInfoBean>> doLogin(@Field("username") String username, @Field("password") String password);
}
