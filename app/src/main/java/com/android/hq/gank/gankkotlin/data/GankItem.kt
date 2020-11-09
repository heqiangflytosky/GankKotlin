package com.android.hq.gank.gankkotlin.data

import java.io.Serializable
import java.util.*

interface GankItem : Serializable

class GankContentItem(bean: GankItemBean) : GankItemBean(bean._id,bean.createdAt,bean.title,bean.desc,bean.publishedAt,bean.source,
    bean.type,bean.url,bean.used,bean.author),GankItem

class GankImageItem(var imageUrl:String?) : GankItemBean(null,null,null,null,null,
    null,null,null,null,null),GankItem {
    override fun equals(other: Any?): Boolean {
        if(this === other){
            return true
        }
        if (other !is GankImageItem) {
            return false
        }

        return imageUrl.equals(other.imageUrl)
    }

    override fun hashCode(): Int {
        return Objects.hash(imageUrl)
    }
}

class GankHeaderItem(var label:String) : GankItemBean(null,null,null,null,null,
    null,null,null,null, null),GankItem {
    override fun equals(other: Any?): Boolean {
        if(this === other){
            return true
        }
        if (other !is GankHeaderItem) {
            return false
        }

        return label.equals(other.label)
    }

    override fun hashCode(): Int {
        return Objects.hash(label)
    }
}

class GankFooterItem() :GankItemBean(null,null,null,null,null,
    null,null,null,null,null),GankItem {
    override fun equals(other: Any?): Boolean {
        if(this === other){
            return true
        }
        if (other !is GankFooterItem) {
            return false
        }

        return true
    }

    override fun hashCode(): Int {
        return Objects.hash(GankFooterItem::class.java)
    }
}

class GankSearchItem(bean: GankSearchItemBean):GankSearchItemBean(bean.desc,bean.ganhuo_id,bean.publishedAt,bean.readability,bean.type,
    bean.url,bean.who),GankItem {
    override fun equals(other: Any?): Boolean {
        if(this === other){
            return true
        }
        if (other !is GankSearchItem) {
            return false
        }

        return true
    }

    override fun hashCode(): Int {
        return Objects.hash(ganhuo_id, type)
    }
}

class HistoryFavItem(gank_id: String?,gank_type: String?,url: String?, who: String?,title: String?,desc: String?,
                     published_date: String?,action_date: Long) : GankDetailData(gank_id,gank_type,url,who,title,desc,published_date,action_date),GankItem {


    override fun equals(o: Any?): Boolean {
        if (this === o)
            return true
        if (o !is HistoryFavItem)
            return false
        return o.gank_id.equals(this.gank_id) && o.gank_type.equals(this.gank_type)
    }

    override fun hashCode(): Int {
        return Objects.hash(gank_id, gank_type)
    }
}