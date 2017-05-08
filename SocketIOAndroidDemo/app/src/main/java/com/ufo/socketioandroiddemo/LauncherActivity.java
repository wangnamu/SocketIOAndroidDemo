package com.ufo.socketioandroiddemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ufo.socketioandroiddemo.login.LoginActivity;
import com.ufo.socketioandroiddemo.login.UserInfoBean;
import com.ufo.socketioandroiddemo.login.UserInfoRepository;

public class LauncherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        UserInfoRepository userInfoRepository = UserInfoRepository.getInstance();
        UserInfoBean bean = userInfoRepository.currentUser(this);

        if (bean == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }


    }


}
