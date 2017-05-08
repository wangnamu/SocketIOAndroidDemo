package com.ufo.retrofitextend;

import android.content.Context;

import com.ufo.retrofitextend.cookie.CookieManger;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tjpld on 2017/5/8.
 */

public class RetrofitExtendFactory {


    private static RetrofitExtendConfig mConfig;


    public RetrofitExtendFactory() {

    }

    public static void initWithBaseUrl(String url) {
        RetrofitExtendConfig config = new RetrofitExtendConfig();
        config.setBaseUrl(url);
        init(config);
    }

    public static void init(RetrofitExtendConfig config) {
        mConfig = config;
    }


    public static Retrofit createNormalRetrofit(Context context) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (mConfig.isDebugMode()) {
            builder.addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS));
        }


        builder.cookieJar(new CookieManger(context));
        builder.connectTimeout(mConfig.getTimeOut(), TimeUnit.SECONDS);
        builder.writeTimeout(mConfig.getTimeOut(), TimeUnit.SECONDS);
        builder.readTimeout(mConfig.getTimeOut(), TimeUnit.SECONDS);
        OkHttpClient client = builder.build();

        return new Retrofit.Builder()
                .baseUrl(mConfig.getBaseUrl())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

    }


}

