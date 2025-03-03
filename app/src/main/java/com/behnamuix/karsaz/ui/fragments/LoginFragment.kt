package com.behnamuix.karsaz.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.behnamuix.karsaz.R
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


class LoginFragment : Fragment() {
    private val URL = "https://behnamuix2024.com/img/karsaz/login/img_login.png"
    private lateinit var auth: FirebaseAuth
    private lateinit var phoneNumberEditText: EditText
    private lateinit var sendCodeButton: Button
    private lateinit var verificationId: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadLoginImg()
        fireBaseConfig()
        val btn_suignup = view.findViewById<View>(R.id.btn_signup)
        btn_suignup.setOnClickListener() {

            loadBottomsheet(10)
        }
    }

    private fun loadBottomsheet(t: Long) {

        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        val bottomSheetView = layoutInflater.inflate(
            R.layout.bottom_sheet_dialog,
            null
        ) // layout باتم شیت خود را اینجا قرار دهید
        bottomSheetDialog.setContentView(bottomSheetView)
        Handler().postDelayed({
            bottomSheetDialog.show()
        }, t)
        val et_phone = bottomSheetView.findViewById<EditText>(R.id.et_phone)
        val btn_phone = bottomSheetView.findViewById<MaterialButton>(R.id.bnt_phone)
        val input = bottomSheetView.findViewById<TextInputLayout>(R.id.textInputLayout)
        input.errorIconDrawable = requireContext().getDrawable(R.drawable.round_error_outline_24)

        et_phone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 != null) {
                    if (p0.length < 11) {
                        btn_phone.isEnabled = false
                        if (!p0.startsWith("+98")) {
                            input.error = "شماره تلفن وارد شده اشتباه است!"
                            btn_phone.isEnabled = false


                        } else {
                            input.error = null


                        }
                    } else {
                        input.error = null
                        btn_phone.isEnabled = true
                        btn_phone.setOnClickListener(){
                            val phoneNumber=et_phone.text.toString()
                            sendVerificationCode(phoneNumber)
                        }


                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })


    }

    private fun sendVerificationCode(phoneNumber: String) {
        val options=PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(80L,TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(object :PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    signInWithPhoneAuthCredential(credential)
                }

                override fun onVerificationFailed(exception: FirebaseException) {
                    Toast.makeText(requireContext(), "Verification failed: ${exception.message}", Toast.LENGTH_SHORT).show()
                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    Toast.makeText(requireContext(), "Verification code sent", Toast.LENGTH_SHORT).show()
                    // در اینجا می‌توانید به صفحه وارد کردن کد تأیید بروید
                    //Task6:


                }
            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)



    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // ورود موفقیت‌آمیز بود
                    Toast.makeText(requireContext(), "Signed in successfully", Toast.LENGTH_SHORT).show()
                } else {
                    // ورود ناموفق بود
                    Toast.makeText(requireContext(), "Sign in failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun fireBaseConfig() {
        auth = FirebaseAuth.getInstance()


    }

    private fun loadLoginImg() {
        val imageView = view?.findViewById(R.id.img_login) as ImageView
        Glide
            .with(this)
            .load(URL)
            .centerCrop()
            .placeholder(R.drawable.round_downloading_24)
            .into(imageView);

    }


}