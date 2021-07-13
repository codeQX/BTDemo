package com.lepu.bt.demo

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.blankj.utilcode.util.Utils
import com.polidea.rxandroidble2.LogConstants
import com.polidea.rxandroidble2.LogOptions
import com.polidea.rxandroidble2.RxBleClient

/**
 * =================================================================================================
 * 默认描述
 *
 * Author: qixin
 * Date: 2021/6/30 上午 10:05
 * =================================================================================================
 */
class App : Application() {

    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        lateinit var rxBleClient: RxBleClient
            private set

        const val TOKEN = "zeHtUMfoRgBchS9N"

    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        Utils.init(this)

        rxBleClient = RxBleClient.create(this)
        RxBleClient.updateLogOptions(
            LogOptions.Builder()
//                .setLogLevel(LogConstants.INFO)
                .setMacAddressLogSetting(LogConstants.MAC_ADDRESS_FULL)
                .setUuidsLogSetting(LogConstants.UUIDS_FULL)
                .setShouldLogAttributeValues(true)
                .build()
        )


    }
}