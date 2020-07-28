package com.android.hq.gank.gankkotlin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.hq.gank.gankkotlin.R
import com.android.hq.gank.gankkotlin.ui.view.RatioImageView

class ContentViewHolder: RecyclerView.ViewHolder{
    var title: TextView? = null
    var from: TextView? = null
    var time: TextView? = null

    constructor(parent: ViewGroup):
            super(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_content, parent, false)){

        title = itemView.findViewById(R.id.content_title) as TextView
        from = itemView.findViewById(R.id.item_footer_from) as TextView
        time = itemView.findViewById(R.id.item_footer_time) as TextView
    }
}

class FooterHolder(parent: ViewGroup):
    RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_content, parent, false))

class HeaderViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_title, parent, false)) {
    var mTitle: TextView

    init {
        mTitle = itemView.findViewById(R.id.category_title) as TextView
    }
}

class ImageViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_image, parent, false)) {
    var mImageView: RatioImageView

    init {
        mImageView = itemView.findViewById(R.id.image) as RatioImageView
        mImageView.ratio = 1.5f
    }
}

class SearchViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        R.layout.recycler_item_content,
        parent,
        false
    )
) {
    var mTitle: TextView
    var mFrom: TextView
    var mTime: TextView
    var mType: TextView

    init {
        mTitle = itemView.findViewById(R.id.content_title) as TextView
        mFrom = itemView.findViewById(R.id.item_footer_from) as TextView
        mTime = itemView.findViewById(R.id.item_footer_time) as TextView
        mType = itemView.findViewById(R.id.item_footer_type) as TextView
        mType.visibility = View.VISIBLE
    }
}

class HistoryFavViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        R.layout.recycler_item_content,
        parent,
        false
    )
) {
    var mTitle: TextView
    var mFrom: TextView
    var mTime: TextView
    var mType: TextView

    init {
        mTitle = itemView.findViewById(R.id.content_title) as TextView
        mFrom = itemView.findViewById(R.id.item_footer_from) as TextView
        mTime = itemView.findViewById(R.id.item_footer_time) as TextView
        mType = itemView.findViewById(R.id.item_footer_type) as TextView
        mType.visibility = View.VISIBLE
    }
}