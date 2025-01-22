package com.behnamuix.karsaz.MVP.view

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.Toast
import com.behnamuix.karsaz.databinding.ActivityMainBinding
import com.behnamuix.karsaz.ui.activity.MainActivity
import com.bumptech.glide.Glide


class ViewMainFragment(
    context: Context
) : FrameLayout(context) {
    val binding = ActivityMainBinding.inflate(LayoutInflater.from(context))
    fun loadProfile() {

        val URl = "https://behnamuix2024.com/img/profile.png"
        Glide
            .with(this)
            .load(URl)
            .into(binding.imgProfile)
        Toast.makeText(context,"behnam",Toast.LENGTH_LONG).show()



    }
    fun loadCalendarWeek(){



        }




    }
