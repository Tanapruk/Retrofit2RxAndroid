package com.tanap.retrofit2rxandroid.network.profile;


import com.tanap.retrofit2rxandroid.model.ProfileDao;

import retrofit2.http.GET;
import rx.Single;

/**
 * Created by trusttanapruk on 7/27/2016.
 */

public interface ProfileApi {

    @GET("service/profile")
    Single<ProfileDao> getProfile();
}
