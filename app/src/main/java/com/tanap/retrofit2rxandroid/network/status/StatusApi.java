package com.tanap.retrofit2rxandroid.network.status;


import com.tanap.retrofit2rxandroid.model.StatusDao;

import retrofit2.http.GET;
import rx.Single;

/**
 * Created by trusttanapruk on 7/27/2016.
 */

public interface StatusApi {
    @GET("service/simple")
    Single<StatusDao> getStatus();

}
