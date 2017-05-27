package com.ufo.socketioandroiddemo.message.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ufo.extend.Tooltips;
import com.ufo.socketioandroiddemo.R;
import com.ufo.socketioandroiddemo.message.model.ChatMessageModel;

import java.util.List;

/**
 * Created by tjpld on 2017/5/15.
 */

public class ChatMessageRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int elapsedTime = 15;

    private List<ChatMessageModel> mData;
    private Context mContext;
    private Tooltips mTooltips;

    private ChatMessageTooltipsItemClickListener mChatMessageTooltipsItemClickListener;

//    private Tooltips.TooltipsItemClickListener mTooltipsItemClickListener = new Tooltips.TooltipsItemClickListener() {
//        @Override
//        public void onTooltipsItemClickListener(String name, Object object) {
//            if (mChatTooltipsItemClickListener != null) {
//                switch (name) {
//                    case "复制":
//                        mChatTooltipsItemClickListener.copyItem((ChatMessageModel) object);
//                        break;
//                    case "转发":
//                        mChatTooltipsItemClickListener.forwardItem((ChatMessageModel) object);
//                        break;
//                    case "删除":
//                        mChatTooltipsItemClickListener.delItem((ChatMessageModel) object);
//                        break;
//                    default:
//                        break;
//                }
//            }
//        }
//    };

    public ChatMessageRecyclerViewAdapter(List<ChatMessageModel> data, Context context) {
        mData = data;
        mContext = context;
        mTooltips = new Tooltips(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ItemType.Host) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.viewholder_chat_host_item, parent, false);
            HostViewHolder viewHolder = new HostViewHolder(view);
            return viewHolder;
        } else if (viewType == ItemType.Guest) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.viewholder_chat_guest_item, parent, false);
            GuestViewHolder viewHolder = new GuestViewHolder(view);
            return viewHolder;
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.viewholder_loading_item, parent, false);
            return new LoadingViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof HostViewHolder) {

            HostViewHolder viewHolder = (HostViewHolder) holder;

            viewHolder.setTopTime(position, mData.get(position).getTime(), (position - 1 >= 0 && mData.get(position - 1) != null) ? mData.get(position - 1).getTime() : 0, elapsedTime);
            viewHolder.setHolder(mContext, mData.get(position));

//            viewHolder.mContent.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    Utils.vibrator(mContext);
//
//                    List<String> list = new ArrayList<>();
//                    list.add("复制");
//                    list.add("转发");
//                    list.add("删除");
//
//                    mTooltips.setDataSource(list);
//                    mTooltips.setObj(mData.get(position));
//
//                    mTooltips.setTooltipsItemClickListener(mTooltipsItemClickListener);
//                    mTooltips.show(v);
//
//                    return false;
//                }
//            });
//
//            viewHolder.mImage.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    Utils.vibrator(mContext);
//
//                    List<String> list = new ArrayList<>();
//                    list.add("转发");
//                    list.add("删除");
//
//                    mTooltips.setDataSource(list);
//                    mTooltips.setObj(mData.get(position));
//
//                    mTooltips.setTooltipsItemClickListener(mTooltipsItemClickListener);
//                    mTooltips.show(v);
//
//                    return false;
//                }
//            });


        } else if (holder instanceof GuestViewHolder) {
            GuestViewHolder viewHolder = (GuestViewHolder) holder;

            viewHolder.setTopTime(position, mData.get(position).getTime(), (position - 1 >= 0 && mData.get(position - 1) != null) ? mData.get(position - 1).getTime() : 0, elapsedTime);
            viewHolder.setHolder(mContext, mData.get(position));

//
//            viewHolder.mContent.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    Utils.vibrator(mContext);
//
//                    List<String> list = new ArrayList<>();
//                    list.add("复制");
//                    list.add("转发");
//                    list.add("删除");
//
//                    mTooltips.setDataSource(list);
//                    mTooltips.setObj(mData.get(position));
//
//                    mTooltips.setTooltipsItemClickListener(mTooltipsItemClickListener);
//                    mTooltips.show(v);
//
//                    return false;
//                }
//            });
//
//            viewHolder.mImage.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    Utils.vibrator(mContext);
//
//                    List<String> list = new ArrayList<>();
//                    list.add("转发");
//                    list.add("删除");
//
//                    mTooltips.setDataSource(list);
//                    mTooltips.setObj(mData.get(position));
//
//                    mTooltips.setTooltipsItemClickListener(mTooltipsItemClickListener);
//                    mTooltips.show(v);
//
//                    return false;
//                }
//            });

        } else {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.mProgressBar.setIndeterminate(true);
        }

    }

//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
//
//        if (payloads.isEmpty()) {
//            onBindViewHolder(holder, position);
//        } else {
//            if (holder instanceof HostViewHolder) {
//                HostViewHolder viewHolder = (HostViewHolder) holder;
//                viewHolder.refresh((ChatModel) payloads.get(0));
//            }
//        }
//
//    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (mData.get(position) == null) {
            return ItemType.Loading;
        }
        return mData.get(position).isHost(mContext) ? ItemType.Host : ItemType.Guest;
    }


    public void setChatTooltipsItemClickListener(ChatMessageTooltipsItemClickListener chatMessageTooltipsItemClickListener) {
        this.mChatMessageTooltipsItemClickListener = chatMessageTooltipsItemClickListener;
    }


    public interface ChatMessageTooltipsItemClickListener {
        void copyItem(ChatMessageModel chatMessageModel);

        void forwardItem(ChatMessageModel chatMessageModel);

        void delItem(ChatMessageModel chatMessageModel);
    }

    private class ItemType {
        public static final int Host = 0;
        public static final int Guest = 1;
        public static final int Loading = 2;
    }
}
