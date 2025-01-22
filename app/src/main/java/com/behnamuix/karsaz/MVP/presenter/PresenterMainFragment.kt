package com.behnamuix.karsaz.MVP.presenter

import com.behnamuix.karsaz.MVP.ext.BaseLifeCycle
import com.behnamuix.karsaz.MVP.model.ModelMainFragment
import com.behnamuix.karsaz.MVP.view.ViewMainFragment

class PresenterMainFragment(
    private val view: ViewMainFragment,
    private val model: ModelMainFragment
) : BaseLifeCycle {
    override fun onCreate() {
        showProfile()
        showCalander()
    }

    fun showProfile() {
        view.loadProfile()
    }
    fun showCalander(){
        view.loadCalendarWeek()
    }
}