package com.tanap.retrofit2rxandroid.network.generic;

import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by trusttanapruk on 7/28/2016.
 */
public abstract class GenericNetworkManager {

    protected <T> Single<T> setDefaultBehavior(Single<T> observable, final Class<T> tClass) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Single<? extends T>>() {
                    @Override
                    public Single<? extends T> call(Throwable throwable) {
                        return checkException(throwable, tClass);
                    }
                });
    }

    private <T> Single<T> checkException(Throwable throwable, Class<T> tClass) {
        if (throwable instanceof HttpException) {
            //error 500
            HttpException httpException = (HttpException) throwable;
            convertHttpResponse(httpException, tClass);
        } else {
            throwable.printStackTrace();
        }

        // TODO: 7/30/2016 handle json, empty string
        return null;
    }

    private <T> Single<T> convertHttpResponse(HttpException httpException, Class<T> tClass) {
        try {
            String json = httpException.response().errorBody().string();
            return Single.just(new Gson().fromJson(json, tClass));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

