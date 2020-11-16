package com.android.hq.gank.gankkotlin.network

import com.android.hq.gank.gankkotlin.data.*
import retrofit2.Call
import retrofit2.http.*

interface GankService {
    @GET("data/category/GanHuo/type/{category}/page/{page}/count/{pageCount}")
    fun getGankData(
        @Path("category") category: String, @Path("pageCount") pageCount: Int, @Path("page") page: Int
    ): Call<GankDataResponse>
}