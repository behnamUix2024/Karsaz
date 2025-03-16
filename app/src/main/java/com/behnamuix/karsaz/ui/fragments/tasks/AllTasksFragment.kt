package com.behnamuix.karsaz.ui.fragments.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.behnamuix.karsaz.R
import com.behnamuix.karsaz.databinding.FragmentAllTasksBinding
import com.behnamuix.karsaz.databinding.FragmentHomeBinding
import com.bumptech.glide.Glide


class AllTasksFragment : Fragment() {
    private val url="http://behnamuix2024.com/img/karsaz/home/Done.png"
    private lateinit var  img_Notask:ImageView
    //_binding is a nullable reference to the binding class
    private var _binding: FragmentAllTasksBinding? = null // اصلاح نوع و مقداردهی اولیه
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAllTasksBinding.inflate(inflater, container, false)
        loadNoTaskImg()
        return binding.root
    }
    private fun loadNoTaskImg() {
        if (!isAdded) return
        img_Notask=binding.imgAllTask
        Glide.with(requireContext())
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.no_image_load)
            .into(img_Notask)
    }


}