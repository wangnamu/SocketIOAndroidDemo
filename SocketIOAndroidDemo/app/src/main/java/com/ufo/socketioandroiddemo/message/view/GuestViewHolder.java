package com.ufo.socketioandroiddemo.message.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.ufo.socketioandroiddemo.R;
import com.ufo.socketioandroiddemo.message.model.ChatMessageModel;
import com.ufo.socketioandroiddemo.message.model.MessageTypeEnum;
import com.ufo.utils.DateUtil;
import com.ufo.utils.ScreenUtil;

import java.text.ParseException;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by tjpld on 2017/5/15.
 */

public class GuestViewHolder extends RecyclerView.ViewHolder {

    public View mFieldText;
    public View mFieldImage;
    public ImageView mHeadPortrait;
    public ImageView mImage;
    public TextView mContent;
    public TextView mName;
    public TextView mTopTime;
    public TextView mItemMsg;
    public ProgressBar mContentProgressBar;
    public ProgressBar mImageProgressBar;

    public GuestViewHolder(View itemView) {
        super(itemView);

        mFieldText = itemView.findViewById(R.id.recycler_view_chat_guest_item_field_text);
        mFieldImage = itemView.findViewById(R.id.recycler_view_chat_guest_item_field_image);

        mHeadPortrait = (ImageView) itemView.findViewById(R.id.recycler_view_chat_guest_item_headPortrait);
        mImage = (ImageView) itemView.findViewById(R.id.recycler_view_chat_guest_item_image);

        mName = (TextView) itemView.findViewById(R.id.recycler_view_chat_guest_item_name);
        mContent = (TextView) itemView.findViewById(R.id.recycler_view_chat_guest_item_text);
        mTopTime = (TextView) itemView.findViewById(R.id.recycler_view_chat_guest_item_top_time);
        mItemMsg = (TextView) itemView.findViewById(R.id.recycler_view_chat_guest_item_msg);


        mContentProgressBar = (ProgressBar) itemView.findViewById(R.id.recycler_view_chat_guest_item_content_progressbar);
        mImageProgressBar = (ProgressBar) itemView.findViewById(R.id.recycler_view_chat_guest_item_image_progressbar);

    }

    public void setTopTime(int position, long current, long last, int elapsed) {

        if (position == 0) {
            mTopTime.setText(DateUtil.dateToShort(current));
            mTopTime.setVisibility(View.VISIBLE);
        } else if (DateUtil.inTimeCurrent(current, last, elapsed)) {
            mTopTime.setText(DateUtil.dateToShort(current));
            mTopTime.setVisibility(View.VISIBLE);
        } else {
            mTopTime.setVisibility(View.GONE);
        }

    }

    public void setHolder(Context context, ChatMessageModel chatMessageModel) {

        mName.setText(chatMessageModel.getNickName());

        if (chatMessageModel.getMessageType().equals(MessageTypeEnum.Text)) {

            showFieldText(context);

            mContent.setText(chatMessageModel.getBody());

            Glide.with(context)
                    .load(chatMessageModel.getHeadPortrait())
                    .placeholder(R.drawable.ic_placeholder_round)
                    .bitmapTransform(new CenterCrop(context), new CropCircleTransformation(context))
                    .dontAnimate()
                    .into(mHeadPortrait);

//            int sendStatus = chatModel.getSendStatusType();
//
//            if (sendStatus == SendStatusType.Sending) {
//                showProgress();
//            } else if (sendStatus == SendStatusType.Sended) {
//                hideProgress();
//            }


        } else {

            showFieldImage(context);

            Glide.with(context)
                    .load(chatMessageModel.getHeadPortrait())
                    .placeholder(R.drawable.ic_placeholder_round)
                    .bitmapTransform(new CenterCrop(context), new CropCircleTransformation(context))
                    .dontAnimate()
                    .into(mHeadPortrait);

            Glide.with(context)
                    .load(chatMessageModel.getThumbnail())
                    .thumbnail(0.1f)
                    .placeholder(R.drawable.ic_placeholder)
                    .override(500, 500)
                    .bitmapTransform(new FitCenter(context),
                            new RoundedCornersTransformation(context, context.getResources().getDimensionPixelOffset(R.dimen.chat_item_radius), 0))
                    .dontAnimate()
                    .into(mImage);

//            int sendStatus = chatModel.getSendStatusType();
//
//            if (sendStatus == SendStatusType.Sending) {
//                showProgress();
//            } else if (sendStatus == SendStatusType.Sended) {
//                hideProgress();
//            }

        }
    }


    private void setContentWith(Context context) {

        int content_margin = context.getResources().getDimensionPixelOffset(R.dimen.chat_content_margin);
        int headPortrait_width = context.getResources().getDimensionPixelOffset(R.dimen.chat_headPortrait_width);
        int progress_width = context.getResources().getDimensionPixelOffset(R.dimen.chat_progress_width);
        int parent_padding = context.getResources().getDimensionPixelOffset(R.dimen.activity_horizontal_margin) * 2;

        mContent.setMaxWidth(ScreenUtil.getScreenWidth(context) - content_margin - headPortrait_width - progress_width - parent_padding);
    }


    private void showFieldText(Context context) {
        mFieldText.setVisibility(View.VISIBLE);
        mFieldImage.setVisibility(View.GONE);

        setContentWith(context);
    }

    private void showFieldImage(Context context) {
        mFieldText.setVisibility(View.GONE);
        mFieldImage.setVisibility(View.VISIBLE);
    }

//    public void showProgress() {
//        if (mFieldText.getVisibility() == View.VISIBLE) {
//            mContentProgressBar.setVisibility(View.VISIBLE);
//        } else {
//            mImageProgressBar.setVisibility(View.VISIBLE);
//        }
//    }
//
//    public void hideProgress() {
//        if (mFieldText.getVisibility() == View.VISIBLE) {
//            mContentProgressBar.setVisibility(View.GONE);
//        } else {
//            mImageProgressBar.setVisibility(View.GONE);
//        }
//    }


}
