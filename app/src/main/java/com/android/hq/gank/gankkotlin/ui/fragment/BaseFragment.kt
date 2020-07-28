package com.android.hq.gank.gankkotlin.ui.fragment

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.hq.gank.gankkotlin.R
import com.android.hq.gank.gankkotlin.ui.adapter.ListAdapter
import com.android.hq.gank.gankkotlin.ui.view.EmptyView

abstract class BaseFragment : Fragment(){

    val STATE_LOADING = 0
    val STATE_UPDATE_SUCCESS = 1
    val STATE_UPDATE_ERROR = 2
    val STATE_UPDATE_EMPTY = 3
    val STATE_NETWORK_ERROR = 4
    val STATE_FAVOURITE_EMPTY = 5
    val STATE_HISTORY_EMPTY = 6

    var recyclerView: RecyclerView? = null
    var refreshLayout: SwipeRefreshLayout? = null
    var adapter: ListAdapter? = null
    var emptyView: EmptyView? = null

    private var mLoadingMore = false
    private var mLinearLayoutManager: LinearLayoutManager? = null

    private var mCanLoadingMore = true
    private var mShowLoadMoreTipsTime: Long = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView : View = inflater.inflate(R.layout.refresh_recyclerview_fragment, null)

        recyclerView = rootView.findViewById(R.id.recycler_view)
        refreshLayout = rootView.findViewById(R.id.refresh)
        emptyView = rootView.findViewById(R.id.empty_view)

        recyclerView?.layoutManager = LinearLayoutManager(recyclerView?.context)
        recyclerView?.setOnScrollListener(mOnScrollListener)

        adapter = ListAdapter(this)
        recyclerView?.adapter = adapter

        refreshLayout?.setOnRefreshListener(mOnRefreshListener)
        refreshLayout?.setColorSchemeResources(R.color.blue, R.color.green, R.color.orange)

        mLinearLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerView?.setLayoutManager(mLinearLayoutManager)

        refreshLayout?.setEnabled(isEnablePullRefresh())
        mCanLoadingMore = isEnableLoadingMore()

        emptyView?.setOnClickListener { v: View? ->
            forceRefreshDate()
        }


        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isEnableRefreshOnViewCreate()) {
            refreshLayout?.post(Runnable { forceRefreshDate() })
        }
    }


    private val mOnRefreshListener = SwipeRefreshLayout.OnRefreshListener { updateData() }

    private fun forceRefreshDate() {
        refreshLayout?.setRefreshing(true)
        updateData()
    }

    fun updateSuccess(isEmpty: Boolean) {
        if (isEmpty) {
            updateState(STATE_UPDATE_EMPTY)
        } else {
            updateState(STATE_UPDATE_SUCCESS)
        }
    }

    fun updateError() {
        updateState(STATE_UPDATE_ERROR)
    }

    fun startLoadMore() {
        loadMore()
        mLoadingMore = true

    }

    fun loadMoreSuccess(isEmpty: Boolean) {
        mLoadingMore = false
        val currentTime = SystemClock.uptimeMillis()
        if (isEmpty && currentTime - mShowLoadMoreTipsTime > 2000) {
            mShowLoadMoreTipsTime = currentTime
            Toast.makeText(activity, R.string.empty_view_load_more_empty, Toast.LENGTH_SHORT).show()
        }
    }

    fun loadMoreError() {
        mLoadingMore = false
        val currentTime = SystemClock.uptimeMillis()
        if (currentTime - mShowLoadMoreTipsTime > 2000) {
            mShowLoadMoreTipsTime = currentTime
            Toast.makeText(activity, R.string.empty_view_load_more_error, Toast.LENGTH_SHORT).show()
        }
    }

    fun updateState(state: Int) {
        var showEmpty = false
        var clickable = true
        var title: String? = null
        when (state) {
            STATE_LOADING, STATE_UPDATE_SUCCESS -> showEmpty = false
            STATE_UPDATE_EMPTY -> {
                showEmpty = true
                title = getString(R.string.empty_view_update_empty)
            }
            STATE_UPDATE_ERROR -> {
                showEmpty = true
                title = getString(R.string.empty_view_update_error)
            }
            STATE_NETWORK_ERROR -> {
            }
            STATE_FAVOURITE_EMPTY, STATE_HISTORY_EMPTY -> {
                showEmpty = true
                title = getString(R.string.empty_view_empty_data)
                clickable = false
            }
        }

        emptyView?.visibility = if (showEmpty) View.VISIBLE else View.GONE
        emptyView?.isClickable = clickable
        if (showEmpty) {
            emptyView?.setTitle(title!!)
        }
    }

    private val mOnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (!mCanLoadingMore) {
                return
            }

            val isScrollUp = if (dy > 0) true else false
            val totalItemCount = mLinearLayoutManager?.itemCount
            val lastVisibleItemPosition = mLinearLayoutManager?.findLastCompletelyVisibleItemPosition()
            if (!mLoadingMore && isScrollUp && (lastVisibleItemPosition!! >= totalItemCount!! - 1)) {
                startLoadMore()
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
        }
    }

    abstract fun updateData()
    abstract fun loadMore()
    abstract fun isEnablePullRefresh(): Boolean
    abstract fun isEnableLoadingMore(): Boolean
    abstract fun isEnableRefreshOnViewCreate(): Boolean
}