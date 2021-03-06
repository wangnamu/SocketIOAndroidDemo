package com.ufo.socketioandroiddemo.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ufo.socketioandroiddemo.MainActivity;
import com.ufo.socketioandroiddemo.R;
import com.ufo.socketioandroiddemo.mvp.MVPBaseActivity;
import com.ufo.socketioservice.SocketIOService;
import com.ufo.tools.MyChat;
import com.ufo.tools.RealmConfig;


public class LoginActivity extends MVPBaseActivity<LoginContract.View, LoginPresenter> implements LoginContract.View {

    Button mButton;
    EditText mEditUserName;
    EditText mEditPassWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initControl();

        boolean isKickedOff = getIntent().getBooleanExtra("isKickedOff", false);
        String msg = getIntent().getStringExtra("msg");
        if (isKickedOff) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage(msg)
                    .setNegativeButton("确定", null);
            builder.show();
        }

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
    public void loginSuccess(UserInfoBean userInfoBean) {

        RealmConfig.setUp(getApplicationContext(), userInfoBean.getUserName());

        getRecent();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFail(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }


    private void startSocketIOService() {
        Intent intent = new Intent(getApplicationContext(), SocketIOService.class);
        intent.putExtra("CheckStatus", false);
        startService(intent);
    }

    private void getRecent() {
        MyChat.getInstance().getRecent(getApplicationContext(), new MyChat.getRecentCallback() {
            @Override
            public void getRecentFinish() {
                startSocketIOService();
            }
        });
    }

}
