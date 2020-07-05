package com.example.kotlinmvptest.content

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.kotlinmvptest.model.CommonValues
import com.example.kotlinmvptest.model.service.RetrofitModel
import com.example.kotlinmvptest.model.service.park.Result
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class ContentViewModel(app: Application) : AndroidViewModel(app) {
    private var currentCount = 0
    lateinit var parkResult : Result
    var plantList : MutableList<com.example.kotlinmvptest.model.service.plant.Result> = arrayListOf()

    fun getPlantInfo() : Single<MutableList<com.example.kotlinmvptest.model.service.plant.Result>>{
        val normalDataModel: RetrofitModel.DataService = RetrofitModel.getmInstance().getAPI()
        return normalDataModel.getPlantType(parkResult.name, "", "")
            .flatMap { response ->
                if (response.isSuccessful && response.code() == 200){
//                    && response.body()!!.result.count != (plantList.size + response.body()!!.result.results.size)){
                    plantList.addAll(response.body()!!.result.results)
                    currentCount+= CommonValues.LIMIT_UPDATE
                }
                Single.just(plantList)
            }.subscribeOn(Schedulers.io())
    }
}