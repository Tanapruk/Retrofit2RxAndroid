package com.tanap.retrofit2rxandroid.network.status;

import com.tanap.retrofit2rxandroid.URL;
import com.tanap.retrofit2rxandroid.model.StatusDao;
import com.tanap.retrofit2rxandroid.network.generic.GenericNetworkController;

import rx.Single;

/**
 * Created by trusttanapruk on 7/27/2016.
 */
public class MyStatusController extends GenericNetworkController {
    private static MyStatusController manager;

    public static MyStatusController getInstance() {
        if (manager == null) {
            manager = new MyStatusController();
        }
        return manager;
    }


    public Single<StatusDao> getMyStatusRx() {
        return MyStatusApiService.newInstance(URL.URL_LOCAL_TRUST).getRxApi().getStatus().compose(applySchedulerAndErrorHandling(StatusDao.class));
    }

}
