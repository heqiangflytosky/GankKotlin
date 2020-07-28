package com.android.hq.gank.gankkotlin.network

import com.android.hq.gank.gankkotlin.data.*
import retrofit2.http.*
import rx.Observable

interface GankService {
    @GET(GankApi.GANK_TODAY)
    fun getGankToday(): Observable<DailyDataResponse>

    @GET("day/{year}/{month}/{day}")
    fun getDailyData(
        @Path("year") year: Int, @Path("month") month: Int, @Path("day") day: Int
    ): Observable<DailyDataResponse>

    @GET(GankApi.GANK_DAY_HISTORY)
    fun getDayHistory(): Observable<DayHistoryResponse>

    @GET("data/{category}/{pageCount}/{page}")
    fun getGankData(
        @Path("category") category: String, @Path("pageCount") pageCount: Int, @Path("page") page: Int
    ): Observable<GankDataResponse>

    @GET("search/query/{keyword}/category/all/count/{count}/page/{page}")
    fun searchData(
        @Path("keyword") keyword: String, @Path("count") count: Int, @Path("page") page: Int
    ): Observable<SearchDataResponse>

    @FormUrlEncoded
    @POST("add2gank")
    fun add2Gank(
        @Field("url") url: String, @Field("desc") desc: String, @Field("who") who: String,
        @Field("type") type: String, @Field("debug") debug: Boolean
    ): Observable<AddToGankResponse>
}