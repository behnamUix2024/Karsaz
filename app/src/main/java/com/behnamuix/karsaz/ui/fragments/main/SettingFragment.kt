package com.behnamuix.karsaz.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.behnamuix.karsaz.R
import com.behnamuix.karsaz.databinding.FragmentHomeBinding
import com.behnamuix.karsaz.databinding.FragmentSearchBinding
import com.behnamuix.karsaz.databinding.FragmentSettingBinding


class SettingFragment : Fragment() {
    //_binding is a nullable reference to the binding class
    private var _binding: FragmentSettingBinding? = null // اصلاح نوع و مقداردهی اولیه
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingBinding.inflate(inflater,container,false)
        return binding.root
    }

}