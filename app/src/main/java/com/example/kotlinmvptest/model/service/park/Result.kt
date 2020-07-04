package com.example.kotlinmvptest.model.service.park

import com.google.gson.annotations.SerializedName

/**
"E_Pic_URL": "http://www.zoo.gov.tw/iTAP/05_Exhibit/01_FormosanAnimal.jpg",
"E_Geo": "MULTIPOINT ((121.5805931 24.9985962))",
"E_Info": "臺灣動物區以臺灣原生動物與棲息環境為展示重點，佈置模擬動物原生棲地之生態環境，讓動物表現如野外般自然的生活習性，引導觀賞者更正確地認識本土野生動物。臺灣位處於亞熱帶，雨量充沛、氣候溫暖，擁有各種地形景觀，因而孕育了豐富龐雜的生物資源。",
"E_no": "1",
"E_Category": "戶外區",
"E_Name": "臺灣動物區",
"E_Memo": "",
"_id": 1,
"E_URL": "http://www.zoo.gov.tw/introduce/gq.aspx?tid=12"
 **/
data class Result(
    @SerializedName("E_Pic_URL") val picUrl: String,
    @SerializedName("E_Geo") val geo: String,
    @SerializedName("E_Info") val info: String,
    @SerializedName("E_no") val no: String,
    @SerializedName("E_Category") val category: String,
    @SerializedName("E_Name") val name: String,
    @SerializedName("E_Memo") val memo: String,
    @SerializedName("_id") val id: String,
    @SerializedName("E_URL") val url: String
)