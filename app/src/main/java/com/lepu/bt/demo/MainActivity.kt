package com.lepu.bt.demo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ToastUtils
import com.lepu.bt.demo.databinding.ActivityMainBinding
import com.lepu.bt.demo.ui.place.PlaceActivity
import com.polidea.rxandroidble2.RxBleClient
import com.polidea.rxandroidble2.RxBleConnection
import com.polidea.rxandroidble2.RxBleDevice
import com.polidea.rxandroidble2.scan.ScanFilter
import com.polidea.rxandroidble2.scan.ScanResult
import com.polidea.rxandroidble2.scan.ScanSettings
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import no.nordicsemi.android.support.v18.scanner.*

/**
 * ppg
 * 00000000000211E19AB40002A5D5C51B
 * 00000040000111E1AC360002A5D5C51B
 *
 * 温湿度
 * 00000000000111E19AB40002A5D5C51B
 * 001D0000-0001-11E1-AC36-0002A5D5C51B
 * 001d0000-0001-11e1-ac36-0002a5d5c51b
 *
 * 温度
 * 00000000000111E19AB40002A5D5C51B
 * 00E00000000111E1AC360002A5D5C51B
 * 00e00000-0001-11e1-ac36-0002a5d5c51b
 */
class MainActivity : AppCompatActivity() {

    private var scanDisposable: Disposable? = null
    private var flowDisposable: Disposable? = null

    private val rxBleClient = App.rxBleClient

    private lateinit var mAdapter: SearchListAdapter
    private lateinit var mDataBinding: ActivityMainBinding


    private var connectionDisposable: Disposable? = null

    private var stateDisposable: Disposable? = null

    private lateinit var connectionObservable: Observable<RxBleConnection>

    private val disconnectTriggerSubject = PublishSubject.create<Unit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(mDataBinding.toolbar)

        mAdapter = SearchListAdapter()
        mDataBinding.recyclerView.adapter = mAdapter

        mAdapter.setOnItemClickListener { adapter, view, position ->
            val bleDevice: RxBleDevice = mAdapter.getItem(position)

            bleDevice.observeConnectionStateChanges()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { state: RxBleConnection.RxBleConnectionState ->
                    LogUtils.d(state)
                    when (state) {
                        RxBleConnection.RxBleConnectionState.CONNECTING -> {

                        }
                        RxBleConnection.RxBleConnectionState.CONNECTED -> {

                        }
                        RxBleConnection.RxBleConnectionState.DISCONNECTING -> {

                        }
                        RxBleConnection.RxBleConnectionState.DISCONNECTED -> {

                        }

                    }
                }
                .let { stateDisposable = it }

            connectionObservable = bleDevice.establishConnection(false)
                .takeUntil(disconnectTriggerSubject)

            connectionObservable.observeOn(AndroidSchedulers.mainThread())
                .doFinally { dispose() }
                .subscribe({ onConnectionReceived() }, { onConnectionFailure(it) })
                .let { connectionDisposable = it }

        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_place->{
                startActivity(Intent(this, PlaceActivity::class.java))
            }
        }
        return true
    }
    private fun triggerDisconnect() = disconnectTriggerSubject.onNext(Unit)

    private fun dispose() {
        connectionDisposable = null
//        updateUI()
    }

    private fun onConnectionFailure(throwable: Throwable) =
        showSnackbarShort("Connection error: $throwable")

    private fun onConnectionReceived() = showSnackbarShort("Connection received")

    override fun onResume() {

        super.onResume()
        when (rxBleClient.state) {
            RxBleClient.State.READY -> {
                readyScan()
            }
            RxBleClient.State.BLUETOOTH_NOT_AVAILABLE -> {
                //不支持蓝牙
                ToastUtils.showShort("不支持蓝牙")
            }
            RxBleClient.State.LOCATION_PERMISSION_NOT_GRANTED -> {
                //location权限未授权，无法搜索或者连接蓝牙
                ToastUtils.showShort("未授予权限")

                PermissionUtils.permission(PermissionConstants.LOCATION)
                    .callback(object : PermissionUtils.FullCallback{

                        override fun onGranted(granted: MutableList<String>) {
                            readyScan()
                        }

                        override fun onDenied(
                            deniedForever: MutableList<String>,
                            denied: MutableList<String>
                        ) {
                        }

                    })
                    .request()

            }
            RxBleClient.State.BLUETOOTH_NOT_ENABLED -> {
                //蓝牙不可用
                ToastUtils.showShort("蓝牙不可用")

            }
            RxBleClient.State.LOCATION_SERVICES_NOT_ENABLED -> {
                //系统位置服务未开启，不能搜索
                ToastUtils.showShort("系统位置服务未开启")

            }
            else -> {
                ToastUtils.showShort("蓝牙状态异常")
            }
        }

    }

    private fun readyScan() {
        //一切正常，开始搜索蓝牙设备
        scanDisposable = scanDevices()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result: ScanResult ->
                result.bleDevice.name?.let { name ->
//                            LogUtils.d("device name $name")
                    if (!mAdapter.data.contains(result.bleDevice)) {
                        mAdapter.addData(result.bleDevice)
                    }
                }
            }, {


            })
    }

    private fun scanDevices(): Observable<ScanResult> {
        val settings = ScanSettings.Builder()
            .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .build()

        val filter = ScanFilter.Builder()
            .setDeviceName("NON-GLU")
//            .setDeviceAddress("01:02:03:AB:CD:EF")
            .build()

        return rxBleClient.scanBleDevices(settings, filter)
    }

    private fun observerState() {
        flowDisposable = rxBleClient.observeStateChanges()
            .switchMap { state ->
                LogUtils.d("State $state")

                when (state) {
                    RxBleClient.State.READY -> {
                        //一切正常，开始搜索蓝牙设备
                        return@switchMap scanDevices()
                    }
                    RxBleClient.State.BLUETOOTH_NOT_AVAILABLE -> {
                        //不支持蓝牙
                        return@switchMap Observable.empty()
                    }
                    RxBleClient.State.LOCATION_PERMISSION_NOT_GRANTED -> {
                        //location权限未授权，无法搜索或者连接蓝牙
                        return@switchMap Observable.empty()
                    }
                    RxBleClient.State.BLUETOOTH_NOT_ENABLED -> {
                        //蓝牙不可用
                        return@switchMap Observable.empty()
                    }
                    RxBleClient.State.LOCATION_SERVICES_NOT_ENABLED -> {
                        //位置服务未开启，不能搜索
                        return@switchMap Observable.empty()
                    }
                    else -> {
                        return@switchMap Observable.empty()
                    }
                }
            }
            .subscribe { result: ScanResult ->
                result.bleDevice.name?.let {
                    LogUtils.d("device name $it")
                }
            }

    }

    override fun onDestroy() {
        super.onDestroy()
        scanDisposable?.dispose()
        flowDisposable?.dispose()
    }

}