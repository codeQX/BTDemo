package com.lepu.bt.demo

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.polidea.rxandroidble2.RxBleDevice

/**
 * =================================================================================================
 * 默认描述
 *
 * Author: qixin
 * Date: 2021/6/30 下午 3:29
 * =================================================================================================
 */
class SearchListAdapter(): BaseQuickAdapter<RxBleDevice, BaseViewHolder>(R.layout.item_search_list) {
    init {

    }

    override fun convert(holder: BaseViewHolder, item: RxBleDevice) {
        holder.setText(R.id.item_title, item.name)

    }
}