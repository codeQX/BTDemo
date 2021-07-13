package com.lepu.bt.demo.logic

import androidx.lifecycle.liveData
import com.lepu.bt.demo.logic.model.Place
import com.lepu.bt.demo.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException

/**
 * =================================================================================================
 * 默认描述
 *
 * Author: qixin
 * Date: 2021/7/12 下午 4:14
 * =================================================================================================
 */
object Repository {
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            }else{
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
        emit(result)
    }
}