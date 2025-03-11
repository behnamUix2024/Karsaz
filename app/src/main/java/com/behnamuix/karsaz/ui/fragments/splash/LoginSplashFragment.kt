package com.behnamuix.karsaz.ui.fragments.splash

import com.behnamuix.karsaz.Timer.MyTimer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.behnamuix.karsaz.R
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.otpview.OTPTextView


class LoginSplashFragment : Fragment() {
    //InitProValue
    private lateinit var bottomSheetDialog: BottomSheetDialog

    private lateinit var queue: RequestQueue

    private val URL_IMG = "https://behnamuix2024.com/img/karsaz/login/img_login.png"
    private var otp = ""
    private lateinit var timer: MyTimer // شیء MyTimer


    //InitSimpleValue
    private var numberChck = false
    private val code = 0
    private var resp = ""
    private val strView2 = "لطفا کد 4 رقمی که به شماره تلفن شما ارسال شده است وارد کنید"
    private var phone = ""
    private var LogTag = "Testing"
    val duration = 120000


    //Button
    private lateinit var btn_signup: Button
    private lateinit var btn_phone: MaterialButton
    private lateinit var btn_ok: MaterialButton

    //EditText
    private lateinit var et_phone: TextInputEditText

    //TextView
    private lateinit var tv_phone: TextView
    private lateinit var tv_timer: TextView
    private lateinit var tv_resend: TextView
    private lateinit var tv_number: TextView

    //ImageView
    private lateinit var img_login: ImageView

    //TextInputLayout
    private lateinit var input_phone: TextInputLayout

    //LinearLayout
    private lateinit var line_note: LinearLayout
    private lateinit var line_phone: LinearLayout

    //OTP
    lateinit var otp_view: OTPTextView


