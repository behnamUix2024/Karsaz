package com.behnamuix.karsaz.MVP.view


import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.Toast
import com.behnamuix.karsaz.R
import com.behnamuix.karsaz.databinding.ActivityMainBinding
import com.bumptech.glide.Glide
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.api.PersianPickerDate
import ir.hamsaa.persiandatepicker.api.PersianPickerListener
import ir.hamsaa.persiandatepicker.util.PersianCalendarUtils
import org.threeten.bp.LocalDate
import java.util.Calendar


class ViewMainActivity(
    context: Context
) : FrameLayout(context) {
    val binding = ActivityMainBinding.inflate(LayoutInflater.from(context))
    private val selectedDates = mutableSetOf<LocalDate>()
    fun loadProfile() {
        var calendar=Calendar.getInstance()
        binding.tvTodayDate.text=calendar.get(Calendar.YEAR).toString()
        val URl = "http://www.behnamuix2024.com/img/profile.png"
        Glide
            .with(this)
            .load(URl)
            .into(binding.imgProfile)

        Toast.makeText(context, "✅ test001", Toast.LENGTH_SHORT).show()


    }

    fun loadCalendar() {
        Toast.makeText(context, "✅ test002  ", Toast.LENGTH_SHORT).show()

        binding.imgDate.setOnClickListener() {
            val picker = PersianDatePickerDialog(context)
            picker.setPositiveButtonString("باشه")
                .setActionTextColor(resources.getColor(R.color.color_b))
                .setTodayTextSize(20)
                .setNegativeButton("بیخیال")
                .setTodayButton("امروز")

                .setTodayButtonVisible(true)
                .setMinYear(1300)
                .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                .setMaxMonth(PersianDatePickerDialog.THIS_MONTH)
                .setMaxDay(PersianDatePickerDialog.THIS_DAY)
                .setInitDate(1377, 1, 31)
                .setActionTextColor(resources.getColor(R.color.color_b))
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
                        var date = persianPickerDate.persianYear.toString() + "/" +
                                persianPickerDate.persianMonth.toString() + "/" +
                                persianPickerDate.persianDay.toString()
                        binding.etDate.setText(date)

                    }

                    override fun onDismissed() {
                        Toast.makeText(context, "تاریخ انتخاب شد", Toast.LENGTH_SHORT).show()
                    }
                })

            picker.show()

        }


    }


}
