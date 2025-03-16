package com.behnamuix.karsaz.ui.fragments.main

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
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
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

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
    private lateinit var img_dark: ImageView
    private lateinit var tablayout: TabLayout
    private lateinit var viewpager: ViewPager2
    private lateinit var queue: RequestQueue


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

        queue = Volley.newRequestQueue(requireContext())
        tv_home_date = binding.tvHomeDate
        customSwitch = binding.customSwitch
        img_light = binding.imgLight
        img_dark = binding.imgDark
        customSwitch.setOnCheckedChangeListener { _, isChecked ->
            animateSwitch(isChecked)


        }
        btn_add=binding.btnAdd
        btn_add.setOnClickListener(){
            showAddButtomSheetDialog()
        }
    }

    private fun showAddButtomSheetDialog() {
        log("showAddButtomSheetDialog()")
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


}

