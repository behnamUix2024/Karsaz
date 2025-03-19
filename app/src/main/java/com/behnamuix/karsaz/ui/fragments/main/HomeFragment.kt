package com.behnamuix.karsaz.ui.fragments.main

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.behnamuix.karsaz.ui.adapter.ViewPagerAdapter
import com.behnamuix.karsaz.R
import com.behnamuix.karsaz.databinding.FragmentHomeBinding
import com.behnamuix.karsaz.ui.fragments.tasks.AllTasksFragment
import com.behnamuix.karsaz.ui.fragments.tasks.DoneTasksFragment
import com.behnamuix.karsaz.ui.fragments.tasks.NdoneTasksFragment
import com.dd.CircularProgressButton
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

class HomeFragment : Fragment() {
    //_binding is a nullable reference to the binding class
    private val urlDate = "https://behnamuix2024.com/time/getDate.php"
    private var _binding: FragmentHomeBinding? = null // اصلاح نوع و مقداردهی اولیه
    private val binding get() = _binding!!

    //initVariable
    private lateinit var btn_add: ImageView
    private lateinit var customSwitch: SwitchCompat
    private lateinit var img_light: ImageView
    private lateinit var tv_home_date: TextView
    private lateinit var tv_save: TextView
    private lateinit var tv_time: TextView
    private lateinit var img_dark: ImageView
    private lateinit var tablayout: TabLayout
    private lateinit var viewpager: ViewPager2
    private lateinit var queue: RequestQueue
    private lateinit var bsdAddHomeFrag: BottomSheetDialog
    private lateinit var bsdAddHomeFragView: View
    private lateinit var btn_save: ConstraintLayout
    private lateinit var pb_save: ProgressBar
    private lateinit var img_set_time: ImageView
    private lateinit var et_add_desc:EditText
    private lateinit var tv_count:TextView


    val duration: Long = 500


    private var tag = "testing"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        main()
    }

    private fun main() {
        config()
        getDate()
    }

    private fun config() {

        bsdAddHomeFragView = layoutInflater.inflate(R.layout.dialog_bottom_sheet_home_add, null)
        bsdAddHomeFrag = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        queue = Volley.newRequestQueue(requireContext())
        tv_home_date = binding.tvHomeDate
        customSwitch = binding.customSwitch
        img_light = binding.imgLight
        img_dark = binding.imgDark

        customSwitch.setOnCheckedChangeListener { _, isChecked ->
            animateSwitch(isChecked)


        }
        btn_add = binding.btnAdd
        btn_add.setOnClickListener() {
            showAddButtomSheetDialog(duration)
        }

    }

    private fun showAddButtomSheetDialog(t: Long) {
        log("showAddButtomSheetDialog()")
        Handler(Looper.getMainLooper()).postDelayed({
            bsdAddHomeFrag.setContentView(bsdAddHomeFragView)
            img_set_time=bsdAddHomeFragView.findViewById(R.id.img_set_tme)
            pb_save = bsdAddHomeFragView.findViewById(R.id.pb_save)
            tv_save = bsdAddHomeFragView.findViewById(R.id.tv_save)
            tv_time=bsdAddHomeFragView.findViewById(R.id.tv_time)
            et_add_desc=bsdAddHomeFragView.findViewById(R.id.et_add_desc)
            tv_count=bsdAddHomeFragView.findViewById(R.id.tv_count)
            btn_save = bsdAddHomeFragView.findViewById<ConstraintLayout>(R.id.btn_save)
            btn_save.setOnClickListener() {
                tv_save.text = "تایید"

                pb_save.visibility = View.VISIBLE
                Handler().postDelayed({

                    pb_save.visibility = View.GONE
                    tv_save.text = "عملیات ذخیره سازی در پایگاه داده انجام شد"
                    //Database
                    //bsdAddHomeFrag.dismiss()
                }, 5000)

            }
            img_set_time.setOnClickListener(){
                loadTimePickerDialog()
            }
            et_add_desc.addTextChangedListener(object:TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val wordCount=countWord(p0.toString())
                    tv_count.text="تعداد حروف:$wordCount"
                }

                override fun afterTextChanged(p0: Editable?) {}

            })



            bsdAddHomeFrag.show()

        }, t)
    }

    private fun countWord(text: String): Int {
        if(text.isEmpty()){
            return 0
        }

        return text.length
    }

    private fun loadTimePickerDialog() {
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H) // یا TimeFormat.CLOCK_12H
            .setTheme(R.style.CustomTimePickerStyle)
            .setHour(12) // ساعت اولیه
            .setMinute(0) // دقیقه اولیه
            .setTitleText("زمان شروع را وارد کنید")
            .setPositiveButtonText("تایید")
            .setNegativeButtonText("چشم پوشی")
            .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
            .build()

        picker.addOnPositiveButtonClickListener {
            val hour = picker.hour
            val minute = picker.minute
            tv_time.text = String.format("%02d:%02d", hour, minute)
        }

        picker.show(childFragmentManager, "timePicker")
    }


    //Animation
    private fun animateSwitch(isChecked: Boolean) {
        log("animateSwitch(isChecked: Boolean) ")

        //thumbColorFrom and thumbColorTo are the colors of the thumb when the switch is on or off
        val thumbColorFrom = if (isChecked) ContextCompat.getColor(
            requireContext(),
            R.color.color_acc2
        ) else ContextCompat.getColor(requireContext(), R.color.color_sec2)
        val thumbColorTo = if (isChecked) ContextCompat.getColor(
            requireContext(),
            R.color.color_sec2
        ) else ContextCompat.getColor(requireContext(), R.color.color_acc2)


        //ArgbEvaluator is used to animate between two colors
        val thumbAnimator = ValueAnimator.ofObject(ArgbEvaluator(), thumbColorFrom, thumbColorTo)
        thumbAnimator.duration = 500
        //addUpdateListener is used to update the color of the thumb as the animation progresses
        thumbAnimator.addUpdateListener { animator ->
            //thumbTintList is used to set the color of the thumb
            customSwitch.thumbTintList =
                android.content.res.ColorStateList.valueOf(animator.animatedValue as Int)

        }
        //.start() is used to start the animation
        thumbAnimator.start()


    }


    private fun DarkTheme() {
        log("DarkTheme() ")
    }

    private fun LightTheme() {
        log("LightTheme()")
    }

    private fun getDate() {
        log("getDate()")
        val stringRequest = object : StringRequest(
            Method.POST, urlDate,
            Response.Listener<String> { response ->
                // Handle the response
                Log.d("test", response.toString())
                tv_home_date.text = response.toString()


            },
            Response.ErrorListener { error ->
                // Handle the error
                Log.e("test", error.toString())
                val err = "داده ای وجود ندارد!"
                tv_home_date.text = err


            }) {

        }
        queue.add(stringRequest)


    }


    private fun log(s: String) {
        Log.i(tag, s)
    }

    private fun ToptablayoutConfig() {
        tablayout = binding.tabLayout
        viewpager = binding.viewPager
        val fragments: List<Fragment> =
            listOf(AllTasksFragment(), DoneTasksFragment(), NdoneTasksFragment())
        val adapter = ViewPagerAdapter(requireActivity(), fragments)
        viewpager.adapter = adapter
        TabLayoutMediator(tablayout, viewpager) { tab, position ->
            when (position) {
                0 -> tab.text = "همه کار ها"
                1 -> tab.text = "کار های انجام شده"
                2 -> tab.text = "کار های انجام نشده"
            }

        }.attach()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        ToptablayoutConfig()
        return binding.root
    }


}

