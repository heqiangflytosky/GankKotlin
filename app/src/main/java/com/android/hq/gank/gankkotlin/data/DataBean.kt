package com.android.hq.gank.gankkotlin.data

import com.android.hq.gank.gankkotlin.utils.OpenDataClass
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.ArrayList

@OpenDataClass
data class GankItemBean (
    var _id: String?,
    var createdAt: String?,
    var desc: String?,
    var publishedAt: String?,
    var source: String?,
    var type: String?,
    var url: String?,
    var used: String?,
    var who: String?
)

data class DailyDataBean (
    @SerializedName(GankType.TYPE_ANDROID)
    var androidDataList: ArrayList<GankItemBean>,
    @SerializedName(GankType.TYPE_IOS)
    var iosGDataList: ArrayList<GankItemBean>,
    @SerializedName(GankType.TYPE_WEB)
    var webDataList: ArrayList<GankItemBean>,
    @SerializedName(GankType.TYPE_APP)
    var appDataList: ArrayList<GankItemBean>,
    @SerializedName(GankType.TYPE_BENEFIT)
    var benefitDataList: ArrayList<GankItemBean>,
    @SerializedName(GankType.TYPE_REST_VIDEO)
    var restVideoDataList: ArrayList<GankItemBean>,
    @SerializedName(GankType.TYPE_EXPAND_RES)
    var expandResDataList: ArrayList<GankItemBean>,
    @SerializedName(GankType.TYPE_RECOMMEND)
    var recommendDataList: ArrayList<GankItemBean>
)

@OpenDataClass
data class GankSearchItemBean (
    var desc: String?,
    var ganhuo_id: String?,
    var publishedAt: String?,
    var readability: String?,
    var type: String?,
    var url: String?,
    var who: String?
)

data class DailyDataResponse (
    var error : Boolean,
    var results: DailyDataBean,
    var category: ArrayList<String>
)

data class DayHistoryResponse(
    var error : Boolean,
    var results: ArrayList<String>
)


data class GankDataResponse(
    var error : Boolean,
    var results: ArrayList<GankItemBean>
)


data class SearchDataResponse (
    var error : Boolean,
    var count: String,
    var results: ArrayList<GankSearchItemBean>
)

data class AddToGankResponse (
    var error : Boolean,
    var msg: String
)

open class GankDetailData(var gank_id: String?,var gank_type: String?,var url: String?, var who: String?,var title: String?,
                     var published_date: String?,var action_date: Long) : Serializable