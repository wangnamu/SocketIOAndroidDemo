package com.ufo.socketioandroiddemo.message.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.ufo.socketioandroiddemo.R;

/**
 * Created by tjpld on 2017/5/23.
 */


public class LoadingViewHolder extends RecyclerView.ViewHolder {
    public ProgressBar mProgressBar;

    public LoadingViewHolder(View itemView) {
        super(itemView);
        mProgressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
    }
}