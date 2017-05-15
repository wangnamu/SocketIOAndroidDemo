package com.ufo.extend;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;


import com.ufo.socketioandroiddemo.R;

import java.util.List;

/**
 * Created by tjpld on 2016/10/13.
 */

public class Tooltips extends PopupWindow {

    private static final int MARGIN = 20;

    private View mRoot;
    private Context mContext;
    private LayoutInflater mInflater;

    private TooltipsItemClickListener mTooltipsItemClickListener;


    private Object mObj;

    public Tooltips(Context context) {

        mContext = context;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRoot = mInflater.inflate(R.layout.popup_tooltips, null);

        this.setContentView(mRoot);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();

        ColorDrawable dw = new ColorDrawable(Color.TRANSPARENT);

        this.setBackgroundDrawable(dw);

    }

    public void setDataSource(List<String> list) {
        LinearLayout content = (LinearLayout) mRoot.findViewById(R.id.tooltips_linear);
        content.removeAllViews();
        for (final String str : list) {
            int margin = mContext.getResources().getDimensionPixelOffset(R.dimen.button_border_less_margin);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(margin, margin, margin, margin);
            Button button = (Button) mInflater.inflate(R.layout.button_tooltips, null);
            button.setText(str);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTooltipsItemClickListener != null) {
                        mTooltipsItemClickListener.onTooltipsItemClickListener(str, mObj);
                        dismiss();
                    }
                }
            });
            content.addView(button, lp);
        }
        this.update();
    }


    public void show(View parent) {
        if (!this.isShowing()) {
//            int[] location = new int[2];
//            parent.getLocationInWindow(location);

            getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

//            int yInWindow = location[1];
            int yInShow = -getContentView().getMeasuredHeight() - parent.getHeight() + MARGIN;
            int y = yInShow;

//            if (yInWindow + yInShow <= Utils.getActionBarHeight(mContext) + Utils.getStatusBarHeight(mContext)) {
//                y = -MARGIN;
//            }
            this.showAsDropDown(parent, parent.getLayoutParams().width / 2, y);
        } else {
            this.dismiss();
        }
    }

    public void setTooltipsItemClickListener(TooltipsItemClickListener listener) {
        this.mTooltipsItemClickListener = listener;
    }

    public void setObj(Object obj) {
        this.mObj = obj;
    }

    public interface TooltipsItemClickListener {
        void onTooltipsItemClickListener(String name, Object object);
    }

}
