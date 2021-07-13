package com.lepu.bt.demo.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


/**
 * =================================================================================================
 * 默认描述
 *
 * Author: qixin
 * Date: 2021/7/12 下午 3:49
 * =================================================================================================
 */
object SunnyWeatherNetwork {

    private val placeService=ServiceCreator.create<PlaceService>()

    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()

    //retrofit kotlin代码已经有了关于这部分的代码
//    private suspend fun <T> Call<T>.await():T{
//        return suspendCoroutine { continuation ->
//            enqueue(object : Callback<T>{
//                override fun onResponse(call: Call<T>, response: Response<T>) {
//                    val body = response.body()
//                    if (body != null) continuation.resume(body)
//                    else continuation.resumeWithException(RuntimeException("response body is null"))
//                }
//
//                override fun onFailure(call: Call<T>, t: Throwable) {
//                    continuation.resumeWithException(t)
//                }
//            })
//
//        }
//    }
}