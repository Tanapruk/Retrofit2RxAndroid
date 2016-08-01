package com.tanap.retrofit2rxandroid.network.generic;

import android.util.Log;

import com.tanap.retrofit2rxandroid.BuildConfig;
import com.tanap.retrofit2rxandroid.URL;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by trusttanapruk on 7/27/2016.
 */
public abstract class GenericNetworkClient<T> {
    private String baseUrl;
    private boolean showLog = false;

    //every network service class must inherit this class and set the class type, too
    protected abstract Class<T> getApiClassType();

    protected abstract Request getRequestInterceptor(Interceptor.Chain chain);

    protected String getBaseUrl() {
        if (baseUrl == null) {
            baseUrl = URL.URL_LOCAL_TRUST;
        }
        return baseUrl;
    }

    protected GenericNetworkClient setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    private boolean isShowLog() {
        showLog = BuildConfig.DEBUG;
        Log.d("TRUST", "isShowLog: " + showLog);
        return showLog;
    }

    private Interceptor getInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                if (getRequestInterceptor(chain) == null) {
                    return chain.proceed(chain.request());
                }
                return chain.proceed(getRequestInterceptor(chain));
            }
        };
    }


    private OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(getInterceptor())
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(isShowLog() ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE)).build();
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
                .create(getApiClassType());
    }

    protected T getGenericRxConnection() {
        return getBaseBuilder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(getApiClassType());
    }

}
