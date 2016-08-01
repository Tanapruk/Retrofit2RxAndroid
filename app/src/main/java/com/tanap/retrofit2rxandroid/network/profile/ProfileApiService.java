package com.tanap.retrofit2rxandroid.network.profile;

import com.tanap.retrofit2rxandroid.network.generic.GenericApiService;

import okhttp3.Request;

/**
 * Created by trusttanapruk on 7/28/2016.
 */
public class ProfileApiService extends GenericApiService<ProfileApi> {
    private static ProfileApiService service;

    public static ProfileApiService getInstance() {
        if (service == null) {
            service = new ProfileApiService();
        }
        return service;
    }

    @Override
    protected Class<ProfileApi> getApiClassType() {
        return ProfileApi.class;
    }

    @Override
    protected Request.Builder getRequestInterceptor(Request.Builder requestBuilder) {
        return requestBuilder
                .addHeader("HEADER", "ProfileHeader");
    }

}
