package com.ufo.socketioandroiddemo.message.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.ufo.extend.LoadMoreForChatOnScrollListener;
import com.ufo.extend.MyKeyBoard;
import com.ufo.extend.MyOtherGridView;
import com.ufo.socketioandroiddemo.R;
import com.ufo.socketioandroiddemo.message.contract.ChatMessageContract;
import com.ufo.socketioandroiddemo.message.model.ChatMessageModel;
import com.ufo.socketioandroiddemo.message.presenter.ChatMessagePresenter;
import com.ufo.socketioandroiddemo.mvp.MVPBaseActivity;
import com.ufo.tools.NotificationAction;

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

    private Boolean shouldScrollBack = false;

    private int beforeHeight = 0;
    private int beforeCount = 0;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(NotificationAction.Send_Message)) {
                onNotifySend(intent);
            } else if (intent.getAction().equals(NotificationAction.Receive_Message)) {
                onNotifyReceive(intent);
            } else if (intent.getAction().equals(NotificationAction.Get_Recent_Finish)) {
                reloadData();
            }
        }

    };


    private ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {

            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();


            if (mRecyclerView.computeVerticalScrollRange() >= mRecyclerView.getHeight()) {
                linearLayoutManager.setStackFromEnd(true);
            } else {
                linearLayoutManager.setStackFromEnd(false);
            }

            if (shouldScrollBack) {

                int afterHeight = linearLayoutManager.getChildAt(0).getHeight();


                mRecyclerView.scrollBy(0, afterHeight - beforeHeight);

                shouldScrollBack = false;
            }

        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_message);

        mPresenter.initExecutorService();

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

        getContext().registerReceiver(mReceiver, new IntentFilter(NotificationAction.Send_Message));
        getContext().registerReceiver(mReceiver, new IntentFilter(NotificationAction.Receive_Message));
        getContext().registerReceiver(mReceiver, new IntentFilter(NotificationAction.Get_Recent_Finish));


        reloadData();

    }


    @Override
    protected void onDestroy() {
        mPresenter.shutDownExecutorService();
        getContext().unregisterReceiver(mReceiver);
        super.onDestroy();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);

    }


    @Override
    protected void onPause() {
        super.onPause();
        mEkBar.reset();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(mOnGlobalLayoutListener);
        } else {
            mRecyclerView.getViewTreeObserver().removeGlobalOnLayoutListener(mOnGlobalLayoutListener);
        }
    }


    private void initControl() {

        mRecyclerView = (RecyclerView) findViewById(R.id.chat_message_recycler_view);

        mAdapter = new ChatMessageRecyclerViewAdapter(mDataSource, this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

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


        LoadMoreForChatOnScrollListener scrollListener = new LoadMoreForChatOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore() {

                if (mPresenter.isHasMore() && !mPresenter.isLoading()) {

                    beforeCount = mDataSource.size();

                    mDataSource.add(0, null);
                    mAdapter.notifyItemInserted(0);

                    loadMoreData();
                }

            }
        };


        mRecyclerView.addOnScrollListener(scrollListener);


        mEkBar = (MyKeyBoard) findViewById(R.id.ek_bar);

        mEkBar.addOnFuncKeyBoardListener(this);
        mEkBar.addFuncView(new MyOtherGridView(this));


        mEkBar.getImageButtonSend().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String body = mEkBar.getEtChat().getText().toString().trim();
                if (!TextUtils.isEmpty(body)) {
                    mPresenter.sendText(body, mCurrentChatID);
                    mEkBar.getEtChat().getText().clear();
                }
            }
        });


        mEkBar.getEtChat().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {

                    String body = mEkBar.getEtChat().getText().toString().trim();
                    if (!TextUtils.isEmpty(body)) {
                        mPresenter.sendText(body, mCurrentChatID);
                        mEkBar.getEtChat().getText().clear();
                    }

                }
                return true;
            }
        });


    }


    private void scrollToPosition(int position) {
        mRecyclerView.smoothScrollToPosition(position);
    }

    private void scrollToBottom(boolean animated) {

        if (mDataSource.size() - 1 < 0)
            return;

        if (animated)
            mRecyclerView.smoothScrollToPosition(mDataSource.size() - 1);
        else
            mRecyclerView.scrollToPosition(mDataSource.size() - 1);

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
     * notification
     */

    private void onNotifySend(Intent intent) {
        ChatMessageModel model = (ChatMessageModel) intent.getExtras().get("model");
        if (model != null && model.getChatID().equals(mCurrentChatID)) {
            mPresenter.updateChatMessage(model);
        }
    }

    private void onNotifyReceive(Intent intent) {
        ChatMessageModel model = (ChatMessageModel) intent.getExtras().get("model");
        if (model != null && model.getChatID().equals(mCurrentChatID)) {
            mPresenter.insertChatMessage(model);
        }
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
        scrollToBottom(false);
    }

    @Override
    public void loadMoreDataComplete() {

        if (mRecyclerView.getLayoutManager().getChildCount() > 1) {

            beforeHeight = mRecyclerView.getLayoutManager().getChildAt(1).getHeight();
            int top = mRecyclerView.getLayoutManager().getChildAt(1).getTop();

            mDataSource.remove(0);
            mAdapter.notifyDataSetChanged();

            int afterCount = mDataSource.size();

            int pos = afterCount - beforeCount;


            ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(pos, top);

            shouldScrollBack = true;
        } else {
            mDataSource.remove(0);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void updateChatMessageCell() {
        mAdapter.notifyDataSetChanged();
        scrollToBottom(true);
    }


    @Override
    public void reloadData() {
        mPresenter.reloadDataWithChatID(mCurrentChatID);
    }


    private void loadMoreData() {
        mPresenter.loadMoreDataWithChatID(mCurrentChatID);
    }


}
