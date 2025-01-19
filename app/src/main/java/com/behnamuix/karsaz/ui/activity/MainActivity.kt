package com.behnamuix.karsaz.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.behnamuix.karsaz.MVP.model.ModelMainActivity
import com.behnamuix.karsaz.MVP.presenter.PresenterMainActivity
import com.behnamuix.karsaz.MVP.view.ViewMainActivity
import com.behnamuix.karsaz.R
import com.behnamuix.karsaz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var presenter: PresenterMainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view=ViewMainActivity(this)
        setContentView(R.layout.activity_main)

        presenter=PresenterMainActivity(view, ModelMainActivity(this))
        presenter.onCreate()

    }
}