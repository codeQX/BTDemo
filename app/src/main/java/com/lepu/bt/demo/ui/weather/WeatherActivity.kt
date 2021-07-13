package com.lepu.bt.demo.ui.weather

import android.content.Context
import android.graphics.Color
import android.hardware.input.InputManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lepu.bt.demo.R
import com.lepu.bt.demo.databinding.ActivityPlaceBinding
import com.lepu.bt.demo.databinding.ActivityWeatherBinding
import com.lepu.bt.demo.logic.model.Weather
import com.lepu.bt.demo.logic.model.getSky
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity() {
    val viewModel by lazy { ViewModelProvider(this).get(WeatherViewModel::class.java) }

     lateinit var mDataBinding: ActivityWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val decorView = window.decorView
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT

        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_weather)
        if (viewModel.locationLng.isEmpty()) {
            viewModel.locationLng = intent.getStringExtra("location_lng") ?: ""
        }

        if (viewModel.locationLat.isEmpty()) {
            viewModel.locationLat = intent.getStringExtra("location_lat") ?: ""
        }
        if (viewModel.placeName.isEmpty()) {
            viewModel.placeName = intent.getStringExtra("place_name") ?: ""
        }
        viewModel.weatherLiveData.observe(this, Observer { result ->
            val weather = result.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                Toast.makeText(this, "无法成功获取天气信息", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
            mDataBinding.swipeRefresh.isRefreshing = false
        })
        mDataBinding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary)

        refreshWeather()

        mDataBinding.swipeRefresh.setOnRefreshListener {
            refreshWeather()
        }

        mDataBinding.includeNowLayout.navBtn.setOnClickListener{
            mDataBinding.drawerLayout.openDrawer(GravityCompat.START)
        }
        mDataBinding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener{
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {
            }

            override fun onDrawerClosed(drawerView: View) {
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(drawerView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }

            override fun onDrawerStateChanged(newState: Int) {
            }

        })

    }

    fun refreshWeather() {
        viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat)
        mDataBinding.swipeRefresh.isRefreshing = true
    }

    private fun showWeatherInfo(weather: Weather) {
        mDataBinding.includeNowLayout.titleLayout.findViewById<TextView>(R.id.placeName).text =
            viewModel.placeName
        val realtime = weather.realtime
        val daily = weather.daily
        // 填充now.xml布局中的数据
        val currentTempText = "${realtime.temperature.toInt()} ℃"
        mDataBinding.includeNowLayout.root.findViewById<TextView>(R.id.currentTemp).text =
            currentTempText
        mDataBinding.includeNowLayout.root.findViewById<TextView>(R.id.currentSky).text =
            getSky(realtime.skycon).info
        val currentPM25Text = "空气指数 ${realtime.airQuality.aqi.chn.toInt()}"
        mDataBinding.includeNowLayout.root.findViewById<TextView>(R.id.currentAQI).text =
            currentPM25Text
        mDataBinding.includeNowLayout.root.setBackgroundResource(getSky(realtime.skycon).bg)
        // 填充forecast.xml布局中的数据
        mDataBinding.includeForecastLayout.forecastLayout.removeAllViews()

        val days = daily.skycon.size
        for (i in 0 until days) {
            val skycon = daily.skycon[i]
            val temperature = daily.temperature[i]
            val view = LayoutInflater.from(this).inflate(
                R.layout.forecast_item,
                mDataBinding.includeForecastLayout.forecastLayout, false
            )
            val dateInfo = view.findViewById(R.id.dateInfo) as TextView
            val skyIcon = view.findViewById(R.id.skyIcon) as ImageView
            val skyInfo = view.findViewById(R.id.skyInfo) as TextView
            val temperatureInfo = view.findViewById(R.id.temperatureInfo) as TextView
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            dateInfo.text = simpleDateFormat.format(skycon.date)
            val sky = getSky(skycon.value)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text = sky.info
            val tempText = "${temperature.min.toInt()} ~ ${temperature.max.toInt()} ℃"
            temperatureInfo.text = tempText
            mDataBinding.includeForecastLayout.forecastLayout.addView(view)
        }
        // 填充life_index.xml布局中的数据
        val lifeIndex = daily.lifeIndex
        mDataBinding.includeIndexLayout.coldRiskText.text = lifeIndex.coldRisk[0].desc
        mDataBinding.includeIndexLayout.dressingText.text = lifeIndex.dressing[0].desc
        mDataBinding.includeIndexLayout.ultravioletText.text = lifeIndex.ultraviolet[0].desc
        mDataBinding.includeIndexLayout.carWashingText.text = lifeIndex.carWashing[0].desc

        mDataBinding.weatherLayout.visibility = View.VISIBLE
    }
}