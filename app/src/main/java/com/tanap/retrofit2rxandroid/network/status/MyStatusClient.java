package com.tanap.retrofit2rxandroid.network.status;

import com.tanap.retrofit2rxandroid.network.generic.GenericNetworkClient;

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
    protected Request.Builder getRequestInterceptor(Request.Builder requestBuilder) {
        return requestBuilder
                .addHeader("HEADER", "MyStatusHeader");

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
