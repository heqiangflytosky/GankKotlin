package com.android.hq.gank.gankkotlin.mvp

interface BasePresenter {
    fun subscribe()
    fun unsubscribe()
    fun loadData(type: String)
}