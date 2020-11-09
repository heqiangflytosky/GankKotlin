package com.android.hq.gank.gankkotlin.utils

import android.content.Context
import android.content.Intent
import android.os.Environment
import android.widget.TextView
import com.android.hq.gank.gankkotlin.R
import com.android.hq.gank.gankkotlin.data.GankDetailData
import com.android.hq.gank.gankkotlin.ui.activity.ArticleDetailActivity
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*



object AppUtils {
    const val INTENT_ITEM_INFO = "intent_item_info"
    private var mAppContext: Context? = null
    private val sDataFormatZ = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private val sDataFormat6S = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private val sDataFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    fun init(context: Context) {
        mAppContext = context
        //sDataFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    fun getCacheDir(): String {
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable()) {
            val cacheFile = mAppContext!!.externalCacheDir
            if (null != cacheFile) {
                return cacheFile.path
            }
        }
        return mAppContext!!.cacheDir.path
    }

    fun formatPublishedTime(time: String): String? {
        var c: Calendar? = null
        try {
            if (time.endsWith("Z")) {
                sDataFormatZ.parse(time)
                c = sDataFormatZ.getCalendar()
            } else if (time.endsWith("SSSSSS")) {
                sDataFormat6S.parse(time)
                c = sDataFormat6S.getCalendar()
            } else {
                sDataFormat.parse(time)
                c = sDataFormat.getCalendar()
            }
        } catch (e: ParseException) {
            e.printStackTrace()
            return null
        }

        if (c != null) {
            val year = c!!.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH) + 1
            val day = c.get(Calendar.DAY_OF_MONTH)
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)
            val second = c.get(Calendar.SECOND)

            //获取当前时间
            //            Date date = new Date();
            //            sDataFormat.format(date);
            c.timeInMillis = System.currentTimeMillis()
            val cur_year = c.get(Calendar.YEAR)
            val cur_month = c.get(Calendar.MONTH) + 1
            val cur_day = c.get(Calendar.DAY_OF_MONTH)
            val cur_hour = c.get(Calendar.HOUR_OF_DAY)
            val cur_minute = c.get(Calendar.MINUTE)
            val cur_second = c.get(Calendar.SECOND)

            if (year < cur_year) {
                return (cur_year - year).toString() + mAppContext?.resources?.getString(R.string.text_years_ago)
            } else if (month < cur_month) {
                return (cur_month - month).toString() + mAppContext?.resources?.getString(R.string.text_months_ago)
            } else if (day < cur_day) {
                return (cur_day - day).toString() + mAppContext?.resources?.getString(R.string.text_days_ago)
            } else if (hour < cur_hour) {
                return (cur_hour - hour).toString() + mAppContext?.resources?.getString(R.string.text_hours_ago)
            } else if (minute < cur_minute) {
                return (cur_minute - minute).toString() + mAppContext?.resources?.getString(R.string.text_minutes_ago)
            } else if (second < cur_second) {
                return (cur_second - second).toString() + mAppContext?.resources?.getString(R.string.text_seconds_ago)
            }
        }
        return null
    }

    fun setTextViewLeftDrawableForHeader(textView: TextView, icon: IIcon) {
        val drawable = IconicsDrawable(mAppContext)
            .icon(icon)
            .color(mAppContext?.resources?.getColor(R.color.theme_primary)?:0)
            .sizeDp(14)
        textView.setCompoundDrawables(drawable, null, null, null)
    }

    fun startArticleDetailActivity(context: Context, data:GankDetailData) {
        var intent = Intent(context, ArticleDetailActivity::class.java)
        intent.putExtra(INTENT_ITEM_INFO, data)
        context.startActivity(intent)
        //MyClass.HH.newInstance()
    }

//    class MyClass {
//        companion object HH{
//            fun newInstance():MyClass {
//
//            }
//        }
//    }
}