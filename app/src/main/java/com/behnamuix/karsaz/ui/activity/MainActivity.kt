package com.behnamuix.karsaz.ui.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.behnamuix.karsaz.utils.Dialog.NoInternetDialogFragment
import com.behnamuix.karsaz.MVP.model.ModelMainActivity
import com.behnamuix.karsaz.MVP.presenter.PresenterMainActivity
import com.behnamuix.karsaz.MVP.view.ViewMainActivity
import com.behnamuix.karsaz.R
import com.behnamuix.karsaz.utils.Network.NetworkCheck
import com.behnamuix.karsaz.ui.fragments.main.FavoriteFragment
import com.behnamuix.karsaz.ui.fragments.main.HomeFragment
import com.behnamuix.karsaz.ui.fragments.main.SearchFragment
import com.behnamuix.karsaz.ui.fragments.main.SettingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
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
        val view=ViewMainActivity(this)
        setContentView(view.binding.root)
        presenter=PresenterMainActivity(view, ModelMainActivity(this))
        presenter.onCreate()
        // دریافت NavController از NavHostFragment
        config()
        //مهم باید نو هاست رو بگیریم و نو کنترلر رو از نو هاست بگیریم


    }

    private fun config() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.VISIBLE
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    // نمایش HomeFragment
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, HomeFragment())
                        .commit()
                    true
                }

                R.id.searchFragment -> {
                    // نمایش SearchFragment
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, SearchFragment())
                        .commit()
                    true
                }

                R.id.favoriteFragment -> {
                    // نمایش FavoriteFragment
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, FavoriteFragment())
                        .commit()
                    true
                }

                R.id.settingFragment -> {
                    // نمایش SettingFragment
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, SettingFragment())
                        .commit()
                    true
                }

                else -> false
            }

        }
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
            Log.i("testing", "Show OffScreen!")
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

    private fun playAudio(addr: Int) {
        mediaPlayer = MediaPlayer.create(this, addr)

        mediaPlayer?.setOnCompletionListener {
            mediaPlayer?.release()
            mediaPlayer = null
        }
        mediaPlayer?.start()
        // below line is use to display a toast message.
        Log.i("testing", "Disconnected audio started playing..")
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