package com.lepu.bt.demo

import android.os.ParcelUuid
import com.blankj.utilcode.util.LogUtils
import no.nordicsemi.android.support.v18.scanner.*

/**
 * =================================================================================================
 * 默认描述
 *
 * Author: qixin
 * Date: 2021/6/30 上午 11:09
 * =================================================================================================
 */
class ScannerManager {
    companion object {
        fun scan() {
            val scanner = BluetoothLeScannerCompat.getScanner()

            val setting = ScanSettings.Builder()
                .setLegacy(false)
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                .setReportDelay(5000)
                .setUseHardwareBatchingIfSupported(true)
                .build()

            // val BASE_UUID = ParcelUuid.fromString("00000000-0000-1000-8000-00805F9B34FB")00002902-0000-1000-8000-00805f9b34fb
            val serviceUuid = ScanFilter.Builder()
                .setServiceUuid(ParcelUuid.fromString("00000000-0000-1000-8000-00805F9B34FB"))
                .build()
            val filters = listOf<ScanFilter>(serviceUuid)

//        scanner.startScan(filters, setting, scanCallback)
            scanner.startScan(scanCallback)
        }


        private val scanCallback = object : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult) {
                super.onScanResult(callbackType, result)
                result.device.name?.let {
                    LogUtils.d("device name $it")
                }
            }

            override fun onBatchScanResults(results: MutableList<ScanResult>) {
                super.onBatchScanResults(results)
                LogUtils.d(results)
            }

            override fun onScanFailed(errorCode: Int) {
                super.onScanFailed(errorCode)
                LogUtils.d(errorCode)
            }

        }
    }
}