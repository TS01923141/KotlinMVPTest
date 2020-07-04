package com.example.kotlinmvptest.model.service.park

import com.google.gson.annotations.SerializedName

data class ParkResponse(
    @SerializedName("result") val result : Park
)