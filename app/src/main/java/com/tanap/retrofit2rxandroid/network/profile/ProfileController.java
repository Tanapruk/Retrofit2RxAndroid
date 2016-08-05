package com.tanap.retrofit2rxandroid.network.profile;

import com.tanap.retrofit2rxandroid.model.ProfileDao;
import com.tanap.retrofit2rxandroid.model.StatusDao;
import com.tanap.retrofit2rxandroid.model.StatusProfileDao;
import com.tanap.retrofit2rxandroid.network.generic.GenericNetworkController;
import com.tanap.retrofit2rxandroid.network.generic.SingleSubscriberImpl;
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

    public void getStatusAndProfile() {

        Single<StatusDao> statusDao = MyStatusController.getInstance().getMyStatusRx();
        Single<ProfileDao> profileDao = ProfileApiService.getInstance().getRxApi().getProfile().compose(applySchedulerAndErrorHandling(ProfileDao.class));

        Single<StatusProfileDao> statusProfileDao = Single.zip(statusDao, profileDao, new Func2<StatusDao, ProfileDao, StatusProfileDao>() {
            @Override
            public StatusProfileDao call(StatusDao statusDao, ProfileDao profileDao) {
                return new StatusProfileDao(statusDao, profileDao);
            }
        });
        statusProfileDao.compose(applySchedulerAndErrorHandling(StatusProfileDao.class)).subscribe(
                new SingleSubscriberImpl<StatusProfileDao>(StatusProfileDao.class, StatusDao.class, ProfileDao.class));
    }


}
