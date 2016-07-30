package com.tanap.retrofit2rxandroid.model;

/**
 * Created by trusttanapruk on 7/28/2016.
 */
public class StatusProfileDao {
    StatusDao statusDao;
    ProfileDao profileDao;
    public StatusProfileDao(StatusDao statusDao, ProfileDao profileDao) {
        this.statusDao = statusDao;
        this.profileDao = profileDao;
    }

    public StatusDao getStatusDao() {
        return statusDao;
    }

    public ProfileDao getProfileDao() {
        return profileDao;
    }
}
