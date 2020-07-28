package com.android.hq.gank.gankkotlin.mvp

import com.android.hq.gank.gankkotlin.data.GankItem

interface GankListContract {
    interface Presenter : BasePresenter {
        fun loadMore(type: String, page: Int)
    }

    interface View : BaseView<Presenter> {
        fun setRefreshing(refreshing: Boolean)
        fun updateData(list: MutableList<GankItem>)
        fun updateMoreData(list: MutableList<GankItem>)
        fun updateSuccess(isEmpty: Boolean)
        fun updateError()
        fun updateLoadMoreError()
    }
}
