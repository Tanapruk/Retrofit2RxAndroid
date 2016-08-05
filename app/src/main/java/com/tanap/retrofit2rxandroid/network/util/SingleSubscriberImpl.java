package com.tanap.retrofit2rxandroid.network.util;

import com.hwangjr.rxbus.RxBus;

import rx.SingleSubscriber;

/**
 * Created by trusttanapruk on 8/5/2016.
 */
public class SingleSubscriberImpl<T> extends SingleSubscriber<T> {

    //this Class<?>[] is for checking current state
    private Class<?>[] classOfDaoList;

    public SingleSubscriberImpl(Class<?>... classOfDaoList) {
        this.classOfDaoList = classOfDaoList;
    }

    @Override
    public void onSuccess(T value) {
        RxBus.get().post(value);
        SubscribingState.getInstance().end(classOfDaoList);
        this.unsubscribe();
    }

    @Override
    public void onError(Throwable error) {
        RxBus.get().post(error);
        SubscribingState.getInstance().end(classOfDaoList);
        this.unsubscribe();
    }
}
