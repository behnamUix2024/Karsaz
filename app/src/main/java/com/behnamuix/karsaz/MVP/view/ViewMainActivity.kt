package com.behnamuix.karsaz.MVP.view

import android.R.attr.typeface
import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.Toast
import com.behnamuix.karsaz.databinding.ActivityMainBinding
import com.bumptech.glide.Glide
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.api.PersianPickerDate
import ir.hamsaa.persiandatepicker.api.PersianPickerListener
import ir.hamsaa.persiandatepicker.util.PersianCalendarUtils
import org.threeten.bp.LocalDate


class ViewMainActivity(
    context: Context
) : FrameLayout(context) {
    val binding = ActivityMainBinding.inflate(LayoutInflater.from(context))
var picker=PersianDatePickerDialog(context)
    private val selectedDates = mutableSetOf<LocalDate>()
    fun loadProfile() {

        val URl = "http://www.behnamuix2024.com/img/profile.png"
        Glide
            .with(this)
            .load(URl)
            .into(binding.imgProfile)

        Toast.makeText(context,"\uD83D\uDCCCtest001",Toast.LENGTH_LONG).show()




    }
    fun loadCalendarWeek(){

        picker.setPositiveButtonString("باشه")
            .setNegativeButton("بیخیال")
            .setTodayButton("امروز")
            .setTodayButtonVisible(true)
            .setMinYear(1300)
            .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
            .setMaxMonth(PersianDatePickerDialog.THIS_MONTH)
            .setMaxDay(PersianDatePickerDialog.THIS_DAY)
            .setInitDate(1377, 1, 31)
            .setActionTextColor(Color.GRAY)
            .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
            .setShowInBottomSheet(true)
            .setListener(object : PersianPickerListener {
                override fun onDateSelected(persianPickerDate: PersianPickerDate) {
                    Log.d(TAG, "onDateSelected: " + persianPickerDate.timestamp) //675930448000
                    Log.d(
                        TAG,
                        "onDateSelected: " + persianPickerDate.gregorianDate
                    ) //Mon Jun 03 10:57:28 GMT+04:30 1991
                    Log.d(
                        TAG,
                        "onDateSelected: " + persianPickerDate.persianLongDate
                    ) // دوشنبه  13  خرداد  1370
                    Log.d(TAG, "onDateSelected: " + persianPickerDate.persianMonthName) //خرداد
                    Log.d(
                        TAG,
                        "onDateSelected: " + PersianCalendarUtils.isPersianLeapYear(
                            persianPickerDate.persianYear
                        )
                    ) //true
                    Toast.makeText(
                        context,
                        persianPickerDate.persianYear.toString() + "/" + persianPickerDate.persianMonth + "/" + persianPickerDate.persianDay,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onDismissed() {
                }
            })

        picker.show()


        }






}
