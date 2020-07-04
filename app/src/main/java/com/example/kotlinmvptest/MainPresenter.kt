package com.example.kotlinmvptest

import com.example.kotlinmvptest.model.base.BasePresenter
import com.example.kotlinmvptest.model.service.RetrofitModel
import com.example.kotlinmvptest.model.service.park.Park
import com.example.kotlinmvptest.model.service.park.ParkResponse
import com.example.kotlinmvptest.model.service.park.Result
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response

class MainPresenter : BasePresenter<MainContract.View>(), MainContract.Presenter {
    private var currentCount = 0
    private var limit = 10
    private var parkList: MutableList<Result> = arrayListOf()

    override fun getParkInfo(): Single<MutableList<Result>> {
        val normalDataModel: RetrofitModel.DataService = RetrofitModel.getmInstance().getAPI()
        return normalDataModel.getParkType("", limit.toString(), currentCount.toString())
            .flatMap { response ->
                if (response.isSuccessful && response.code() == 200 && response.body()!!.result.count != "null") {
                    parkList.addAll(response.body()!!.result.results)
                    currentCount+= limit
                }
                Single.just(parkList)
            }.subscribeOn(Schedulers.io())
    }
}