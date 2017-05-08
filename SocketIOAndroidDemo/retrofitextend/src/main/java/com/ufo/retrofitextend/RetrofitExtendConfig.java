package com.ufo.retrofitextend;

/**
 * Created by tjpld on 2017/5/8.
 */

public class RetrofitExtendConfig {

    private boolean isDebugMode = true;
    private String baseUrl = "";
    private long timeOut = 60;


    public boolean isDebugMode() {
        return isDebugMode;
    }

    public void setDebugMode(boolean debugMode) {
        isDebugMode = debugMode;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }


}
