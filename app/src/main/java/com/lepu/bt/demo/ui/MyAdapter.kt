package com.lepu.bt.demo.ui


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * =================================================================================================
 * 默认描述
 *
 * Author: qixin
 * Date: 2021/7/8 下午 2:21
 * =================================================================================================
 */
class MyAdapter() : RecyclerView.Adapter<VH>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


}

fun f(view: View){

}

sealed class  VH(view: View) : RecyclerView.ViewHolder(view)

class VH1 (view: View) : VH(view) {

}

class VH2(view: View) : VH(view) {

}

