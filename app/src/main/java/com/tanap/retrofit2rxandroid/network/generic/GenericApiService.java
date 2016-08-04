package com.tanap.retrofit2rxandroid.network.generic;

import android.util.Log;

import com.tanap.retrofit2rxandroid.BuildConfig;
import com.tanap.retrofit2rxandroid.URL;

import java.io.IOException;

import okhttp3.CertificatePinner;
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
public abstract class GenericApiService<T> {
    private String baseUrl;
    private boolean showLog = false;
    private T api;

    //every network service class must inherit this class and set the class type, too
    protected abstract Class<T> getApiClassType();

    protected abstract Request.Builder getRequestInterceptor(Request.Builder requestBuilder);

    protected String getBaseUrl() {
        if (baseUrl == null) {
            baseUrl = URL.URL_LOCAL_TRUST;
        }
        return baseUrl;
    }



    public GenericApiService setBaseUrl(String baseUrl) {
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

    // TODO: 8/4/2016 session timeout, session expire

    private CertificatePinner getCertificatePinner() {
        return new CertificatePinner.Builder()
                .add("sha256/", "sha1/")
                .build();
    }

    private OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new GenericInterceptor())
                .addInterceptor(getOnTopInterceptor())
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(isShowLog() ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
                .certificatePinner(getCertificatePinner())
                .build();
    }

    protected Retrofit.Builder getBaseRetrofitBuilder() {
        Log.d("TRUST", "getBaseRetrofitBuilder: "+ getClient().connectTimeoutMillis());
        return new Retrofit.Builder().baseUrl(getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(getClient());
    }

    public void setApi(T api) {
        this.api = api;
    }


    public T getApi() {
        if (api == null) {
            api = getBaseRetrofitBuilder()
                    .build()
                    .create(getApiClassType());
        }
        return api;
    }


    public T getRxApi() {
        if (api == null) {
            api = getBaseRetrofitBuilder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build()
                    .create(getApiClassType());
        }
        return api;
    }

}
