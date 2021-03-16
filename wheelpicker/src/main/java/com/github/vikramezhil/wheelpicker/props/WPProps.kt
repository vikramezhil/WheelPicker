package com.github.vikramezhil.wheelpicker.props

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.LinearLayout.HORIZONTAL
import com.github.vikramezhil.wheelpicker.R

data class WheelPickerProperties(
    var context: Context,

    var itemsList: ArrayList<String> = ArrayList(),

    var bgColor: Int = Color.BLACK,

    var selectedItemBgColor: Int = Color.BLACK,

    var selectedItemTxtColor: Int = Color.WHITE,

    var unselectedItemBgColor: Int = Color.BLACK,

    var unselectedItemTxtColor: Int = Color.WHITE,

    var dividerColor: Int = Color.WHITE,

    var enableItemHighlight: Boolean = true,

    var showDivider: Boolean = true,

    var itemsTxtBold: Boolean = false,

    var itemsTxtItalic: Boolean = false,

    var infiniteScrolling: Boolean = false,

    var scaleDownEnabled: Boolean = false,

    var scrolling: Boolean = false,

    var initialized: Boolean = false,

    var viewRefreshed: Boolean = false,

    var itemsTxtSize: Float = context.resources.getDimension(R.dimen.wp_txt_size),

    var selectedItemTxtAlpha: Float = 1f,

    var unselectedItemsTxtAlpha: Float = 1f,

    var height: Int = context.resources.getDimensionPixelSize(R.dimen.wp_min_height),

    var vPadding: Int = context.resources.getDimensionPixelSize(R.dimen.wp_v_padding),

    var hPadding: Int = context.resources.getDimensionPixelSize(R.dimen.wp_h_padding),

    var itemsTextStyle: Int = 0,

    var selectedItemPos: Int = 0,

    var visibility: Int = View.VISIBLE,

    var orientation: Int = HORIZONTAL
)

interface OnWheelPickerListener {
    /**
     * Sends an update when a wheel picker item is selected
     * @param position Int The item position
     * @param value String The item value
     */
    fun onItemSelected(position: Int, value: String)

    /**
     * Sends an update when wheel picker is refreshed
     * @param list ArrayList<String> The items list
     * @param position Int The item position
     * @param value String The item value
     */
    fun onRefreshed(list: ArrayList<String>, position: Int, value: String)

    /**
     * Sends an update when scrolling is triggered
     */
    fun onScrolling()
}