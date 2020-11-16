package com.android.hq.gank.gankkotlin.mvp

import androidx.annotation.NonNull
import com.android.hq.gank.gankkotlin.data.GankContentItem
import com.android.hq.gank.gankkotlin.data.GankDataResponse
import com.android.hq.gank.gankkotlin.data.GankItem
import com.android.hq.gank.gankkotlin.network.RequestManager
import java.util.ArrayList

class GankListPresenter :GankListContract.Presenter {
    private var mView: GankListContract.View

    constructor(@NonNull view: GankListContract.View): super() {
        mView = view
        mView.setPresenter(this)
    }

    override fun subscribe() {
    }

    override fun unsubscribe() {
    }

    override fun loadData(type: String) {
        RequestManager.instance.getGankData(type, 20, 1,
            onSuccess = {gankDataResponse: GankDataResponse ->
                mView.setRefreshing(false)
                val list = ArrayList<GankItem>()
                if (gankDataResponse != null && gankDataResponse.data != null) {
                    for (bean in gankDataResponse.data) {
                        list.add(GankContentItem(bean))
                    }
                }
                mView.updateData(list)
                mView.updateSuccess(list.isEmpty())
            },


            onFail =  {
                mView.setRefreshing(false)
                mView.updateError()
            }

        )
    }

    override fun loadMore(type: String, page: Int) {
        RequestManager.instance.getGankData(type, 20, page,
            onSuccess = {gankDataResponse: GankDataResponse ->
                val list = ArrayList<GankItem>()
                if (gankDataResponse != null && gankDataResponse.data != null) {
                    for (bean in gankDataResponse.data) {
                        list.add(GankContentItem(bean))
                    }
                }
                mView.updateMoreData(list)
            },

            onFail = {
                mView.updateLoadMoreError()
            }
        )
    }

}