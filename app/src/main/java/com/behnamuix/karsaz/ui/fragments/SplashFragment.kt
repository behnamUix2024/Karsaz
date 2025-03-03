package com.behnamuix.karsaz.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController

import com.behnamuix.karsaz.R
import com.behnamuix.karsaz.databinding.FragmentSplashBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import render.animations.Bounce
import render.animations.Flip
import render.animations.Render

class SplashFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private val SPLASH_TIME_OUT = 1
    private var loopJob: Job? = null
    private var currentIndex = 0
    var textlist = listOf("یکپارچه تر", "دقیق تر", "راحت تر", "سریع تر")
    private lateinit var binding: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_splash, container, false)

        return view


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSplashBinding.bind(view)
        configShared()
        animatedAbout()

    }




    private fun configShared() {
        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val isFirstRun = sharedPreferences.getBoolean("isFirstRun", true)
        if (isFirstRun) {
            splashLoading()
        } else {
            loadLoginFrag()
        }

    }

    private fun splashLoading() {
        binding.splashLoading.playAnimation()
        binding.splashLoading.loop(true)

        Handler().postDelayed({
            loadLoginFrag()
        }, SPLASH_TIME_OUT.toLong())
    }

    private fun animatedAbout() {
        stopLoop() // توقف حلقه قبلی
        currentIndex = 0 // بازنشانی به ابتدای لیست
        loopJob = viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            while (currentIndex < textlist.size) {
                updateWord(textlist[currentIndex])
                delay(1000L) // تأخیر 1 ثانیه
                currentIndex++
            }
            // اگر به انتهای لیست رسیدیم، دوباره شروع کنیم
            if (currentIndex >= textlist.size) {
                animatedAbout()
            }
        }

    }


    private fun updateWord(word: String) {
        val render = Render(requireContext())
        binding.tvAnim.text = word
        render.setAnimation(Bounce().InDown(binding.tvAnim))
        render.setDuration(800)
        render.start()


    }

    private fun stopLoop() {
        loopJob?.cancel()
        currentIndex = 0
    }


    private fun loadLoginFrag() {
        val navController = findNavController()
        navController.navigate(R.id.action_splashFragment_to_loginFragment) // action_home_to_profile شناسه Action است.

    }



    override fun onDestroy() {
        super.onDestroy()
        stopLoop() // جلوگیری از Memory Leak
    }


}