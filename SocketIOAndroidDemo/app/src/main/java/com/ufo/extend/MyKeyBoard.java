package com.ufo.extend;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;


import com.ufo.socketioandroiddemo.R;

import sj.keyboard.XhsEmoticonsKeyBoard;

/**
 * Created by tjpld on 2016/10/11.
 */

public class MyKeyBoard extends XhsEmoticonsKeyBoard {

    //public final int APPS_HEIGHT = 120;

    public MyKeyBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void inflateKeyboardBar(){
        mInflater.inflate(R.layout.view_my_keyboard, this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == com.keyboard.view.R.id.btn_other) {
            toggleFuncView(FUNC_TYPE_APPPS);
            //setFuncViewHeight(EmoticonsKeyboardUtils.dip2px(getContext(), APPS_HEIGHT));
        }
    }

    @Override
    public void OnSoftClose() {
        super.OnSoftClose();
//        if (mLyKvml.getCurrentFuncKey() == FUNC_TYPE_APPPS) {
//            setFuncViewHeight(EmoticonsKeyboardUtils.dip2px(getContext(), APPS_HEIGHT));
//        }
    }

    @Override
    public void onFuncChange(int key) {
        if (FUNC_TYPE_APPPS == key) {
            mImageButtonOther.setImageResource(R.drawable.ic_keyboard_24dp);
        } else {
            mImageButtonOther.setImageResource(R.drawable.ic_add_24dp);
        }
    }

    @Override
    public void reset() {
        super.reset();
        mImageButtonOther.setImageResource(R.drawable.ic_add_24dp);
    }

}
