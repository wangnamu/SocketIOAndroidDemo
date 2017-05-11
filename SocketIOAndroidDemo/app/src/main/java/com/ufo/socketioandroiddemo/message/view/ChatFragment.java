package com.ufo.socketioandroiddemo.message.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ufo.socketioandroiddemo.R;
import com.ufo.socketioandroiddemo.message.contract.ChatContract;
import com.ufo.socketioandroiddemo.message.presenter.ChatPresenter;
import com.ufo.socketioandroiddemo.mvp.MVPBaseFragment;

/**
 * Created by tjpld on 2017/5/9.
 */

public class ChatFragment extends MVPBaseFragment<ChatContract.View, ChatPresenter> implements ChatContract.View {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);



        return view;
    }

    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        return fragment;
    }

}
