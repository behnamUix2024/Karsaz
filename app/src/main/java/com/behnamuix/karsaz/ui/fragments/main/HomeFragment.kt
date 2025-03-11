package com.behnamuix.karsaz.ui.fragments.main

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import com.behnamuix.karsaz.R
import com.behnamuix.karsaz.databinding.FragmentHomeBinding
import com.behnamuix.karsaz.databinding.FragmentLastBinding

class HomeFragment : Fragment() {
    //_binding is a nullable reference to the binding class
    private var _binding: FragmentHomeBinding? = null // اصلاح نوع و مقداردهی اولیه
    private val binding get() = _binding!!

    //initVariable
    private lateinit var customSwitch: SwitchCompat
    private lateinit var img_light: ImageView
    private lateinit var img_dark: ImageView


    private var tag = "testing"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        main()
    }

    private fun main() {
        config()
    }

    private fun config() {
        customSwitch = binding.customSwitch
        img_light = binding.imgLight
        img_dark = binding.imgDark
        customSwitch.setOnCheckedChangeListener { _, isChecked ->
            animateSwitch(isChecked)


        }
    }


    //Animation
    private fun animateSwitch(isChecked: Boolean) {
        log("animateSwitch(isChecked: Boolean) ")
        switchThumbAnimation(isChecked)
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

    private fun switchThumbAnimation(isChecked: Boolean) {
        if (isChecked) {
            img_light.animate().translationY(-100f).rotationBy(90f).alphaBy(0f).start()
            img_dark.animate().translationY(0f).start()
            LightTheme()

        } else {
            img_light.animate().translationY(0f).rotationBy(0f).alphaBy(1f).start()
            img_dark.animate().translationY(-100f).start()
            DarkTheme()

        }
    }

    private fun DarkTheme() {
        log("DarkTheme() ")
    }

    private fun LightTheme() {
        log("LightTheme()")
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
        return binding.root
    }


}

