package com.github.vikramezhil.wheelpickerexample.viewmodel

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.github.vikramezhil.wheelpicker.props.OnWheelPickerListener

/**
 * Wheel Picker Example View Model
 * @author vikram.ezhil
 */

class MainViewModel(application: Application) : AndroidViewModel(application) {

    // Vertical Wheel Picker
    val weightInKgs: ObservableField<ArrayList<String>> = ObservableField()
    val initialWeightKgPosition: ObservableField<Int> = ObservableField()
    val weightInPounds: ObservableField<ArrayList<String>> = ObservableField()
    val initialWeightPoundsPosition: ObservableField<Int> = ObservableField()
    val units: ObservableField<ArrayList<String>> = ObservableField()
    val selectedUnit: ObservableField<String> = ObservableField()
    val selectedWeight: ObservableField<String> = ObservableField()

    // Horizontal Wheel Picker
    val initialItemPosition: ObservableField<Int> = ObservableField()
    val selectedItem: ObservableField<String> = ObservableField()

    init {
        val weightInKgsList = (40..200).getValuesRangeInStringList().map { "$it kgs" }.toCollection(ArrayList())
        weightInKgs.set(weightInKgsList)
        initialWeightKgPosition.set(weightInKgsList.size/2)

        val weightInPoundsList = (90..440).getValuesRangeInStringList().map { "$it lbs" }.toCollection(ArrayList())
        weightInPounds.set(weightInPoundsList)
        initialWeightPoundsPosition.set(weightInPoundsList.size/2)

        units.set(arrayListOf("Kilograms", "Pounds"))
        selectedUnit.set("Kilograms")

        initialItemPosition.set(4)
    }

    /**
     * Weight value selection listener
     * @return OnWheelPickerListener The wheel picker listener object
     */
    fun onWeightValueSelectionListener(): OnWheelPickerListener {
        return object: OnWheelPickerListener {
            override fun onItemSelected(position: Int, value: String) {
                selectedWeight.set("Selected weight: $value")
            }

            override fun onRefreshed(list: ArrayList<String>, position: Int, value: String) {
                selectedWeight.set("Selected weight: $value")
            }

            override fun onScrolling() {
                Log.d(MainViewModel::class.java.name, "Weight values wheel picker is scrolling")
            }
        }
    }

    /**
     * Unit value selection listener
     * @return OnWheelPickerListener The wheel picker listener object
     */
    fun onWeightUnitSelectionListener(): OnWheelPickerListener {
        return object: OnWheelPickerListener {
            override fun onItemSelected(position: Int, value: String) {
                selectedUnit.set(value)
            }

            override fun onRefreshed(list: ArrayList<String>, position: Int, value: String) {}

            override fun onScrolling() {
                Log.d(MainViewModel::class.java.name, "Weight units wheel picker is scrolling")
            }
        }
    }

    /**
     * Item selection listener
     * @return OnWheelPickerListener The wheel picker listener object
     */
    fun onItemSelectionListener(): OnWheelPickerListener {
        return object: OnWheelPickerListener {
            override fun onItemSelected(position: Int, value: String) {
                selectedItem.set("Selected item: $value")
            }

            override fun onRefreshed(list: ArrayList<String>, position: Int, value: String) {
                selectedItem.set("Selected item: $value")
            }

            override fun onScrolling() {
                Log.d(MainViewModel::class.java.name, "Item wheel picker is scrolling")
            }
        }
    }

    /**
     * Gets the values range in String list
     * @receiver IntRange The range
     * @return ArrayList<String> The values range in String list
     */
    private fun IntRange.getValuesRangeInStringList(): ArrayList<String> {
        return this.toList().map { it.toString() }.toCollection(ArrayList())
    }
}