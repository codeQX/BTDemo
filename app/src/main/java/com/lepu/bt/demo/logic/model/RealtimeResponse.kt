package com.lepu.bt.demo.logic.model

import com.google.gson.annotations.SerializedName

/**
 * =================================================================================================
 * 默认描述
 *
 * Author: qixin
 * Date: 2021/7/13 下午 2:49
 * =================================================================================================
 */
data class RealtimeResponse(val status: String, val result: Result) {
    data class Result(val realtime: Realtime)
    data class Realtime(val skycon: String, val temperature: Float,
                        @SerializedName("air_quality") val airQuality: AirQuality)
    data class AirQuality(val aqi: AQI)
    data class AQI(val chn: Float)
}