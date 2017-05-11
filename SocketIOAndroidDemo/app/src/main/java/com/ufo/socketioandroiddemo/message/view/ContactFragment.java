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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.ufo.socketioandroiddemo.R;
import com.ufo.socketioandroiddemo.message.contract.ContactContract;
import com.ufo.socketioandroiddemo.message.model.ChatModel;
import com.ufo.socketioandroiddemo.message.presenter.ContactPresenter;
import com.ufo.socketioandroiddemo.mvp.MVPBaseFragment;
import com.ufo.tools.NotificationAction;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by tjpld on 2017/5/9.
 */

public class ContactFragment extends MVPBaseFragment<ContactContract.View, ContactPresenter> implements ContactContract.View {

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
            if (intent.getAction().equals(NotificationAction.Update_Contact)) {
                mPresenter.loadData();
            }
        }

    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDataSource = new ArrayList<>();
        mHandler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        initControl(view);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(NotificationAction.Update_Contact);            //添加动态广播的Action
        getContext().registerReceiver(mReceiver, intentFilter);

        mPresenter.loadData();

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getContext().unregisterReceiver(mReceiver);
    }

    public static ContactFragment newInstance() {
        ContactFragment fragment = new ContactFragment();
        return fragment;
    }


    private void initControl(View view) {


        mRecyclerView = (RecyclerView) view.findViewById(R.id.contact_recycler_view);

        mAdapter = new RecyclerViewAdapter(mDataSource, getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        mAdapter.setRecyclerViewItemListener(new RecyclerViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });

        mRecyclerView.setAdapter(mAdapter);

    }


    static class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

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
            View view = LayoutInflater.from(mContext).inflate(R.layout.viewholder_contact, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            holder.text.setText(mData.get(position).getName());

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

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.contact_view_holder_imageView);
            text = (TextView) itemView.findViewById(R.id.contact_view_holder_textView);
        }

    }

    public interface RecyclerViewItemListener {
        void onItemClick(View view, int position);
    }


}
