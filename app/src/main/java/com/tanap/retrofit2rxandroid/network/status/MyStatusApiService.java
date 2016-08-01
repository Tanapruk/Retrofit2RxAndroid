package com.tanap.retrofit2rxandroid.network.status;

import com.tanap.retrofit2rxandroid.network.generic.GenericApiService;

import okhttp3.Request;

/**
 * Created by trusttanapruk on 7/27/2016.
 */
public class MyStatusApiService extends GenericApiService<StatusApi> {
    private static MyStatusApiService service;

    @Override
    protected Class<StatusApi> getApiClassType() {
        return StatusApi.class;
    }

    @Override
    protected Request.Builder getRequestInterceptor(Request.Builder requestBuilder) {
        return requestBuilder
                .addHeader("HEADER", "MyStatusHeader");
    }


    public static MyStatusApiService getInstance() {
        if (service == null) {
            service = new MyStatusApiService();
        }
        return service;
    }


}
