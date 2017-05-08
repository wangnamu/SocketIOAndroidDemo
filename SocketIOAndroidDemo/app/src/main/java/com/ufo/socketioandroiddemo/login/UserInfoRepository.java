package com.ufo.socketioandroiddemo.login;


import android.content.Context;
import android.content.SharedPreferences;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by tjpld on 2017/5/8.
 */

public class UserInfoRepository {

    private static final UserInfoRepository shareInstance = new UserInfoRepository();

    public static UserInfoRepository getInstance() {
        return shareInstance;
    }

    private UserInfoRepository() {

    }

    public UserInfoBean currentUser(Context context) {

        SharedPreferences preferences = context.getSharedPreferences("currentUser",MODE_PRIVATE);

        if(!preferences.getAll().isEmpty()){

            String sID = preferences.getString("sID", null);
            String userName = preferences.getString("userName", null);
            String passWord = preferences.getString("passWord", null);
            String nickName = preferences.getString("nickName", null);
            String headPortrait = preferences.getString("headPortrait", null);
            long loginTime = preferences.getLong("loginTime", 0);
            boolean inUse = preferences.getBoolean("inUse", false);

            UserInfoBean userInfoBean = new UserInfoBean();
            userInfoBean.setSID(sID);
            userInfoBean.setUserName(userName);
            userInfoBean.setPassWord(passWord);
            userInfoBean.setNickName(nickName);
            userInfoBean.setHeadPortrait(headPortrait);
            userInfoBean.setLoginTime(loginTime);
            userInfoBean.setInUse(inUse);

            return userInfoBean;
        }

        return null;
    }

    public void login(Context context,UserInfoBean bean) {

        bean.setInUse(true);
        bean.setLoginTime(System.currentTimeMillis());

        SharedPreferences preferences = context.getSharedPreferences("currentUser",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("sID",bean.getSID());
        editor.putString("userName",bean.getUserName());
        editor.putString("passWord",bean.getPassWord());
        editor.putString("nickName",bean.getNickName());
        editor.putString("headPortrait",bean.getHeadPortrait());
        editor.putLong("loginTime",bean.getLoginTime());
        editor.putBoolean("inUse",bean.getInUse());

        editor.apply();
    }

    public void logoff(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("currentUser",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }


}
