package com.tanap.retrofit2rxandroid.network.profile;

import com.tanap.retrofit2rxandroid.model.ProfileDao;
import com.tanap.retrofit2rxandroid.model.StatusDao;
import com.tanap.retrofit2rxandroid.model.StatusProfileDao;
import com.tanap.retrofit2rxandroid.network.generic.GenericNetworkManager;
import com.tanap.retrofit2rxandroid.network.status.MyStatusManager;

import rx.Single;
import rx.functions.Func2;

/**
 * Created by trusttanapruk on 7/27/2016.
 */
public class ProfileManager extends GenericNetworkManager {
    private static ProfileManager manager;

    public static ProfileManager getInstance() {
        if (manager == null) {
            manager = new ProfileManager();
        }
        return manager;
    }


    public Single<ProfileDao> getProfile() {
        Single<ProfileDao> observable = ProfileService.getInstance().getJsonRxConnection().getProfile();
        return setDefaultBehavior(observable, ProfileDao.class);
    }


    public Single<StatusProfileDao> getStatusAndProfile() {
        Single<StatusDao> statusDao = MyStatusManager.getInstance().getMyStatusRx();
        Single<ProfileDao> profileDao = getProfile();
        Single<StatusProfileDao> statusProfileDao = Single.zip(statusDao, profileDao, new Func2<StatusDao, ProfileDao, StatusProfileDao>() {
            @Override
            public StatusProfileDao call(StatusDao statusDao, ProfileDao profileDao) {
                return new StatusProfileDao(statusDao, profileDao);
            }
        });

        return setDefaultBehavior(statusProfileDao, StatusProfileDao.class);
    }

}
