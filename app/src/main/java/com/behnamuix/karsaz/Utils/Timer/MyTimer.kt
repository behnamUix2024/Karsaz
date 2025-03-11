package com.behnamuix.karsaz.Timer

import android.os.CountDownTimer
import android.widget.TextView

//Timer
class MyTimer(private val textView: TextView, private val onFinish: () -> Unit) {
    private var countDownTimer: CountDownTimer? = null
    fun start(duration: Long) {
        countDownTimer = object : CountDownTimer(duration, 1000) {
            override fun onTick(p0: Long) {
                val min = (p0 / 1000) / 60
                val sec = (p0 / 1000) % 60
                textView.text = String.format("%02d:%02d", min, sec)
            }

            override fun onFinish() {
                onFinish.invoke()
            }

        }.start()

    }

    fun stop() {
        countDownTimer?.cancel()
    }
}