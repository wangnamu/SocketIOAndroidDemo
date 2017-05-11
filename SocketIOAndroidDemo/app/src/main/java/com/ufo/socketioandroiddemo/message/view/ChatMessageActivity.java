package com.ufo.socketioandroiddemo.message.view;

import android.os.Bundle;

import com.ufo.socketioandroiddemo.message.contract.ChatMessageContract;
import com.ufo.socketioandroiddemo.message.presenter.ChatMessagePresenter;
import com.ufo.socketioandroiddemo.mvp.MVPBaseFragment;

import static android.R.id.message;

/**
 * Created by tjpld on 2017/5/9.
 */

public class ChatMessageActivity extends MVPBaseFragment<ChatMessageContract.View, ChatMessagePresenter> implements ChatMessageContract.View {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}
