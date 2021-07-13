package com.lepu.bt.demo.ui.place

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.lepu.bt.demo.R
import com.lepu.bt.demo.databinding.ActivityMainBinding
import com.lepu.bt.demo.databinding.ActivityPlaceBinding

class PlaceActivity : AppCompatActivity() {


    private lateinit var mDataBinding: ActivityPlaceBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_place)

    }
}