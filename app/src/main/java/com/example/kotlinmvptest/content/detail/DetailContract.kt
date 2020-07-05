package com.example.kotlinmvptest.content.detail

import com.example.kotlinmvptest.model.base.BaseContract
import com.example.kotlinmvptest.model.service.park.Result
import io.reactivex.rxjava3.core.Single


public interface DetailContract{

    interface View : BaseContract.View {

    }

    interface Presenter : BaseContract.Presenter<View> {

        fun setPlantResult(plantResult: com.example.kotlinmvptest.model.service.plant.Result)
        fun getPlantResult(): com.example.kotlinmvptest.model.service.plant.Result
    }
}