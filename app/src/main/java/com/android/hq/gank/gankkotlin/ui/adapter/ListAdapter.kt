package com.android.hq.gank.gankkotlin.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.android.hq.gank.gankkotlin.R
import com.android.hq.gank.gankkotlin.data.*
import com.android.hq.gank.gankkotlin.utils.AppUtils
import com.bumptech.glide.Glide
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic

class ListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    val TYPE_INVALID = -1
    val TYPE_CONTENT = 1
    val TYPE_IMAGE = 2
    val TYPE_HEADER = 3
    val TYPE_FOOTER = 4
    val TYPE_SEARCH_ITEM = 5
    val TYPE_HISTORY_FAV_ITEM = 6

    var fragment: Fragment? = null
    var list: MutableList<GankItem>? = null
    constructor(fragment: Fragment) {
        this.fragment = fragment;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType) {
            TYPE_CONTENT -> return ContentViewHolder(parent)
            TYPE_IMAGE -> return ImageViewHolder(parent)
            TYPE_HEADER -> return HeaderViewHolder(parent)
            TYPE_FOOTER -> return FooterHolder(parent)
            TYPE_SEARCH_ITEM -> return SearchViewHolder(parent)
            TYPE_HISTORY_FAV_ITEM -> return HistoryFavViewHolder(parent)
            else -> return null!!
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = list?.get(position)
        when(item) {
            is GankContentItem -> return TYPE_CONTENT
        }

        if (item is GankContentItem) {
            return TYPE_CONTENT
        } else if (item is GankImageItem) {
            return TYPE_IMAGE
        } else if (item is GankHeaderItem) {
            return TYPE_HEADER
        } else if (item is GankFooterItem) {
            return TYPE_FOOTER
        } else if (item is GankSearchItem) {
            return TYPE_SEARCH_ITEM
        } else if (item is HistoryFavItem) {
            return TYPE_HISTORY_FAV_ITEM
        }
        return TYPE_INVALID
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ContentViewHolder) {
            var item: GankContentItem = list?.get(position) as GankContentItem
            holder.title?.text = item.desc
            holder.from?.text = item.who
            holder.time?.text = AppUtils.formatPublishedTime(item.publishedAt?:"")
//            holder.itemView.setOnClickListener(object :View.OnClickListener{
//                override fun onClick(v: View?) {
//
//                }
//            })
            holder.itemView.setOnClickListener { v: View? ->
                AppUtils.startArticleDetailActivity(fragment!!.context!!, GankDetailData(item._id,item.type,item.url,
                    item.who,item.desc,item.publishedAt,System.currentTimeMillis()))
            }
        } else if (holder is ImageViewHolder) {
            Glide.with(fragment!!).load((list?.get(position) as GankImageItem).imageUrl)
                .centerCrop().into(holder.mImageView)
        } else if (holder is HeaderViewHolder) {
            var title :String = (list?.get(position) as GankHeaderItem).title
            holder.mTitle.text = title
            when (title) {
                GankType.TYPE_BENEFIT -> AppUtils.setTextViewLeftDrawableForHeader(
                    holder.mTitle,
                    MaterialDesignIconic.Icon.gmi_mood
                )
                GankType.TYPE_ANDROID -> AppUtils.setTextViewLeftDrawableForHeader(
                    holder.mTitle,
                    MaterialDesignIconic.Icon.gmi_android
                )
                GankType.TYPE_IOS -> AppUtils.setTextViewLeftDrawableForHeader(
                    holder.mTitle,
                    MaterialDesignIconic.Icon.gmi_apple
                )
                GankType.TYPE_APP -> AppUtils.setTextViewLeftDrawableForHeader(
                    holder.mTitle,
                    MaterialDesignIconic.Icon.gmi_apps
                )
                GankType.TYPE_REST_VIDEO -> AppUtils.setTextViewLeftDrawableForHeader(
                    holder.mTitle,
                    MaterialDesignIconic.Icon.gmi_collection_video
                )
                GankType.TYPE_EXPAND_RES -> AppUtils.setTextViewLeftDrawableForHeader(
                    holder.mTitle,
                    FontAwesome.Icon.faw_location_arrow
                )
                else -> AppUtils.setTextViewLeftDrawableForHeader(
                    holder.mTitle,
                    MaterialDesignIconic.Icon.gmi_more
                )
            }
        } else if (holder is SearchViewHolder) {

        } else if (holder is HistoryFavViewHolder) {

        }
    }

    override fun getItemCount(): Int {
        return list?.size?:0
    }

    fun updateData(list: MutableList<GankItem>) {
        if (this.list === null || !(this.list?.containsAll(list))!!) {
            forceUpdateData(list)
        } else {
            Toast.makeText(fragment?.activity, R.string.text_update, Toast.LENGTH_SHORT).show()
        }
    }

    fun forceUpdateData(list: MutableList<GankItem>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun loadMoreData(list: List<GankItem>?) {
        this.list?.removeAt(this.list?.size!! - 1)
        notifyItemRangeRemoved(this.list?.size!!, 1)
        if (list != null) {
            val size = this.list?.size
            this.list?.addAll(size!!, list)
            notifyItemRangeInserted(size!!, list.size)
        }
    }

    fun onLoadMore() {
        val size = this.list?.size
        this.list?.add(GankFooterItem())
        notifyItemRangeInserted(size!!, 1)
    }
}