package com.android.hq.gank.gankkotlin.mvp

interface BaseView<T> {
    fun setPresenter(presenter: T)
}