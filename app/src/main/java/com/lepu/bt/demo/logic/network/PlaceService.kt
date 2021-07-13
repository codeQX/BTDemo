package com.lepu.bt.demo.logic.network

import com.lepu.bt.demo.App
import com.lepu.bt.demo.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * =================================================================================================
 * 默认描述
 *
 * Author: qixin
 * Date: 2021/7/12 下午 2:25
 * =================================================================================================
 */
interface PlaceService {
    @GET("v2/place?token=${App.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String):Call<PlaceResponse>

}