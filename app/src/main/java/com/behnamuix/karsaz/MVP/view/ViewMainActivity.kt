package com.behnamuix.karsaz.MVP.view

import android.R
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.behnamuix.karsaz.databinding.ActivityMainBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target


class ViewMainActivity(
    context:Context
) :FrameLayout(context) {
    val binding=ActivityMainBinding.inflate(LayoutInflater.from(context))
    fun loadProfile(){
        val url = "https://behnamuix2024.com/img/profile.png"

        Glide
            .with(context)
            .load(url)
            .centerCrop()
            .into(binding.imgProfile)

    }
}