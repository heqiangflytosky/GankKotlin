package com.android.hq.gank.gankkotlin.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.android.hq.gank.gankkotlin.R
import com.android.hq.gank.gankkotlin.data.GankType
import com.android.hq.gank.gankkotlin.ui.adapter.PagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {
    var viewPager:ViewPager?=null
    var tabLayout : TabLayout? = null
    var pagerAdapter : PagerAdapter?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return initView(inflater)
    }

    fun initView(inflater: LayoutInflater):View{
        var rootView = inflater.inflate(R.layout.fragment_main, null, false)
        viewPager = rootView.findViewById(R.id.view_pager)
        tabLayout = rootView.findViewById(R.id.tabs)
        pagerAdapter = PagerAdapter(activity)

        viewPager?.adapter = pagerAdapter
        viewPager?.offscreenPageLimit = 3
        tabLayout?.setupWithViewPager(viewPager)
        tabLayout?.setTabsFromPagerAdapter(pagerAdapter)

        addPages()
        addTabs()

        return rootView
    }

    fun addPages() {
        pagerAdapter?.addPage(GankDailyFragment())

        pagerAdapter?.addPage(GankListFragment.newInstance(GankType.TYPE_ANDROID))

        pagerAdapter?.addPage(GankListFragment.newInstance(GankType.TYPE_IOS))

        pagerAdapter?.addPage(GankListFragment.newInstance(GankType.TYPE_WEB))

        pagerAdapter?.addPage(GankListFragment.newInstance(GankType.TYPE_APP))

        pagerAdapter?.addPage(GankListFragment.newInstance(GankType.TYPE_EXPAND_RES))

    }

    fun addTabs() {
        addTab(R.string.tab_daily_recommend, 0, true)
        addTab(R.string.tab_daily_android, 1, false)
        addTab(R.string.tab_daily_ios, 2, false)
        addTab(R.string.tab_daily_web, 3, false)
        addTab(R.string.tab_daily_app, 4, false)
        addTab(R.string.tab_daily_expand_res, 5, false)

        viewPager?.currentItem = 0
    }

    fun addTab(textId:Int, position: Int, selected: Boolean) {
        var tab = tabLayout?.newTab()
        tab?.text = resources.getText(textId)

        tabLayout?.addTab(tab!!, position, selected)

    }
}