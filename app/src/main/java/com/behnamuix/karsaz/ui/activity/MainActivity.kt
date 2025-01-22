package com.behnamuix.karsaz.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.behnamuix.karsaz.MVP.model.ModelMainFragment
import com.behnamuix.karsaz.MVP.presenter.PresenterMainFragment
import com.behnamuix.karsaz.MVP.view.ViewMainFragment
import com.behnamuix.karsaz.R

class MainActivity : AppCompatActivity() {
    private lateinit var presenter: PresenterMainFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view= ViewMainFragment(this)
        presenter=PresenterMainFragment(view, ModelMainFragment(this))
        presenter.onCreate()
        setContentView(R.layout.activity_main)


    }
}