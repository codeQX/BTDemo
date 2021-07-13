package com.lepu.bt.demo.ui.place

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lepu.bt.demo.databinding.FragmentPlaceBinding
import com.lepu.bt.demo.ui.weather.WeatherActivity

/**
 * =================================================================================================
 * 默认描述
 *
 * Author: qixin
 * Date: 2021/7/13 上午 11:31
 * =================================================================================================
 */
class PlaceFragment:Fragment() {
    val viewModel by lazy {
        ViewModelProvider(this).get(PlaceViewModel::class.java)
    }

    private lateinit var mAdapter: PlaceAdapter

    private lateinit var mBinding:FragmentPlaceBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentPlaceBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity is PlaceActivity && viewModel.isPlaceSaved()) {
            val place = viewModel.getSavedPlace()
            val intent = Intent(context, WeatherActivity::class.java).apply {
                putExtra("location_lng", place.location.lng)
                putExtra("location_lat", place.location.lat)
                putExtra("place_name", place.name)
            }
            startActivity(intent)
            activity?.finish()
            return
        }

        mAdapter = PlaceAdapter(this, viewModel.placeList)
        mBinding.recyclerView.adapter = mAdapter

        mBinding.searchPlaceEdit.addTextChangedListener { text: Editable? ->
            val content = text.toString()
            if (content.isNotEmpty()){
                viewModel.searchPlaces(content)
            }else{
                mBinding.recyclerView.visibility = View.GONE
                mBinding.bgImageView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                mAdapter.notifyDataSetChanged()

            }

        }

        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer { result ->
            val places = result.getOrNull()
            if (places != null) {
                mBinding.recyclerView.visibility = View.VISIBLE
                mBinding.bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                mAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "未能查询到任何地点", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })

    }


}