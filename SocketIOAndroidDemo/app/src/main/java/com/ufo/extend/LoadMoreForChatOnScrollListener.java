package com.ufo.extend;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


/**
 * Created by tjpld on 2017/5/23.
 */

public abstract class LoadMoreForChatOnScrollListener extends RecyclerView.OnScrollListener {


    private LinearLayoutManager mLinearLayoutManager;

    public LoadMoreForChatOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if (firstVisibleItem == 0) {
            onLoadMore();
        }

    }


    public abstract void onLoadMore();
}
