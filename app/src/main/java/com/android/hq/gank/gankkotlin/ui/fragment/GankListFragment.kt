package com.android.hq.gank.gankkotlin.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.android.hq.gank.gankkotlin.data.GankItem
import com.android.hq.gank.gankkotlin.mvp.GankListContract
import com.android.hq.gank.gankkotlin.mvp.GankListPresenter

class GankListFragment : BaseFragment(),GankListContract.View {
    var mCurrentPage:Int = 0
    var mType:String?=null
    var mPresenter:GankListContract.Presenter?=null

    companion object {
        private const val TYPE = "type"

        fun newInstance(type:String):Fragment {
            var bundle = Bundle()
            bundle.putString(TYPE, type)
            var fragment = GankListFragment()
            fragment.arguments = bundle
            GankListPresenter(fragment)
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var bundle = arguments;
        mType = bundle?.getString(TYPE) ?:null
    }

    override fun onResume() {
        super.onResume()
        mPresenter?.subscribe()
    }

    override fun onPause() {
        super.onPause()
        mPresenter?.unsubscribe()
    }

    override fun updateData() {
        mPresenter?.loadData(mType!!)
    }

    override fun loadMore() {
        adapter?.onLoadMore()
        mPresenter?.loadMore(mType!!,++mCurrentPage)
    }

    override fun isEnablePullRefresh(): Boolean {
        return true
    }

    override fun isEnableLoadingMore(): Boolean {
        return true
    }

    override fun isEnableRefreshOnViewCreate(): Boolean {
        return true
    }

    override fun setRefreshing(refreshing: Boolean) {
        refreshLayout?.isRefreshing = refreshing
    }

    override fun updateData(list: MutableList<GankItem>) {
        adapter?.updateData(list)
        mCurrentPage = 1
    }

    override fun updateMoreData(list: MutableList<GankItem>) {
        adapter?.loadMoreData(list)
        loadMoreSuccess(list.isEmpty())
    }

    override fun updateLoadMoreError() {
        mCurrentPage--
        adapter?.loadMoreData(null)
        loadMoreError()
    }

    override fun setPresenter(presenter: GankListContract.Presenter) {
        mPresenter = presenter
    }
}