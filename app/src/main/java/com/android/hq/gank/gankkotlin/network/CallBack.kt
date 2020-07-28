package com.android.hq.gank.gankkotlin.network

interface CallBack<T> {
    fun onSuccess(t: T?)
    fun onFail()
}