package com.tanap.retrofit2rxandroid.network.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by trusttanapruk on 8/5/2016.
 */
public class SubscribingState {
    private static SubscribingState subscribingState;
    private List<String> serviceList;

    private SubscribingState() {
        serviceList = new ArrayList<>();
    }

    public static SubscribingState getInstance() {
        if (subscribingState == null) {
            subscribingState = new SubscribingState();
        }
        return subscribingState;
    }

    public boolean isAnyInProgress() {
        if (serviceList == null || serviceList.isEmpty()) {
            return false;
        }
        return serviceList.size() > 0;
    }

    public List<String> getServiceList() {
        return serviceList;
    }


    public boolean isInProgress(Class<?> className) {
        return serviceList.contains(className.getSimpleName());
    }

    public void start(Class<?>... classOfDaoList) {
        for (Class<?> aClass : classOfDaoList) {
            serviceList.add(aClass.getSimpleName());
        }

    }

    public void end(Class<?>... classOfObjectList) {
        for (Class<?> aClass : classOfObjectList) {
            ListIterator<String> listIterator = serviceList.listIterator();
            while (listIterator.hasNext()) {
                if (listIterator.next().equalsIgnoreCase(aClass.getSimpleName())) {
                    listIterator.remove();
                }
            }
        }
    }


}
