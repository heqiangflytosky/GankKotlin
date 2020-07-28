package com.android.hq.gank.gankkotlin.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class ViewPagerEx : ViewPager {
    var canScroll:Boolean = true
    constructor(context: Context):super(context) {

    }

    constructor(context: Context, attrs: AttributeSet):super(context,attrs) {

    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return canScroll && super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return canScroll && super.onTouchEvent(ev)
    }

}