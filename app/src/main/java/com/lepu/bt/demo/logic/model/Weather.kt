package com.lepu.bt.demo.logic.model

/**
 * =================================================================================================
 * 默认描述
 *
 * Author: qixin
 * Date: 2021/7/13 下午 2:51
 * =================================================================================================
 */
data class Weather(val realtime: RealtimeResponse.Realtime, val daily: DailyResponse.Daily)