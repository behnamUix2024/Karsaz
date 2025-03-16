package com.behnamuix.karsaz.MVP.view

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.Toast
import com.behnamuix.karsaz.databinding.ActivityMainBinding


import java.util.Calendar


class ViewMainActivity(
    context: Context
) : FrameLayout(context) {
    val binding = ActivityMainBinding.inflate(LayoutInflater.from(context))
    fun showAddDialog(){
        Toast.makeText(context,"MVP started!",Toast.LENGTH_SHORT).show()
    }


}
