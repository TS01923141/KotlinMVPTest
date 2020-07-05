package com.example.kotlinmvptest

import com.example.kotlinmvptest.model.CommonValues
import com.example.kotlinmvptest.model.base.BasePresenter
import com.example.kotlinmvptest.model.service.RetrofitModel
import com.example.kotlinmvptest.model.service.park.Result
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class MainPresenter : BasePresenter<MainContract.View>(), MainContract.Presenter {
    private var currentCount = 0
    var parkList: MutableList<Result> = arrayListOf()

    override fun getParkInfo(): Single<MutableList<Result>> {
        val normalDataModel: RetrofitModel.DataService = RetrofitModel.getmInstance().getAPI()
        return normalDataModel.getParkType("", CommonValues.LIMIT_UPDATE.toString(), currentCount.toString())
            .flatMap { response ->
                if (response.isSuccessful && response.code() == 200
                    && response.body()!!.result.count != (parkList.size + response.body()!!.result.results.size)) {
                    parkList.addAll(response.body()!!.result.results)
                    currentCount+= CommonValues.LIMIT_UPDATE
                }
                Single.just(parkList)
            }.subscribeOn(Schedulers.io())
    }

    override fun getParkListSize() : Int {
        return parkList.size
    }
}