    //Main
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        main(view)

    }

    private fun main(v: View) {
        log("main()")
        config(v)
        loadLoginImg()
        textWatcher()
        onclick()
    }

    //LoadSplashLoginImage
    private fun loadLoginImg() {
        log("loadLoginImg()")
        Glide.with(this).load(URL_IMG).centerCrop().placeholder(R.drawable.round_downloading_24)
            .into(img_login);

    }

    //ComponentConfig
    private fun config(v: View) {
        //FirstInit
        val bottomSheetView = layoutInflater.inflate(R.layout.dialog_bottom_sheet, null)
        bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        // layout باتم شیت خود را اینجا قرار دهید
        otp_view = bottomSheetView.findViewById<OTPTextView>(R.id.otp_view)
        input_phone = bottomSheetView.findViewById<TextInputLayout>(R.id.input_phone)
        line_note = bottomSheetView.findViewById<LinearLayout>(R.id.line_note)
        tv_timer = bottomSheetView.findViewById<TextView>(R.id.tv_timer)
        tv_resend = bottomSheetView.findViewById<TextView>(R.id.tv_resend)
        tv_phone = bottomSheetView.findViewById<TextView>(R.id.tv_phone)
        btn_phone = bottomSheetView.findViewById<MaterialButton>(R.id.btn_phone)
        et_phone = bottomSheetView.findViewById<TextInputEditText>(R.id.et_phone)
        btn_ok = bottomSheetView.findViewById<MaterialButton>(R.id.btn_ok)
        tv_number = bottomSheetView.findViewById<TextView>(R.id.tv_number)
        line_phone = bottomSheetView.findViewById<LinearLayout>(R.id.line_phone)
        btn_ok = bottomSheetView.findViewById<MaterialButton>(R.id.btn_ok)
        bottomSheetDialog.setContentView(bottomSheetView)
        queue = Volley.newRequestQueue(requireContext())
        btn_signup = v.findViewById<Button>(R.id.btn_signup)
        img_login = v.findViewById<ImageView>(R.id.img_login)
        //ConfigView1
        line_note.visibility = View.GONE
        tv_resend.visibility = View.GONE
        otp_view.visibility = View.GONE
        btn_ok.visibility = View.GONE
        log("config()")
    }

    //TextWatcher
    private fun textWatcher() {
        log("textWatcher()")
        et_phone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkPhoneNumber(p0)

            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

    }

    //ClickListener
    private fun onclick() {
        log("onclick()")
        btn_signup.setOnClickListener() {
            log("btn_signup")
            loadBottomsheet(10)
        }
        btn_phone.setOnClickListener() {
            log("btn_phone")
            if (numberChck) {
                phone = et_phone.text.toString()
                sendVerificationCode(phone)
                //Go to the View2
                changeText(tv_phone, strView2)
                view2()
                startTimer()
            } else {

            }
        }
        btn_ok.setOnClickListener() {
            checkOTP()
        }
        tv_resend.setOnClickListener() {
           resend()
        }
        tv_number.setOnClickListener() {
           editNumber()
        }
    }

    private fun resend() {
        sendVerificationCode(phone)
        Toast.makeText(requireContext(),"کد 4 رقمی مجدد برای شماره $phone ارسال شد",Toast.LENGTH_SHORT).show()
    }

    private fun editNumber() {
        log("editNumber()")
        timer.stop()
        view1()
        et_phone.setText(phone)
    }

    private fun startTimer(){
        log("startTimer()")

        // ساخت تایمر و اتصال به TextView
        timer = MyTimer(textView = tv_timer, onFinish = {
            tv_resend.visibility = View.VISIBLE
            line_note.visibility=View.GONE
        })

        timer.start(duration.toLong())

    }

    private fun sendVerificationCode(phone: String): String {
        log("sendVerificationCode(phone: String)")
        //code = Random.nextInt(1000, 10000)
        //"https://www.behnamuix2024.com/api/sendsms.php?to=$phone",
        val stringRequest = object : StringRequest(Method.POST,
            "https://www.behnamuix2024.com/api/fake.php",
            Response.Listener<String> { response ->
                // Handle the response
                Log.d("test", response.toString())


                resp = response.toString()

            },
            Response.ErrorListener { error ->
                // Handle the error
                Log.e("test", error.toString())
                resp = error.toString()


            }) {

        }
        queue.add(stringRequest)
        return resp


    }

    fun extractVerificationCode(msg: String): String? {
        log("extractVerificationCode(text: String)")
        val regex = Regex("""\b\d{4}\b""") // کد ۴ رقمی مستقل
        return regex.find(msg)?.value
    }

    private fun checkOTP() {
        otp = extractVerificationCode(resp).toString()
        Toast.makeText(requireContext(), otp, Toast.LENGTH_LONG).show()
        if (otp_view.otp == otp) {
            otp_view.showSuccess()
            bottomSheetDialog.dismiss()
            log("checkOTP()")
            //Go to the EndFragment
            loadEndFragment()
        } else {
            if (!tv_resend.isVisible) {
                tv_resend.visibility = View.VISIBLE
            }
            otp_view.showError()
            Toast.makeText(requireContext(),"کد اشتباه است!",Toast.LENGTH_SHORT).show()


        }

    }

    //CheckPhoneNo
    private fun checkPhoneNumber(p0: CharSequence?): Boolean {
        if (p0 != null) {
            if (!p0.startsWith("09") || p0.length < 11) {
                input_phone.error = "شماره تلفن شما اشتباه است!"
                btn_phone.isEnabled = false

            } else {
                input_phone.error = null
                btn_phone.isEnabled = true
                numberChck = true
                log("checkPhoneNumber()")


            }
        }
        return numberChck
    }

    //BottomSheet
    private fun loadBottomsheet(t: Long) {


        Handler(Looper.getMainLooper()).postDelayed({
            bottomSheetDialog.show()
        }, t)


    }


    private fun view2() {
        et_phone.visibility = View.GONE
        input_phone.visibility = View.GONE
        btn_phone.visibility = View.GONE
        btn_ok.visibility = View.VISIBLE
        line_note.visibility = View.VISIBLE
        otp_view.visibility = View.VISIBLE
        line_phone.visibility = View.VISIBLE
        tv_number.text = phone

        log("view2()")
    }

    private fun view1() {

        et_phone.visibility = View.VISIBLE
        input_phone.visibility = View.VISIBLE
        btn_phone.visibility = View.VISIBLE
        btn_ok.visibility = View.GONE
        line_note.visibility = View.GONE
        otp_view.visibility = View.GONE
        tv_resend.visibility = View.GONE
        line_phone.visibility = View.GONE

        log("view1()")
    }

    private fun changeText(tvPhone: TextView, strView2: String) {
        log("changeText(tvPhone: TextView, strView2: String)")
        tvPhone.text = strView2

    }

    private fun log(s: String) {
        Log.i(LogTag, s)
    }

    //Navigate
    private fun loadEndFragment() {
        log("loadEndFragment()")
        val navController = findNavController()
        navController.navigate(R.id.action_loginFragment_to_endFragment) // action_home_to_profile شناسه Action است.

    }


    override fun onStop() {
        super.onStop()
        timer.stop()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)


        return view
    }

}









