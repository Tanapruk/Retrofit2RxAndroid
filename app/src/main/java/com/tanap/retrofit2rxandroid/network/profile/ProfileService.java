package com.tanap.retrofit2rxandroid.network.profile;

import com.tanap.retrofit2rxandroid.network.generic.GenericNetworkService;

/**
 * Created by trusttanapruk on 7/28/2016.
 */
public class ProfileService extends GenericNetworkService {
    private static ProfileService service;

    @Override
    protected Class getTClass() {
        return ProfileApi.class;
    }

    public static ProfileService getInstance() {
        if (service == null) {
            service = new ProfileService();
        }
        return service;
    }

    public ProfileApi getJsonRxConnection() {
        return (ProfileApi) getGenericRxConnection();
    }
}
