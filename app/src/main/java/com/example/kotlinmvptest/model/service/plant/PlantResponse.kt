package com.example.kotlinmvptest.model.service.plant

import com.google.gson.annotations.SerializedName

data class PlantResponse(
    @SerializedName("result") val result : Plant
)