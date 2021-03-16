package com.github.vikramezhil.wheelpickerexample.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.github.vikramezhil.wheelpickerexample.R
import com.github.vikramezhil.wheelpickerexample.databinding.ActivityMainBinding
import com.github.vikramezhil.wheelpickerexample.viewmodel.MainViewModel

/**
 * Wheel Picker Example Activity
 * @author vikram.ezhil
 */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.viewModel = viewModel
    }
}