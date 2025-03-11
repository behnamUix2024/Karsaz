package com.behnamuix.karsaz.ui.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.behnamuix.karsaz.Dialog.NoInternetDialogFragment
import com.behnamuix.karsaz.MVP.model.ModelMainActivity
import com.behnamuix.karsaz.MVP.presenter.PresenterMainActivity
import com.behnamuix.karsaz.MVP.view.ViewMainActivity
import com.behnamuix.karsaz.R
import com.behnamuix.karsaz.Utils.Network.NetworkCheck
import java.io.IOException


class MainActivity : AppCompatActivity() {
    private lateinit var presenter: PresenterMainActivity
    private var mediaPlayer: MediaPlayer? = null
    private var isDialogShowing = false
    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (context != null && !NetworkCheck.isInternetAvailable(context)) {
                showNoInternetDialog()
            } else {
                dismissNoInternetDialog()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = ViewMainActivity(this)

        setContentView(view.binding.root)
        registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        presenter = PresenterMainActivity(view, ModelMainActivity(this))
        presenter.onCreate()


    }
    private fun showNoInternetDialog() {
        if (!isDialogShowing) {
            val dialog = NoInternetDialogFragment().apply {
                setRetryListener {
                    // اقدامات لازم برای تلاش مجدد (مثلاً رفرش صفحه)
                    Toast.makeText(this@MainActivity, "تلاش مجدد...", Toast.LENGTH_SHORT).show()
                }
            }
            dialog.show(supportFragmentManager, "NoInternetDialog")
            Log.i("testing","Show OffScreen!")
            playAudio(R.raw.disconnected)
            isDialogShowing = true
        }
    }
    private fun dismissNoInternetDialog() {
        supportFragmentManager.findFragmentByTag("NoInternetDialog")?.let {
            (it as NoInternetDialogFragment).dismiss()
            isDialogShowing = false
        }
    }

    private fun playAudio(addr:Int) {
        mediaPlayer = MediaPlayer.create(this, addr)

        mediaPlayer?.setOnCompletionListener {
            mediaPlayer?.release()
            mediaPlayer = null
        }
        mediaPlayer?.start()
        // below line is use to display a toast message.
        Log.i("testing","Disconnected audio started playing..")
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer?.release()
        mediaPlayer?.stop()
    }
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkReceiver) // حذف ثبت Receiver
        mediaPlayer?.release()
        mediaPlayer = null
    }

}