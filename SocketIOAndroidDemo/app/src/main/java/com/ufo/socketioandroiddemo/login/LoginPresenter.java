package com.ufo.socketioandroiddemo.login;


import com.ufo.model.ResultModel;
import com.ufo.retrofitextend.RetrofitExtendFactory;
import com.ufo.socketioandroiddemo.mvp.BasePresenterImpl;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class LoginPresenter extends BasePresenterImpl<LoginContract.View> implements LoginContract.Presenter {

    @Override
    public void login(String username, String password) {

        Retrofit retrofit = RetrofitExtendFactory.createNormalRetrofit(mView.getAppContext());
        LoginAPI loginAPI = retrofit.create(LoginAPI.class);

        Subscription subscription = loginAPI.doLogin(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultModel<UserInfoBean>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        String errorMessage;

                        if (e instanceof HttpException) {
                            HttpException httpException = (HttpException) e;
                            int code = httpException.code();
                            String msg = httpException.getMessage();

                            errorMessage = code + " " + msg;

                        } else {
                            errorMessage = e.getMessage();
                        }
                        mView.loginFail(errorMessage);
                    }

                    @Override
                    public void onNext(ResultModel<UserInfoBean> resultModel) {

                        if (resultModel.getSuccess()) {
                            UserInfoRepository.getInstance().login(mView.getAppContext(),resultModel.getData());
                            mView.loginSuccess(resultModel.getData());
                        }
                        else {
                            mView.loginFail(resultModel.getErrorMessage());
                        }

                    }
                });

        addSubscription(subscription);

    }

}
