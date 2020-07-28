package com.android.hq.gank.gankkotlin.ui.activity

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.android.hq.gank.gankkotlin.R
import com.android.hq.gank.gankkotlin.data.GankDetailData
import com.android.hq.gank.gankkotlin.utils.AppUtils
import kotlinx.android.synthetic.main.activity_article_detail.*
import kotlinx.android.synthetic.main.tool_bar_article_detail_bottom.*
import kotlinx.android.synthetic.main.tool_bar_article_detail_top.*

class ArticleDetailActivity:AppCompatActivity() {
    var data: GankDetailData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)
        initData()
        initView()
    }

    fun initData() {
        data = intent.getSerializableExtra(AppUtils.INTENT_ITEM_INFO) as GankDetailData?
    }

    fun initView() {
        menu_title.text = data?.title
        presenter.text = resources.getString(R.string.text_presenter)+data?.who

        webview.settings.javaScriptEnabled = true
        webview.webChromeClient = GankWebChromeClient()
        webview.webViewClient = GankWebViewClient()
        webview.loadUrl(data?.url)

    }

    override fun onDestroy() {
        super.onDestroy()
        (webview.parent as ViewGroup).removeView(webview)
        webview.destroy()
    }

    inner class GankWebChromeClient:WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            progress_bar.progress = newProgress
            if (newProgress == 100) {
                progress_bar.visibility = View.GONE
            }
        }
    }

    inner class GankWebViewClient:WebViewClient(){
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            progress_bar.visibility = View.VISIBLE
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            progress_bar.visibility = View.GONE
        }
    }
}

