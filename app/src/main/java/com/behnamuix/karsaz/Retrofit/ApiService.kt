package com.behnamuix.karsaz.Retrofit

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("send_task.php")
    fun addTask(
        @Field("task_title") title: String,
        @Field("task_tag") tag: String,
        @Field("task_parity") parity: String,
        @Field("task_time") time: String,
        @Field("task_date") date: String,
        @Field("task_cat") cat: String,
        @Field("task_desc") desc: String
    ): Call<ApiResponse>

    @POST("get_task.php")
    fun getTask():Call<ApiResponseJson>
}