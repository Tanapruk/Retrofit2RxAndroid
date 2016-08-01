package com.tanap.retrofit2rxandroid.network.profile;

import com.tanap.retrofit2rxandroid.model.ProfileDao;
import com.tanap.retrofit2rxandroid.model.StatusDao;
import com.tanap.retrofit2rxandroid.model.StatusProfileDao;
import com.tanap.retrofit2rxandroid.network.generic.GenericNetworkController;
import com.tanap.retrofit2rxandroid.network.status.MyStatusController;

import rx.Single;
import rx.functions.Func2;

/**
 * Created by trusttanapruk on 7/27/2016.
 */
public class ProfileController extends GenericNetworkController {
    private static ProfileController manager;

    public static ProfileController getInstance() {
        if (manager == null) {
            manager = new ProfileController();
        }
        return manager;
    }


    public Single<ProfileDao> getProfile() {
        return ProfileApiService.getInstance().getRxApi().getProfile().compose(applyErrorHandling(ProfileDao.class));
    }


    public Single<StatusProfileDao> getStatusAndProfile() {
        Single<StatusDao> statusDao = MyStatusController.getInstance().getMyStatusRx();
        Single<ProfileDao> profileDao = getProfile();
        Single<StatusProfileDao> statusProfileDao = Single.zip(statusDao, profileDao, new Func2<StatusDao, ProfileDao, StatusProfileDao>() {
            @Override
            public StatusProfileDao call(StatusDao statusDao, ProfileDao profileDao) {
                return new StatusProfileDao(statusDao, profileDao);
            }
        });

        return statusProfileDao.compose(applyErrorHandling(StatusProfileDao.class));
    }

}
