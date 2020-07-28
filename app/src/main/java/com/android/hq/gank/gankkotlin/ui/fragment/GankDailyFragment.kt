package com.android.hq.gank.gankkotlin.ui.fragment

import android.util.Log
import com.android.hq.gank.gankkotlin.data.*
import com.android.hq.gank.gankkotlin.network.CallBack
import com.android.hq.gank.gankkotlin.network.RequestManager
import java.util.ArrayList

class GankDailyFragment: BaseFragment() {
    val LOG_TAG = "GankDailyFragment"
    override fun updateData() {
        RequestManager.instance.getDailyData(object : CallBack<DailyDataResponse>{
            override fun onSuccess(dailyDataResponse: DailyDataResponse?) {
                Log.e(LOG_TAG, "onSuccess")
                refreshLayout?.isRefreshing = false
                val list = ArrayList<GankItem>()
                val maxLength = 5
                if (dailyDataResponse != null) {
                    val dataResult = dailyDataResponse.results
                    if (dataResult != null) {
                        val benefitDataList = dataResult!!.benefitDataList
                        if (benefitDataList != null && benefitDataList!!.size != 0) {
                            list.add(GankHeaderItem(GankType.TYPE_BENEFIT))
                            list.add(GankImageItem(benefitDataList!!.get(benefitDataList!!.size - 1).url))
                        }

                        val androidDataList = dataResult!!.androidDataList
                        if (androidDataList != null && androidDataList!!.size != 0) {
                            list.add(GankHeaderItem(GankType.TYPE_ANDROID))
                            for (i in androidDataList!!.size downTo androidDataList!!.size - maxLength + 1) {
                                if (i - 1 < 0) {
                                    break
                                }
                                list.add(GankContentItem(androidDataList!!.get(i - 1)))
                            }
                        }

                        val iosGDataList = dataResult!!.iosGDataList
                        if (iosGDataList != null && iosGDataList!!.size != 0) {
                            list.add(GankHeaderItem(GankType.TYPE_IOS))
                            for (i in iosGDataList!!.size downTo iosGDataList!!.size - maxLength + 1) {
                                if (i - 1 < 0) {
                                    break
                                }
                                list.add(GankContentItem(iosGDataList!!.get(i - 1)))
                            }
                        }

                        val webDataList = dataResult!!.webDataList
                        if (webDataList != null && webDataList!!.size != 0) {
                            list.add(GankHeaderItem(GankType.TYPE_WEB))
                            for (i in webDataList!!.size downTo webDataList!!.size - maxLength + 1) {
                                if (i - 1 < 0) {
                                    break
                                }
                                list.add(GankContentItem(webDataList!!.get(i - 1)))
                            }
                        }

                        val appDataList = dataResult!!.appDataList
                        if (appDataList != null && appDataList!!.size != 0) {
                            list.add(GankHeaderItem(GankType.TYPE_APP))
                            for (i in appDataList!!.size downTo appDataList!!.size - maxLength + 1) {
                                if (i - 1 < 0) {
                                    break
                                }
                                list.add(GankContentItem(appDataList!!.get(i - 1)))
                            }
                        }

                        val restVideoDataList = dataResult!!.restVideoDataList
                        if (restVideoDataList != null && restVideoDataList!!.size != 0) {
                            list.add(GankHeaderItem(GankType.TYPE_REST_VIDEO))
                            for (i in restVideoDataList!!.size downTo restVideoDataList!!.size - maxLength + 1) {
                                if (i - 1 < 0) {
                                    break
                                }
                                list.add(GankContentItem(restVideoDataList!!.get(i - 1)))
                            }
                        }

                        val expandResDataList = dataResult!!.expandResDataList
                        if (expandResDataList != null && expandResDataList!!.size != 0) {
                            list.add(GankHeaderItem(GankType.TYPE_EXPAND_RES))
                            for (i in expandResDataList!!.size downTo expandResDataList!!.size - maxLength + 1) {
                                if (i - 1 < 0) {
                                    break
                                }
                                list.add(GankContentItem(expandResDataList!!.get(i - 1)))
                            }
                        }

                        val recommendDataList = dataResult!!.recommendDataList
                        if (recommendDataList != null && recommendDataList!!.size != 0) {
                            list.add(GankHeaderItem(GankType.TYPE_RECOMMEND))
                            for (i in recommendDataList!!.size downTo recommendDataList!!.size - maxLength + 1) {
                                if (i - 1 < 0) {
                                    break
                                }
                                list.add(GankContentItem(recommendDataList!!.get(i - 1)))
                            }
                        }
                    }
                }
                adapter?.updateData(list)
                updateSuccess(list.isEmpty())

            }

            override fun onFail() {
                refreshLayout?.isRefreshing = false
                updateError()
            }

        })
    }

    override fun loadMore() {

    }

    override fun isEnablePullRefresh(): Boolean {
        return true
    }

    override fun isEnableLoadingMore(): Boolean {
        return false
    }

    override fun isEnableRefreshOnViewCreate(): Boolean {
        return true
    }

}