package com.lepu.bt.demo.logic.network

import retrofit2.await

/**
 * =================================================================================================
 * 默认描述
 *
 * Author: qixin
 * Date: 2021/7/12 下午 3:49
 * =================================================================================================
 */
object SunnyWeatherNetwork {

    //地区服务
    suspend fun searchPlaces(query: String) =
        ServiceCreator.placeService.searchPlaces(query).await()

    //获取实时和未来天气
    suspend fun getDailyWeather(lng: String, lat: String) =
        ServiceCreator.weatherService.getDailyWeather(lng, lat).await()

    suspend fun getRealtimeWeather(lng: String, lat: String) =
        ServiceCreator.weatherService.getRealtimeWeather(lng, lat).await()

}