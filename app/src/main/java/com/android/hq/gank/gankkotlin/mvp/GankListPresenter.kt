package com.android.hq.gank.gankkotlin.mvp

import androidx.annotation.NonNull
import com.android.hq.gank.gankkotlin.data.GankContentItem
import com.android.hq.gank.gankkotlin.data.GankDataResponse
import com.android.hq.gank.gankkotlin.data.GankItem
import com.android.hq.gank.gankkotlin.network.CallBack
import com.android.hq.gank.gankkotlin.network.RequestManager
import io.reactivex.disposables.CompositeDisposable
import java.util.ArrayList

class GankListPresenter :GankListContract.Presenter {
    private var mView: GankListContract.View
    private var mCompositeDisposable: CompositeDisposable

    constructor(@NonNull view: GankListContract.View): super() {
        mView = view
        mView.setPresenter(this)
        mCompositeDisposable = CompositeDisposable()
    }

    override fun subscribe() {
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun loadData(type: String) {
        RequestManager.instance.getGankData(type, 20, 1, object :CallBack<GankDataResponse> {
            override fun onSuccess(gankDataResponse: GankDataResponse?) {
                mView.setRefreshing(false)
                val list = ArrayList<GankItem>()
                if (gankDataResponse != null && gankDataResponse.results != null) {
                    for (bean in gankDataResponse.results) {
                        list.add(GankContentItem(bean))
                    }
                }
                mView.updateData(list)
                mView.updateSuccess(list.isEmpty())
            }

            override fun onFail() {
                mView.setRefreshing(false)
                mView.updateError()
            }

        })
    }

    override fun loadMore(type: String, page: Int) {
        RequestManager.instance.getGankData(type, 20, page, object : CallBack<GankDataResponse>{
            override fun onSuccess(gankDataResponse: GankDataResponse?) {
                val list = ArrayList<GankItem>()
                if (gankDataResponse != null && gankDataResponse.results != null) {
                    for (bean in gankDataResponse.results) {
                        list.add(GankContentItem(bean))
                    }
                }
                mView.updateMoreData(list)
            }

            override fun onFail() {
                mView.updateLoadMoreError()
            }
        })
    }

}