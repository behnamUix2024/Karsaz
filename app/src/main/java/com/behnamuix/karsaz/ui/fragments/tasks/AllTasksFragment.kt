package com.behnamuix.karsaz.ui.fragments.tasks

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.behnamuix.karsaz.R
import com.behnamuix.karsaz.Retrofit.ApiResponseJson
import com.behnamuix.karsaz.Retrofit.ApiService
import com.behnamuix.karsaz.databinding.FragmentAllTasksBinding
import com.behnamuix.karsaz.databinding.FragmentHomeBinding
import com.behnamuix.karsaz.db.model.Tasks
import com.behnamuix.karsaz.ui.adapter.TaskAllRecAdapter
import com.behnamuix.karsaz.ui.fragments.main.SliderFragment
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class AllTasksFragment : Fragment() {
    private var URL="https://behnamuix2024.com/api/"
    private val url = "http://behnamuix2024.com/img/karsaz/home/Done.png"
    private lateinit var img_Notask: ImageView
    private lateinit var tv_notask: TextView
    private lateinit var retrofit: Retrofit
    //_binding is a nullable reference to the binding class
    private var _binding: FragmentAllTasksBinding? = null // اصلاح نوع و مقداردهی اولیه
    private val binding get() = _binding!!


    private var recyclerView: RecyclerView? = null // اصلاح نوع و مقداردهی اولیه
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAllTasksBinding.inflate(inflater, container, false)
        //loadNoTaskImg()
        config()
        loadTaskRec()
        return binding.root
    }

    private fun config() {
        recyclerView = binding.recTaskList
        img_Notask = binding.imgAllTask
        tv_notask=binding.textView12
        retrofit=Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private fun loadTaskRec() {
        img_Notask.visibility=View.GONE
        tv_notask.visibility=View.GONE
        recyclerView?.visibility=View.VISIBLE
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false) // برای اسکرول افقی

        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.getTask()
        call.enqueue(object : Callback<ApiResponseJson> {
            override fun onResponse(
                call: Call<ApiResponseJson>,
                response: Response<ApiResponseJson>
            ) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse?.status == "success" && apiResponse.data != null) {
                        Log.d("TaskAllRecAdapter", "Data List: ${apiResponse.data}")
                        val adapter = TaskAllRecAdapter(apiResponse.data)
                        recyclerView?.adapter = adapter // ابتدا آداپتور را تنظیم کنید

                    } else {
                        loadNoTaskImg()
                        Toast.makeText(context, "خطا!", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(context, "خطا35453!", Toast.LENGTH_LONG).show()
                    loadNoTaskImg()
                }
            }

            override fun onFailure(call: Call<ApiResponseJson>, t: Throwable) {
                loadNoTaskImg()
                Toast.makeText(context, t.localizedMessage.toString(), Toast.LENGTH_LONG).show()
                Log.d("alpha", t.localizedMessage)
            }
        })
    }

    private fun loadNoTaskImg() {

        if (!isAdded) return
        img_Notask.visibility=View.VISIBLE
        tv_notask.visibility=View.VISIBLE
        recyclerView?.visibility=View.GONE

        Glide.with(requireContext())
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.no_image_load)
            .into(img_Notask)
    }



}