package com.behnamuix.karsaz.MVP.view

import android.R
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.behnamuix.karsaz.databinding.ActivityMainBinding
import com.bumptech.glide.Glide
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.InDateStyle
import com.kizitonwose.calendarview.model.OutDateStyle
import com.kizitonwose.calendarview.model.ScrollMode
import org.threeten.bp.MonthDay
import org.threeten.bp.YearMonth
import org.threeten.bp.temporal.WeekFields
import java.util.Locale


class ViewMainActivity(
    context: Context
) : FrameLayout(context) {
    val binding = ActivityMainBinding.inflate(LayoutInflater.from(context))
    fun loadProfile() {
        val URl = "https://behnamuix2024.com/img/profile.png"
        Glide
            .with(this)
            .load(URl)
            .into(binding.imgProfile)
        Toast.makeText(context, "Profile Loaded", Toast.LENGTH_SHORT).show()


    }
    fun loadCalanderRow(){
        val cal=binding.calendarView
        val currentMonth=YearMonth.now()
        val firstMonth=currentMonth.minusMonths(10)
        val lastMonth=currentMonth.plusMonths(10)
        val firstDayOfWeek=WeekFields.of(Locale.getDefault()).firstDayOfWeek
        cal.setup(firstMonth,lastMonth,firstDayOfWeek)
        cal.scrollToMonth(currentMonth)
        cal.inDateStyle=InDateStyle.ALL_MONTHS
        cal.outDateStyle=OutDateStyle.END_OF_ROW
        cal.scrollMode=ScrollMode.PAGED
        cal.orientation=RecyclerView.HORIZONTAL
        cal.maxRowCount=1
        cal.hasBoundaries=false

        }




    }
