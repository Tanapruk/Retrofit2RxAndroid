package com.tanap.retrofit2rxandroid.network.profile;

import com.tanap.retrofit2rxandroid.network.generic.GenericNetworkClient;

import okhttp3.Request;

/**
 * Created by trusttanapruk on 7/28/2016.
 */
public class ProfileClient extends GenericNetworkClient {
    private static ProfileClient service;

    @Override
    protected Class getApiClassType() {
        return ProfileApi.class;
    }

    @Override
    protected Request.Builder getRequestInterceptor(Request.Builder requestBuilder) {
        return requestBuilder
                .addHeader("HEADER", "ProfileHeader");

    }

    public static ProfileClient getInstance() {
        if (service == null) {
            service = new ProfileClient();
        }
        return service;
    }

    public ProfileApi getJsonRxConnection() {
        return (ProfileApi) getGenericRxConnection();
    }
}
