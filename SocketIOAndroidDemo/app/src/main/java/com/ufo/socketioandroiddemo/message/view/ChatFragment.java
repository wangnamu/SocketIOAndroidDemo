package com.ufo.socketioandroiddemo.message.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.ufo.socketioandroiddemo.R;
import com.ufo.socketioandroiddemo.message.contract.ChatContract;
import com.ufo.socketioandroiddemo.message.model.ChatModel;
import com.ufo.socketioandroiddemo.message.presenter.ChatPresenter;
import com.ufo.socketioandroiddemo.mvp.MVPBaseFragment;
import com.ufo.tools.NotificationAction;
import com.ufo.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by tjpld on 2017/5/9.
 */

public class ChatFragment extends MVPBaseFragment<ChatContract.View, ChatPresenter> implements ChatContract.View {

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private List<ChatModel> mDataSource;
    private Handler mHandler;

    @Override
    public Handler getHandler() {
        return mHandler;
    }

    @Override
    public List<ChatModel> getDataSource() {
        return mDataSource;
    }

    @Override
    public void refreshData() {
        mAdapter.notifyDataSetChanged();
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(NotificationAction.Update_Chat)) {
                updateChat();
            } else if (intent.getAction().equals(NotificationAction.Get_Recent_Begin)) {
                getRecentBegin();
            } else if (intent.getAction().equals(NotificationAction.Get_Recent_Finish)) {
                getRecentFinish();
            }
        }

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataSource = new ArrayList<>();
        mHandler = new Handler();
        mPresenter.initExecutorService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        initControl(view);

        getContext().registerReceiver(mReceiver, new IntentFilter(NotificationAction.Update_Chat));
        getContext().registerReceiver(mReceiver, new IntentFilter(NotificationAction.Get_Recent_Begin));
        getContext().registerReceiver(mReceiver, new IntentFilter(NotificationAction.Get_Recent_Finish));


        mPresenter.updateChat();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.shutDownExecutorService();
        getContext().unregisterReceiver(mReceiver);
    }

    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        return fragment;
    }


    private void initControl(View view) {


        mRecyclerView = (RecyclerView) view.findViewById(R.id.chat_recycler_view);

        mAdapter = new RecyclerViewAdapter(mDataSource, getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        mAdapter.setRecyclerViewItemListener(new RecyclerViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(),ChatMessageActivity.class);
                intent.putExtra("ChatID",mDataSource.get(position).getSID());
                intent.putExtra("Name",mDataSource.get(position).getName());
                startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(mAdapter);

    }


    /**
     * notification
     */

    private void updateChat() {
        mPresenter.updateChat();
    }

    private void getRecentBegin() {
        Log.e("getRecentBegin","getRecentBegin");
    }

    private void getRecentFinish() {
//        [self hideNavigationLoading];
        Log.e("getRecentFinish","getRecentFinish");
        mPresenter.updateChat();
    }


    /**
     * Adapter
     */

    private static class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

        private List<ChatModel> mData;
        private Context mContext;
        private RecyclerViewItemListener mRecyclerViewItemListener;

        public void setRecyclerViewItemListener(RecyclerViewItemListener recyclerViewItemListener) {
            this.mRecyclerViewItemListener = recyclerViewItemListener;
        }


        public RecyclerViewAdapter(List<ChatModel> data, Context context) {
            this.mContext = context;
            this.mData = data;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.viewholder_chat, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            holder.name.setText(mData.get(position).getName());
            holder.body.setText(mData.get(position).getBody());
            holder.time.setText(DateUtil.dateToShort(mData.get(position).getTime()));

            Glide.with(mContext)
                    .load(mData.get(position).getImg())
                    .placeholder(R.drawable.ic_placeholder_round)
                    .bitmapTransform(new CenterCrop(mContext), new CropCircleTransformation(mContext))
                    .dontAnimate()
                    .into(holder.img);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRecyclerViewItemListener != null) {
                        mRecyclerViewItemListener.onItemClick(v, position);
                    }
                }
            });
        }


        @Override
        public int getItemCount() {
            return this.mData.size();
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView name;
        TextView body;
        TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.chat_view_holder_img);
            name = (TextView) itemView.findViewById(R.id.chat_view_holder_name);
            body = (TextView) itemView.findViewById(R.id.chat_view_holder_body);
            time = (TextView) itemView.findViewById(R.id.chat_view_holder_time);
        }

    }

    private interface RecyclerViewItemListener {
        void onItemClick(View view, int position);
    }


}
