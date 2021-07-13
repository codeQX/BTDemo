package com.lepu.bt.demo.logic.model

import com.google.gson.annotations.SerializedName

/**
 * =================================================================================================
 * 默认描述
 *
 * Author: qixin
 * Date: 2021/7/12 下午 2:22
 * =================================================================================================
 */
data class PlaceResponse(val status: String, val places: List<Place>)

data class Place(
    val name: String,
    val location: Location,
    @SerializedName("formatted_address") val address: String
)

data class Location(val lng: String, val lat: String)