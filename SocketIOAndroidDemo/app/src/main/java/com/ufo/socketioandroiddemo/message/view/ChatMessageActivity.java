package com.ufo.socketioandroiddemo.message.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.ufo.extend.MyKeyBoard;
import com.ufo.extend.MyOtherGridView;
import com.ufo.socketioandroiddemo.R;
import com.ufo.socketioandroiddemo.message.contract.ChatMessageContract;
import com.ufo.socketioandroiddemo.message.model.ChatMessageModel;
import com.ufo.socketioandroiddemo.message.presenter.ChatMessagePresenter;
import com.ufo.socketioandroiddemo.mvp.MVPBaseActivity;

import java.util.ArrayList;
import java.util.List;

import sj.keyboard.widget.FuncLayout;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;

/**
 * Created by tjpld on 2017/5/9.
 */

public class ChatMessageActivity extends MVPBaseActivity<ChatMessageContract.View, ChatMessagePresenter> implements ChatMessageContract.View, FuncLayout.OnFuncKeyBoardListener {

    private String mCurrentChatID;
    private String mCurrentName;

    private RecyclerView mRecyclerView;
    private ChatMessageRecyclerViewAdapter mAdapter;
    private List<ChatMessageModel> mDataSource;

    private MyKeyBoard mEkBar;

    private Handler mHandler;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_message);

        mCurrentChatID = getIntent().getStringExtra("ChatID");
        mCurrentName = getIntent().getStringExtra("Name");

        mDataSource = new ArrayList<>();
        mHandler = new Handler();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(mCurrentName);
        }


        initControl();

        reloadData();

    }

    private void initControl() {

        mRecyclerView = (RecyclerView) findViewById(R.id.chat_message_recycler_view);

        mAdapter = new ChatMessageRecyclerViewAdapter(mDataSource, this);
        //mAdapter.setChatTooltipsItemClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //layoutManager.setStackFromEnd(true);


        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == SCROLL_STATE_DRAGGING) {
                    mEkBar.reset();
                }
            }
        });


        mEkBar = (MyKeyBoard) findViewById(R.id.ek_bar);

        mEkBar.addOnFuncKeyBoardListener(this);
        mEkBar.addFuncView(new MyOtherGridView(this));


        mEkBar.getImageButtonSend().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendText();
            }
        });


        mEkBar.getEtChat().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    //sendText();
                }
                return true;
            }
        });


    }


    private void scrollToPosition(final int position) {
        mRecyclerView.requestLayout();
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.smoothScrollToPosition(position);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * keyboard
     */

    @Override
    public void OnFuncPop(int height) {
        if (mDataSource.size() > 0) {
            scrollToPosition(mDataSource.size() - 1);
        }
    }

    @Override
    public void OnFuncClose() {
    }




    /**
     * present
     */

    @Override
    public Handler getHandler() {
        return mHandler;
    }

    @Override
    public List<ChatMessageModel> getDataSource() {
        return mDataSource;
    }


    @Override
    public void reloadDataComplete() {
        mAdapter.notifyDataSetChanged();
        //scrollToPosition();
    }

    @Override
    public void loadMoreDataComplete() {
    }

    @Override
    public void updateChatMessageCell() {

        mAdapter.notifyDataSetChanged();
        //scrollToPosition();

    }


    @Override
    public void reloadData() {
        mPresenter.reloadDataWithChatID(mCurrentChatID);
    }



    private void loadMoreData() {
        mPresenter.loadMoreDataWithChatID(mCurrentChatID);
    }
}
