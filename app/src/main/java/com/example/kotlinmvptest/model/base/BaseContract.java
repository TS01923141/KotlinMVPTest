package com.example.kotlinmvptest.model.base;


import android.content.Context;
import android.os.Bundle;
import androidx.lifecycle.Lifecycle;

public interface BaseContract {

    interface View {
    }

    interface Presenter<V extends BaseContract.View> {

        Bundle getStateBundle();

        void attachLifecycle(Lifecycle lifecycle);

        void detachLifecycle(Lifecycle lifecycle);

        void attachView(V view);

        void detachView();

        V getView();

        boolean isViewAttached();

        void onPresenterCreated();

        void onPresenterDestroy();
    }
}
