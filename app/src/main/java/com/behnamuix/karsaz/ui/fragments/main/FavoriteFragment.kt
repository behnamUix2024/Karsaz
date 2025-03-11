package com.behnamuix.karsaz.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.behnamuix.karsaz.R
import com.behnamuix.karsaz.databinding.FragmentFavoriteBinding
import com.behnamuix.karsaz.databinding.FragmentHomeBinding
import com.behnamuix.karsaz.databinding.FragmentSearchBinding


class FavoriteFragment : Fragment() {
    //_binding is a nullable reference to the binding class
    private var _binding: FragmentFavoriteBinding? = null // اصلاح نوع و مقداردهی اولیه
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater,container,false)
        return binding.root
    }


}