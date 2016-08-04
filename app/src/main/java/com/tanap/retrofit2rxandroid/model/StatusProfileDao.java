package com.tanap.retrofit2rxandroid.model;

import org.parceler.Parcel;

/**
 * Created by trusttanapruk on 7/28/2016.
 */
@Parcel
public class StatusProfileDao {
    public StatusProfileDao() {
    }

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
