package com.tanap.retrofit2rxandroid.network.generic;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by trusttanapruk on 7/28/2016.
 */
public abstract class GenericNetworkController {


    protected <T> Single.Transformer<T, T> applySchedulerAndErrorHandling(final Class<T> typeOfClass) {
        //this append change command after observable methods
        //this makes sure that the network service doesn't interrupt with the mainthread
        //also each request will get a error handling
        //http://blog.danlew.net/2015/03/02/dont-break-the-chain/
        SubscribingState.getInstance().start(typeOfClass);
        return new Single.Transformer<T, T>() {
            @Override
            public Single<T> call(final Single<T> single) {
                return single
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorResumeNext(new Func1<Throwable, Single<? extends T>>() {
                            @Override
                            public Single<? extends T> call(Throwable throwable) {
                                return handleException(throwable, typeOfClass);
                            }
                        });
            }
        };
    }

    private <T> Single<T> handleException(Throwable throwable, Class<T> type) {
        if (!(throwable instanceof HttpException)) {
            return Single.error(throwable);
        }
        return convertResponseBody((HttpException) throwable, type);
    }

    private <T> Single<T> convertResponseBody(HttpException httpException, Class<T> type) {

        if (httpException.response() == null || httpException.response().errorBody() == null) {
            return Single.error(new NullPointerException("Null response() or response().errorBody()"));
        }

        if (httpException.response().code() == 404) {
            // TODO: 8/2/2016 response().errorBody() cached the previous request
            //if we don't do like this then httpException
            // may get response from its cache and successfully convert to Json
            // cannot send out other object, too.
            return Single.just(null);
        }

        String jsonBody;
        try {
            jsonBody = httpException.response().errorBody().string();
        } catch (IOException e) {
            return Single.error(e);
        }

        if (jsonBody.isEmpty()) {
            return Single.error(new NullPointerException("Empty JSON Body"));
        }

        if (!isJSONValid(jsonBody)) {
            return Single.error(new JSONException("Body is not a JSON type " + jsonBody));
        }

        return Single.just(new Gson().fromJson(jsonBody, type));
    }


    private boolean isJSONValid(String string) {
        //parsing JSON and parsing into array
        try {
            new JSONObject(string);
        } catch (JSONException ex) {
            try {
                new JSONArray(string);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }
}

