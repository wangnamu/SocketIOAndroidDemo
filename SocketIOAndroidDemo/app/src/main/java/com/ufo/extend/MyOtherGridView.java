package com.ufo.extend;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.ufo.socketioandroiddemo.R;

import java.util.ArrayList;


/**
 * Created by tjpld on 2016/10/12.
 */

public class MyOtherGridView extends RelativeLayout {

    protected View view;

    public MyOtherGridView(Context context) {
        this(context, null);
    }

    public MyOtherGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.view_other, this);
        init();
    }

    protected void init() {
        GridView gv_apps = (GridView) view.findViewById(R.id.gv_other);
        ArrayList<OtherModel> mAppBeanList = new ArrayList<>();
        mAppBeanList.add(new OtherModel(R.drawable.icon_camera, "拍照"));
        mAppBeanList.add(new OtherModel(R.drawable.icon_photo, "图片"));
        mAppBeanList.add(new OtherModel(R.drawable.icon_audio, "视频"));
        mAppBeanList.add(new OtherModel(R.drawable.icon_qzone, "空间"));
        mAppBeanList.add(new OtherModel(R.drawable.icon_contact, "联系人"));
        mAppBeanList.add(new OtherModel(R.drawable.icon_file, "文件"));
        mAppBeanList.add(new OtherModel(R.drawable.icon_loaction, "位置"));
        AppsAdapter adapter = new AppsAdapter(getContext(), mAppBeanList);
        gv_apps.setAdapter(adapter);
    }


    class AppsAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private Context mContext;
        private ArrayList<OtherModel> mData = new ArrayList<>();

        public AppsAdapter(Context context, ArrayList<OtherModel> data) {
            this.mContext = context;
            this.inflater = LayoutInflater.from(context);
            if (data != null) {
                this.mData = data;
            }
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.view_other_item, null);
                viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
                viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final OtherModel appBean = mData.get(position);
            if (appBean != null) {
                viewHolder.iv_icon.setBackgroundResource(appBean.getIcon());
                viewHolder.tv_name.setText(appBean.getFuncName());
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, appBean.getFuncName(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return convertView;
        }

        class ViewHolder {
            public ImageView iv_icon;
            public TextView tv_name;
        }
    }


    class OtherModel {

        private int icon;
        private String funcName;

        public int getIcon() {
            return icon;
        }

        public String getFuncName() {
            return funcName;
        }

        public OtherModel(int icon, String funcName) {
            this.icon = icon;
            this.funcName = funcName;
        }
    }


}