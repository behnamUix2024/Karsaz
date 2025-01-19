package com.behnamuix.karsaz.MVP.view

import android.R
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.Toast
import com.behnamuix.karsaz.databinding.ActivityMainBinding
import com.bumptech.glide.Glide
import ir.erfandm.persiandatepicker.datepicker.MaterialDatePicker


class ViewMainActivity(
    context: Context
) : FrameLayout(context) {
    val binding = ActivityMainBinding.inflate(LayoutInflater.from(context))
    fun loadProfile() {
        val URl = "http://behnamuix2024.com/img/profile.png"
        Glide
            .with(this)
            .load(URl)
            .into(binding.imgProfile)
        Toast.makeText(context, "Profile Loaded", Toast.LENGTH_SHORT).show()


    }
    fun loadDatePicker(){

    }
}