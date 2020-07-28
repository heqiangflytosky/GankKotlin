package com.android.hq.gank.gankkotlin.ui.adapter

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentPagerAdapter
import java.util.ArrayList

class PagerAdapter: FragmentPagerAdapter {
    var mContext : Context? = null
    var mTabs = ArrayList<PageInfo>()

    constructor(activity: FragmentActivity?):super(activity!!.supportFragmentManager){
        mContext = activity
    }
    override fun getItem(position: Int): Fragment {
        val info = mTabs[position]
        return info.fragment
    }

    override fun getCount(): Int {
        return mTabs.size
    }

    fun addPage(fragment: Fragment) {
        val info = PageInfo(fragment)
        mTabs.add(info)
        notifyDataSetChanged()
    }
}

class PageInfo(val fragment: Fragment) {
    val tag: String? = null
}