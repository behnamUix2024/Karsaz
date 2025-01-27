package com.behnamuix.karsaz.MVP.presenter

import com.behnamuix.karsaz.MVP.ext.BaseLifeCycle
import com.behnamuix.karsaz.MVP.model.ModelMainActivity
import com.behnamuix.karsaz.MVP.view.ViewMainActivity

class PresenterMainActivity(
    private val view: ViewMainActivity,
    private val model: ModelMainActivity
) : BaseLifeCycle {
    override fun onCreate() {
        showProfile()
        showCalander()
    }

    fun showProfile() {
        view.loadProfile()
    }
    fun showCalander(){
        view.loadCalendar()
    }
}