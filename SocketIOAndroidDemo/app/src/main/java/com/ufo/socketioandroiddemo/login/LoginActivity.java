package com.ufo.socketioandroiddemo.login;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ufo.socketioandroiddemo.R;
import com.ufo.socketioandroiddemo.mvp.MVPBaseActivity;


public class LoginActivity extends MVPBaseActivity<LoginContract.View, LoginPresenter> implements LoginContract.View {

    Button mButton;
    EditText mEditUserName;
    EditText mEditPassWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initControl();
    }


    private void initControl() {

        mEditUserName = (EditText) findViewById(R.id.edit_username);
        mEditPassWord = (EditText) findViewById(R.id.edit_password);

        mButton = (Button) findViewById(R.id.button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mEditUserName.getText().toString();
                String password = mEditPassWord.getText().toString();

                mPresenter.login(username, password);
            }
        });

    }

    @Override
    public void loginSuccess() {

    }

    @Override
    public void loginFail(String errorMessage) {
        Toast.makeText(this,errorMessage,Toast.LENGTH_LONG).show();
    }
}
