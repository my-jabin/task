package com.jiujiu.task.ui.widget

import android.content.Context
import android.widget.ArrayAdapter
import com.jiujiu.task.R

class AutoCompletePlaceAdapter constructor(context: Context, private val data: List<String>)

    : ArrayAdapter<String>(context, R.layout.dropdown_menu_popup_item, data) {

//    fun findPositionByTypeId(id: Int): Int {
//        val type = data.find { type -> type.id == id }
//        return types.indexOf(type)
//    }
//
//    fun getTypeByPosition(position: Int): ProductType? {
//        return if (position >= 0 && position < types.size) types[position]
//        else null
//    }

}