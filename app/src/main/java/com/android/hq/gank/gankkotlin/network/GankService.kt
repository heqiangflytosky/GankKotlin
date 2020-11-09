package com.android.hq.gank.gankkotlin.network

import com.android.hq.gank.gankkotlin.data.*
import retrofit2.http.*
import rx.Observable

interface GankService {
    @GET("https://gank.io/api/"+GankApi.GANK_TODAY)
    fun getGankToday(): Observable<DailyDataResponse>

//    @GET("day/{year}/{month}/{day}")
//    fun getDailyData(
//        @Path("year") year: Int, @Path("month") month: Int, @Path("day") day: Int
//    ): Observable<DailyDataResponse>
//
//    @GET(GankApi.GANK_DAY_HISTORY)
//    fun getDayHistory(): Observable<DayHistoryResponse>

    @GET("data/category/GanHuo/type/{category}/page/{page}/count/{pageCount}")
    fun getGankData(
        @Path("category") category: String, @Path("pageCount") pageCount: Int, @Path("page") page: Int
    ): Observable<GankDataResponse>

    @GET("search/{keyword}/category/GanHuo/type/All/page/{page}/count/{count}")
    fun searchData(
        @Path("keyword") keyword: String, @Path("count") count: Int, @Path("page") page: Int
    ): Observable<SearchDataResponse>

    @FormUrlEncoded
    @POST("add2gank")
    fun add2Gank(
        @Field("url") url: String, @Field("title") desc: String, @Field("author") who: String,
        @Field("type") type: String, @Field("debug") debug: Boolean
    ): Observable<AddToGankResponse>
}