package com.example.stdying

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by li on 2019/6/13.
 *
 * @author li
 */
class MainAdapter(resId: Int, items: List<String>)
    : BaseQuickAdapter<String, BaseViewHolder>(resId, items) {
    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.setText(R.id.tv_name, item)
    }
}