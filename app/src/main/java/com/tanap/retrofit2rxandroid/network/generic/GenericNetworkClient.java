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

    protected abstract Request.Builder getRequestInterceptor(Request.Builder requestBuilder);

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

    private Interceptor getOnTopInterceptor() {
        //per client interceptor
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder newRequestBuilder = getRequestInterceptor(chain.request().newBuilder());
                if (newRequestBuilder == null) {
                    return chain.proceed(chain.request());
                }
                return chain.proceed(newRequestBuilder.build());
            }
        };
    }


    private OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new GenericInterceptor())
                .addInterceptor(getOnTopInterceptor())
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(isShowLog() ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE)).build();
    }

    protected Retrofit.Builder getBaseRetrofitBuilder() {
        return new Retrofit.Builder().baseUrl(getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(getClient());
    }

    protected T getGenericJsonConnection() {
        return getBaseRetrofitBuilder()
                .build()
                .create(getApiClassType());
    }

    protected T getGenericRxConnection() {
        return getBaseRetrofitBuilder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(getApiClassType());
    }

}
