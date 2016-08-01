package com.tanap.retrofit2rxandroid.network.status;

import com.tanap.retrofit2rxandroid.network.generic.GenericNetworkClient;

import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * Created by trusttanapruk on 7/27/2016.
 */
public class MyStatusClient extends GenericNetworkClient {
    private static MyStatusClient service;

    @Override
    protected Class getApiClassType() {
        return StatusApi.class;
    }

    @Override
    protected Request getRequestInterceptor(Interceptor.Chain chain) {
        return chain.request().newBuilder()
                .addHeader("HEADER", "HEADER1")
                .build();
    }


    public static MyStatusClient getInstance() {
        if (service == null) {
            service = new MyStatusClient();
        }
        return service;
    }

    public StatusApi getJsonConnection() {
        return (StatusApi) getGenericJsonConnection();
    }

    public StatusApi getJsonRxConnection() {
        return (StatusApi) getGenericRxConnection();
    }

}
