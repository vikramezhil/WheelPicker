package com.github.vikramezhil.wheelpicker.manager

import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.forEach
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.github.vikramezhil.wheelpicker.props.OnWheelPickerListener
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * Wheel Picker Slider Manager
 * @author vikram.ezhil
 */

class WPSliderManager(context: Context, orientationVal: Int, private val scaleDownEnabled: Boolean, private val listener: OnWheelPickerListener) : LinearLayoutManager(context) {

    private lateinit var view: RecyclerView

    init {
        orientation = orientationVal
    }

    override fun isAutoMeasureEnabled(): Boolean {
        return false
    }

    override fun onAttachedToWindow(view: RecyclerView?) {
        super.onAttachedToWindow(view)

        this.view = view!!

        // Smart snapping
        LinearSnapHelper().attachToRecyclerView(view)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State) {
        super.onLayoutChildren(recycler, state)
        scaleDownView(orientation)
    }

    override fun canScrollVertically(): Boolean {
        return orientation == VERTICAL
    }

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        val scrolled = super.scrollVerticallyBy(dy, recycler, state)
        scaleDownView(orientation)
        return scrolled
    }

    override fun canScrollHorizontally(): Boolean {
        return orientation == HORIZONTAL
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
        scaleDownView(orientation)
        return scrolled
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)

        // When scroll stops we notify on the selected item
        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            // Find the closest child to the recyclerView center --> this is the selected item.
            val recyclerViewCenter = if (orientation == VERTICAL) {
                ((view.top - view.bottom)/2) + view.bottom
            } else {
                ((view.right - view.left)/2) + view.left
            }

            var minDistance = if (orientation == VERTICAL) {
                view.height
            } else {
                view.width
            }

            var position = -1

            for (cat in 0 until view.childCount) {
                val child = view.getChildAt(cat)
                val childCenter = if (orientation == VERTICAL) {
                    getDecoratedBottom(child) + (getDecoratedTop(child) - getDecoratedBottom(child)) / 2
                } else {
                    getDecoratedLeft(child) + (getDecoratedRight(child) - getDecoratedLeft(child)) / 2
                }

                val childDistanceFromCenter = abs(childCenter - recyclerViewCenter)
                if (childDistanceFromCenter < minDistance) {
                    minDistance = childDistanceFromCenter
                    position = view.getChildLayoutPosition(child)
                }
            }

            // Sending an update on the selected item
            listener.onItemSelected(position, "")
        } else if (state == RecyclerView.SCROLL_STATE_DRAGGING) {
            // Sending an update that the view is scrolling
            listener.onScrolling()
        }
    }

    /**
     * Scales down the view
     */
    private fun scaleDownView(orientation: Int) {
        if (scaleDownEnabled) {
            val mid = if (orientation == VERTICAL) {
                height / 2.0f
            } else {
                width / 2.0f
            }

            for (cat in 0 until childCount) {
                // Calculating the distance of the child from the center
                val child = getChildAt(cat)
                if (child != null) {
                    val childMid = if (orientation == VERTICAL) {
                        (getDecoratedTop(child) + getDecoratedBottom(child)) / 2.0f
                    } else {
                        (getDecoratedLeft(child) + getDecoratedRight(child)) / 2.0f
                    }

                    val distanceFromCenter = abs(mid - childMid)
                    // The scaling formula
                    val scale = if (orientation == VERTICAL) {
                        1 - sqrt((distanceFromCenter/height).toDouble()).toFloat() * 0.66f
                    } else {
                        1 - sqrt((distanceFromCenter/width).toDouble()).toFloat() * 0.66f
                    }

                    if (child is LinearLayout) {
                        (child).forEach {
                            if (it is TextView) {
                                // Set scale to text view
                                it.scaleX = scale
                                it.scaleY = scale
                            }
                        }
                    } else {
                        child.scaleX = scale
                        child.scaleY = scale
                    }
                }
            }
        }
    }
}