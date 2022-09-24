package com.github.vikramezhil.wheelpicker.view

import android.content.Context
import android.content.ContextWrapper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout.HORIZONTAL
import android.widget.LinearLayout.VERTICAL
import androidx.annotation.AttrRes
import androidx.lifecycle.LifecycleOwner
import com.github.vikramezhil.wheelpicker.R
import com.github.vikramezhil.wheelpicker.adapter.WPAdapter
import com.github.vikramezhil.wheelpicker.databinding.PickerWheelBinding
import com.github.vikramezhil.wheelpicker.manager.WPSliderManager
import com.github.vikramezhil.wheelpicker.props.OnWheelPickerListener
import com.github.vikramezhil.wheelpicker.props.WheelPickerProperties
import java.lang.Exception

/**
 * Wheel Picker View
 * @author vikram.ezhil
 */

class WheelPicker@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: PickerWheelBinding = PickerWheelBinding.inflate(LayoutInflater.from(context), this, true)
    private var properties = WheelPickerProperties(context)
    private var wheelPickerAdapter: WPAdapter? = null
    private var onWheelPickerListener: OnWheelPickerListener? = null

    init {
        getLifeCycleOwner()?.let { binding.lifecycleOwner = it }

        attrs?.let {
            val typedArray = context.theme.obtainStyledAttributes(it, R.styleable.WheelPicker, 0, 0)

            try {
                // General
                properties.background = typedArray.getDrawable(R.styleable.WheelPicker_wheelPickerBg) ?: properties.background
                properties.dividerColor = typedArray.getInt(R.styleable.WheelPicker_wheelPickerDividerColor, properties.dividerColor)
                properties.orientation = if(typedArray.getBoolean(R.styleable.WheelPicker_wheelPickerOrientationVertical, false)) {
                    VERTICAL
                } else {
                    HORIZONTAL
                }
                properties.infiniteScrolling = typedArray.getBoolean(R.styleable.WheelPicker_wheelPickerInfiniteScrolling, false)
                properties.scaleDownEnabled = typedArray.getBoolean(R.styleable.WheelPicker_wheelPickerScaleDownEnabled, false)
                properties.showDivider = typedArray.getBoolean(R.styleable.WheelPicker_wheelPickerShowDivider, true)

                // Items
                val items = typedArray.getResourceId(R.styleable.WheelPicker_wheelPickerItems, 0)
                if (items != 0) { properties.itemsList = typedArray.resources.getStringArray(items).toCollection(ArrayList()) }
                properties.itemsTextStyle = typedArray.getResourceId(R.styleable.WheelPicker_wheelPickerItemsTextStyle, properties.itemsTextStyle)
                properties.itemsTxtSize = typedArray.getDimension(R.styleable.WheelPicker_wheelPickerItemsTextSize, properties.itemsTxtSize)
                properties.itemsTxtBold = typedArray.getBoolean(R.styleable.WheelPicker_wheelPickerItemsTextBold, false)
                properties.itemsTxtItalic = typedArray.getBoolean(R.styleable.WheelPicker_wheelPickerItemsTextItalic, false)
                properties.unselectedItemsTxtAlpha = typedArray.getFloat(R.styleable.WheelPicker_wheelPickerItemsUnselectedTextAlpha, properties.unselectedItemsTxtAlpha)

                // Item
                properties.selectedItemPos = typedArray.getInt(R.styleable.WheelPicker_wheelPickerDefaultSelectedItemPos, 0)
                properties.selectedItemBgColor = typedArray.getInt(R.styleable.WheelPicker_wheelPickerItemSelectedBgColor, properties.selectedItemBgColor)
                properties.selectedItemTxtColor = typedArray.getInt(R.styleable.WheelPicker_wheelPickerItemSelectedTextColor, properties.selectedItemTxtColor)
                properties.unselectedItemBgColor = typedArray.getInt(R.styleable.WheelPicker_wheelPickerItemUnselectedBgColor, properties.unselectedItemBgColor)
                properties.unselectedItemTxtColor = typedArray.getInt(R.styleable.WheelPicker_wheelPickerItemUnselectedTextColor, properties.unselectedItemTxtColor)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                typedArray.recycle()
            }
        }

        // Setting the minimum height
        binding.wheelPicker.minimumHeight = properties.height

        // Setting the wheel picker background
        binding.wheelPicker.background = properties.background
        initWheelPicker()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        properties.height = h

        if (properties.orientation == VERTICAL) {
            properties.vPadding = (h/2) - properties.vPadding
            binding.wheelPicker.setPadding(0, properties.vPadding, 0, properties.vPadding)
        } else {
            properties.hPadding = (w/2) - properties.hPadding
            binding.wheelPicker.setPadding(properties.hPadding, 0, properties.hPadding, 0)
        }

        // Updating the adapter
        wheelPickerAdapter?.update(properties)
    }

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)

        if (properties.visibility != visibility && visibility == View.VISIBLE) {
            refreshWheelPicker(properties.selectedItemPos)
        }

        properties.visibility = visibility
    }

    /**
     * Gets the view lifecycle owner
     * @return The lifecycle owner attached to the view
     */
    private fun getLifeCycleOwner(): LifecycleOwner? {
        var viewContext = rootView.context
        while (viewContext is ContextWrapper) {
            if (viewContext is LifecycleOwner) {
                return viewContext
            }

            viewContext = viewContext.baseContext
        }

        return null
    }

    /**
     * Initializes the wheel picker
     */
    private fun initWheelPicker() {
        properties.initialized = true

        // Layout manager
        binding.wheelPicker.layoutManager = WPSliderManager(context, properties.orientation, properties.scaleDownEnabled, object: OnWheelPickerListener {
            override fun onItemSelected(position: Int, value: String) {
                properties.scrolling = false

                if (properties.viewRefreshed) {
                    properties.viewRefreshed = false

                    onWheelPickerListener?.onRefreshed(properties.itemsList, position, properties.itemsList[position])
                } else {
                    if (position != properties.selectedItemPos) {
                        properties.selectedItemPos = position

                        // Sending an update when an item is selected
                        onWheelPickerListener?.onItemSelected(position, properties.itemsList[position])
                    }

                    // Updating the adapter
                    wheelPickerAdapter?.update(properties)
                }
            }

            override fun onRefreshed(list: ArrayList<String>, position: Int, value: String) {}

            override fun onScrolling() {
                properties.scrolling = true

                // Updating the adapter
                wheelPickerAdapter?.update(properties)

                // Sending an update when the wheel is scrolling
                onWheelPickerListener?.onScrolling()
            }
        })

        // Adapter
        wheelPickerAdapter = WPAdapter(context, properties, object: OnWheelPickerListener {
            override fun onItemSelected(position: Int, value: String) {
                properties.scrolling = false

                if (position != properties.selectedItemPos) {
                    properties.selectedItemPos = position

                    // Scrolling to the selected item position
                    binding.wheelPicker.smoothScrollToPosition(position)

                    // Sending an update when an item is selected
                    onWheelPickerListener?.onItemSelected(position, properties.itemsList[position])
                }
            }

            override fun onRefreshed(list: ArrayList<String>, position: Int, value: String) {}

            override fun onScrolling() {
                // Sending an update when the wheel is scrolling
                onWheelPickerListener?.onScrolling()
            }
        })

        // Setting the adapter
        binding.wheelPicker.adapter = wheelPickerAdapter

        // Refreshing the wheel picker
        refreshWheelPicker(properties.selectedItemPos)
    }

    /**
     * Refreshes the wheel picker
     * @param position Int The wheel picker position
     */
    private fun refreshWheelPicker(position: Int = 0) {
        if (!properties.initialized) return

        properties.viewRefreshed = true

        val prevPosition = properties.selectedItemPos
        if (properties.itemsList.size > 0) {
            if (position < 0 || position >= properties.itemsList.size) {
                properties.selectedItemPos = 0
            } else {
                properties.selectedItemPos = position
            }
        }

        // Finding initial scroll to position to prevent long animation when there are
        // lot of items in the list during smooth scroll
        val scrollToPosition = when {
            properties.selectedItemPos > prevPosition -> {
                // Scroll down position
                properties.selectedItemPos - 1
            }
            properties.selectedItemPos < prevPosition -> {
                // Scroll up position
                properties.selectedItemPos + 1
            }
            else -> {
                properties.selectedItemPos
            }
        }

        if (scrollToPosition >= 0 &&  scrollToPosition < properties.itemsList.size) {
            binding.wheelPicker.scrollToPosition(scrollToPosition)
        }

        binding.wheelPicker.smoothScrollToPosition(properties.selectedItemPos)

        // Updating the adapter
        wheelPickerAdapter?.update(properties)
    }

    /**
     * Sets the wheel picker listener
     * @param onWheelPickerListener OnWheelViewListener? The class instance which implements the listener
     */
    fun setOnWheelPickerListener(onWheelPickerListener: OnWheelPickerListener?) {
        this.onWheelPickerListener = onWheelPickerListener
    }

    /**
     * Sets the wheel picker items
     * @param items ArrayList<String> The items list
     */
    fun setItems(items: ArrayList<String>) {
        properties.itemsList = items
        refreshWheelPicker()
    }

    /**
     * Sets the wheel picker items and scrolls to the desired position
     * @param itemsList ArrayList<String> The items list
     * @param scrollToPos Int The scroll to position
     */
    fun setItems(itemsList: ArrayList<String>, scrollToPos: Int) {
        properties.itemsList = itemsList
        refreshWheelPicker(scrollToPos)
    }

    /**
     * Sets the wheel picker selected item based on position
     * @param position Int The wheel picker to be selected item position
     */
    fun setSelectedItemPosition(position: Int) {
        refreshWheelPicker(position)
    }

    /**
     * Sets the wheel picker selected item based on item name
     * @param itemName String The wheel picker to be selected item name
     */
    fun setSelectedItem(itemName: String) {
        refreshWheelPicker(properties.itemsList.indexOf(itemName))
    }

    /**
     * Gets the current selected wheel picker item
     * @return String? The current selected wheel picker item
     */
    fun getCurrentSelectedItem(): String? {
        if (properties.selectedItemPos >= 0 && properties.selectedItemPos < properties.itemsList.size) {
            return properties.itemsList[properties.selectedItemPos]
        }

        return null
    }

    /**
     * Gets the current selected wheel picker item position
     * @return Int The current selected wheel picker item position
     */
    fun getCurrentSelectedItemPosition(): Int {
        return properties.selectedItemPos
    }
}