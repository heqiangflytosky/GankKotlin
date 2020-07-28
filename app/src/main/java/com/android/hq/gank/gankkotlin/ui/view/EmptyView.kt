package com.android.hq.gank.gankkotlin.ui.view

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.android.hq.gank.gankkotlin.R

class EmptyView : FrameLayout {
    var titleView: TextView ? =null
    constructor(context: Context): this(context, null)

    constructor(context: Context, attrs: AttributeSet?): this(context,attrs,0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int):
            this(context, attrs, defStyleAttr,0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int):
            super(context, attrs, defStyleAttr, defStyleRes) {
        val view = LayoutInflater.from(context).inflate(R.layout.empty_view, null)
        titleView = view.findViewById(R.id.empty_title) as TextView

        addView(view)
    }

    fun setTitle(title: String) {
        if (TextUtils.isEmpty(title)) {
            titleView?.visibility=View.GONE
            return
        }
        titleView?.text = title
        titleView?.visibility = View.VISIBLE
    }
}