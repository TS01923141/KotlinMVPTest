package com.example.kotlinmvptest

import com.example.kotlinmvptest.model.base.BaseContract
import com.example.kotlinmvptest.model.service.park.Result
import io.reactivex.rxjava3.core.Single

public interface MainContract{

    interface View : BaseContract.View {

    }

    interface Presenter : BaseContract.Presenter<View> {

        fun getParkInfo(): Single<MutableList<Result>>
    }
}