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

            String sID = preferences.getString("SID", null);
            String userName = preferences.getString("UserName", null);
            String passWord = preferences.getString("PassWord", null);
            String nickName = preferences.getString("NickName", null);
            String headPortrait = preferences.getString("HeadPortrait", null);
            long loginTime = preferences.getLong("LoginTime", 0);
            boolean inUse = preferences.getBoolean("InUse", false);

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
        editor.putString("SID",bean.getSID());
        editor.putString("UserName",bean.getUserName());
        editor.putString("PassWord",bean.getPassWord());
        editor.putString("NickName",bean.getNickName());
        editor.putString("HeadPortrait",bean.getHeadPortrait());
        editor.putLong("LoginTime",bean.getLoginTime());
        editor.putBoolean("InUse",bean.getInUse());

        editor.apply();
    }

    public void logoff(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("currentUser",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }


}
