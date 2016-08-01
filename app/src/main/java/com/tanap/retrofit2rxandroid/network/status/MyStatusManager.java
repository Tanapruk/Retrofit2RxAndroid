package com.tanap.retrofit2rxandroid.network.status;

import com.tanap.retrofit2rxandroid.model.StatusDao;
import com.tanap.retrofit2rxandroid.network.generic.GenericNetworkManager;

import rx.Single;

/**
 * Created by trusttanapruk on 7/27/2016.
 */
public class MyStatusManager extends GenericNetworkManager {
    private static MyStatusManager manager;

    public static MyStatusManager getInstance() {
        if (manager == null) {
            manager = new MyStatusManager();
        }
        return manager;
    }


    public Single<StatusDao> getMyStatusRx() {
        Single<StatusDao> observable = MyStatusClient.getInstance().getJsonRxConnection().getStatus();
        return setDefaultBehavior(observable, StatusDao.class);
    }

}
