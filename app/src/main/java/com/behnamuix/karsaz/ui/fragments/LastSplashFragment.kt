package com.behnamuix.karsaz.ui.fragments

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.drawToBitmap
import com.behnamuix.karsaz.R
import com.behnamuix.karsaz.databinding.FragmentEndBinding
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LastSplashFragment : Fragment() {

    private var _binding: FragmentEndBinding? = null
    private val binding get() = _binding!!

    private val url = "https://behnamuix2024.com/img/karsaz/end/end_splash.png"
    private lateinit var imgEndSplash: ImageView
    private var dialog: AlertDialog? = null
    private var btnNameOk: MaterialButton? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEndBinding.inflate(inflater, container, false)
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
        imgEndSplash=binding.imgEnd
    }


    private fun setupDialog() {
        if (!isAdded) return
        val builder = MaterialAlertDialogBuilder(requireContext()).apply {
            setView(R.layout.end_frag_dialog)
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
            .placeholder(R.drawable.round_downloading_24)
            .into(imgEndSplash)
    }





    override fun onDestroyView() {
        super.onDestroyView()
        dialog?.dismiss()
        dialog = null;
        _binding = null; // جلوگیری از Memory Leak
    }
}