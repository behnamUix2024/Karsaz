package com.behnamuix.karsaz.ui.fragments.main

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import com.behnamuix.karsaz.R
import com.behnamuix.karsaz.databinding.FragmentHomeBinding
import com.behnamuix.karsaz.databinding.SliderFragmentBinding

class SliderFragment : Fragment() {
    private var _binding: SliderFragmentBinding? = null
    private val binding get() = _binding!!


    private lateinit var img_slider: ImageView
    var initialX = 0f
    var dX = 0f
    val maxTranslationX = 540f
    val minTranslationX = 0f
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        _binding = SliderFragmentBinding.inflate(inflater, container, false)
        taskSlider()
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun taskSlider() {
        img_slider=binding.imgSliderHandle
        img_slider.setOnTouchListener{view,event->
            when(event.action){
                MotionEvent.ACTION_DOWN->{
                    dX=view.x-event.rawX
                    initialX=view.x
                    true
                }
                MotionEvent.ACTION_MOVE->{
                    var newTranslationX=event.rawX+dX
                    if(newTranslationX>initialX+maxTranslationX){
                        newTranslationX=initialX+maxTranslationX
                    }else if(newTranslationX<initialX+minTranslationX){
                        newTranslationX=initialX+minTranslationX
                    }
                    view.animate()
                        .x(newTranslationX).setDuration(0).start()
                    true
                }
                MotionEvent.ACTION_UP->{
                    view.animate().rotationBy(180f).setDuration(500).start()
                    binding.tvSliderGide.text="وظیفه تکمیل شد"
                    binding.imgSliderArrow.visibility=View.INVISIBLE
                    true
                }

                else -> false
            }
        }


    }

}