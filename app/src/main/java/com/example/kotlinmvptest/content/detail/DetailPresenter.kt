package com.example.kotlinmvptest.content.detail

import com.example.kotlinmvptest.model.base.BasePresenter
import com.example.kotlinmvptest.model.service.plant.Result

class DetailPresenter : BasePresenter<DetailContract.View>(), DetailContract.Presenter {
    private lateinit var plantResult : Result

    override fun setPlantResult(plantResult : Result){
        this.plantResult = plantResult
    }

    override fun getPlantResult() : Result{
        return plantResult
    }
}