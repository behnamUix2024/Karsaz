package com.behnamuix.karsaz.MVP.view

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.behnamuix.karsaz.databinding.ActivityMainBinding

class ViewMainActivity(
    context:Context
) :FrameLayout(context) {
    val binding=ActivityMainBinding.inflate(LayoutInflater.from(context))
}