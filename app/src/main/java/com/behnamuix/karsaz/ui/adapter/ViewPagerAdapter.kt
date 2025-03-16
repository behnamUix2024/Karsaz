package com.behnamuix.karsaz.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPagerAdapter(fragementActivity:FragmentActivity,private val fragments:List<Fragment>):
FragmentStateAdapter(fragementActivity){
    override fun getItemCount(): Int = fragments.size


    override fun createFragment(position: Int): Fragment=fragments[position]

}