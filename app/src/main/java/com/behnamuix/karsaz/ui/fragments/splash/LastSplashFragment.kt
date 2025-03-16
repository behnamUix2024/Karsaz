package com.behnamuix.karsaz.ui.fragments.splash

import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.behnamuix.karsaz.R
import com.behnamuix.karsaz.databinding.FragmentLastBinding
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class LastSplashFragment : Fragment() {

    private var _binding: FragmentLastBinding? = null // اصلاح نوع و مقداردهی اولیه
    private val binding get() = _binding!!

    private val url = "https://behnamuix2024.com/img/karsaz/end/end_splash.png"
    private lateinit var imgEndSplash: ImageView
    private var dialog: AlertDialog? = null
    private var btnNameOk: MaterialButton? = null
    private var btnEnd: MaterialButton? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        config()
        setupDialog()
        loadLoginImg()
        showDialog()//نمایش دیالوگ را اینجا قرار دادم.
    }

    private fun config() {
        if (_binding != null) { // بررسی null بودن _binding
            imgEndSplash = binding.imgEnd // اصلاح نام view
            btnEnd = binding.btnEnd
            btnEnd?.setOnClickListener {
                loadHomeFragment()
            }
        }
    }


    private fun setupDialog() {
        if (!isAdded) return
        val builder = MaterialAlertDialogBuilder(requireContext()).apply {
            setView(R.layout.dialog_last_frag_dialog)
            setCancelable(true)
        }
        dialog = builder.create()

        dialog?.setOnShowListener {
            btnNameOk = dialog?.findViewById<MaterialButton>(R.id.btn_ok_name)
            // بررسی null بودن دکمه
            if (btnNameOk != null) {
                btnNameOk?.setOnClickListener {
                    dialog?.dismiss()
                }
            } else {
                Log.e("TAG", "Button not found in dialog!")
            }
        }
    }

    private fun showDialog() {
        if (dialog?.isShowing == false && isAdded) {
            dialog?.show()
        }
    }

    private fun loadLoginImg() {
        if (!isAdded) return
        Glide.with(requireContext())
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.no_image_load)
            .into(imgEndSplash)
    }

    private fun loadHomeFragment() {
        Log.i("testing", "loadHomeFragment()")
        val navController = findNavController()
        navController.navigate(R.id.action_endFragment_to_homeFragment) // action_home_to_profile شناسه Action است.

    }



    override fun onDestroyView() {
        super.onDestroyView()
        dialog?.dismiss()
        dialog = null;
        _binding = null; // جلوگیری از Memory Leak
    }
}