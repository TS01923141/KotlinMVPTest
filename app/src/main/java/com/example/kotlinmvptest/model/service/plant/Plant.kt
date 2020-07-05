package com.example.kotlinmvptest.model.service.plant

import com.google.gson.annotations.SerializedName

/**
 *
"LIMIT_UPDATE": 1000,
"offset": 0,
"count": 110,
"sort": "",
"results": []
 */
data class Plant(
    @SerializedName("limit") val limit : String,
    @SerializedName("offset") val offset : String,
    @SerializedName("count") val count : Int,
    @SerializedName("sort") val sort : String,
    @SerializedName("results") val results : ArrayList<Result>
)