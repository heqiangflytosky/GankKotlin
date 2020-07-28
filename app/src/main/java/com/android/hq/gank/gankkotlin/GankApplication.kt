package com.android.hq.gank.gankkotlin

import android.app.Application
import com.android.hq.gank.gankkotlin.utils.AppUtils

class GankApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppUtils.init(this)
    }
}