package com.example.kotlinmvptest.model.service

import com.example.kotlinmvptest.model.service.park.ParkResponse
import com.example.kotlinmvptest.model.service.plant.PlantResponse
import io.reactivex.rxjava3.core.Single
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

public class RetrofitModel {
    companion object {
        private lateinit var mInstance: RetrofitModel
        private lateinit var dataService: DataService

        @JvmStatic
        fun getmInstance(): RetrofitModel {
            if (!this::mInstance.isInitialized) {
                mInstance = RetrofitModel()
            }
            return mInstance
        }
    }

    private val BASE_URL = "https://data.taipei/"

    public interface DataService {
        @GET("/api/v1/dataset/5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a?scope=resourceAquire")
        fun getParkType(
            @Query("q") q: String,
            @Query("limit") limit: String,
            @Query("offset") offset: String
        ): Single<Response<ParkResponse>>

        @GET("/api/v1/dataset/f18de02f-b6c9-47c0-8cda-50efad621c14?scope=resourceAquire")
        fun getPlantType(
            @Query("q") q: String,
            @Query("limit") limit: String,
            @Query("offset") offset: String
        ): Single<Response<PlantResponse>>
    }

    internal var okHttpClient = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(90, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .retryOnConnectionFailure(true)
        .build()

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
        dataService = retrofit.create<DataService>(DataService::class.java)
    }

    fun getAPI(): DataService {
        return dataService
    }
}