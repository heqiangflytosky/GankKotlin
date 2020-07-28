package com.android.hq.gank.gankkotlin.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

class SizeObserverLinearLayout: LinearLayout {
    private var mL: OnSizeWillChangeListener? = null
    private var mLastMeasuredHeight = 0
    constructor(context: Context): super(context){

    }
    constructor(context: Context?, attrs: AttributeSet ?):super(context, attrs) {

    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int):
            super(context, attributeSet, defStyleAttr){

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val measureWidth = View.MeasureSpec.getSize(widthMeasureSpec)
        val measureHeight = View.MeasureSpec.getSize(heightMeasureSpec)
        if (mLastMeasuredHeight != measureHeight) {
            mL?.onSizeWillChanged(measureWidth, measureHeight)
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mLastMeasuredHeight = measureHeight
    }

    fun setOnSizeChangeListener(l: OnSizeWillChangeListener) {
        mL = l
    }
}

interface OnSizeWillChangeListener {
    fun onSizeWillChanged(w: Int, h: Int)
}