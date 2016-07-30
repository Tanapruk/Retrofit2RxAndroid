package com.tanap.retrofit2rxandroid.network.status;

import com.tanap.retrofit2rxandroid.network.generic.GenericNetworkService;

/**
 * Created by trusttanapruk on 7/27/2016.
 */
public class MyStatusService extends GenericNetworkService {
    private static MyStatusService service;

    @Override
    protected Class getTClass() {
        return StatusApi.class;
    }

    public static MyStatusService getInstance() {
        if (service == null) {
            service = new MyStatusService();
        }
        return service;
    }

    public StatusApi getJsonConnection() {
        return (StatusApi) getGenericJsonConnection();
    }

    public StatusApi getJsonRxConnection() {
        return (StatusApi) getGenericRxConnection();
    }

}
