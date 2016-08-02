package com.tanap.retrofit2rxandroid.network.generic;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by trusttanapruk on 8/1/2016.
 */
public class GenericInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(getGenericRequest(chain.request().newBuilder()).build());
    }

    private Request.Builder getGenericRequest(Request.Builder builder) {
        return builder
                .addHeader("HEADER", "GENERIC_HEADER")
                .addHeader("HEADER2", "GENERIC_HEADER2");
    }


}
