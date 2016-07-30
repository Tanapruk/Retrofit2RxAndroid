package com.tanap.retrofit2rxandroid.network.generic;

import android.util.Log;

import com.tanap.retrofit2rxandroid.BuildConfig;
import com.tanap.retrofit2rxandroid.URL;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by trusttanapruk on 7/27/2016.
 */
public abstract class GenericNetworkService<T> {
    private String baseUrl;
    private boolean showLog = false;

    protected abstract Class<T> getTClass();

    protected String getBaseUrl() {
        if (baseUrl == null) {
            baseUrl = URL.URL_LOCAL_TRUST;
        }
        return baseUrl;
    }

    protected GenericNetworkService setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    private boolean isShowLog() {
        showLog = BuildConfig.DEBUG;
        Log.d("TRUST", "isShowLog: " + showLog);
        return showLog;
    }


    private OkHttpClient getClient() {
        return new OkHttpClient.Builder().addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(isShowLog() ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE)).build();
    }

    protected Retrofit.Builder getBaseBuilder() {
        return new Retrofit.Builder().baseUrl(getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(getClient());
    }

    protected T getGenericJsonConnection() {
        return getBaseBuilder()
                .build()
                .create(getTClass());
    }

    protected T getGenericRxConnection() {
        return getBaseBuilder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(getTClass());
    }

}
