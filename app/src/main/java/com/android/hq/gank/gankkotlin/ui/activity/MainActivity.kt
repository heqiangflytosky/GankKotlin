package com.android.hq.gank.gankkotlin.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.android.hq.gank.gankkotlin.R
import com.android.hq.gank.gankkotlin.ui.adapter.PagerAdapter
import com.android.hq.gank.gankkotlin.ui.fragment.AboutFragment
import com.android.hq.gank.gankkotlin.ui.fragment.MainFragment
import com.android.hq.gank.gankkotlin.ui.view.OnSizeWillChangeListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tool_bar.*

class MainActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var pagerAdapter : PagerAdapter

    private val colorSelected : Int by lazy {
        getColor(R.color.main_page_toolbar_select_text_color)
    }
    private val colorUnSelected : Int by lazy {
        getColor(R.color.main_page_toolbar_unselect_text_color)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

    }

    private fun initViews() {
        pagerAdapter = PagerAdapter(this)

        view_pager_main.adapter = pagerAdapter
        view_pager_main.canScroll = false
        view_pager_main.offscreenPageLimit = 3

        pagerAdapter.addPage(MainFragment())
        pagerAdapter.addPage(AboutFragment())

        search.visibility = View.GONE
        present.visibility = View.GONE

        resetButton()

        view_pager_main.currentItem = 0
        home_img.setImageResource(R.drawable.tab_home)
        home_tv.setTextColor(colorSelected)

        root_view.setOnSizeChangeListener(object : OnSizeWillChangeListener {
            override fun onSizeWillChanged(w: Int, h: Int) {
                val heightDiff = root_view.getRootView().getMeasuredHeight() - h
                if (heightDiff > 100) {
                    tool_bar.visibility = View.GONE
                } else {
                    tool_bar.visibility = View.VISIBLE
                }
            }
        })

        page1.setOnClickListener(this)
        search.setOnClickListener(this)
        present.setOnClickListener(this)
        about.setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        resetButton()
        when (v?.id) {
            R.id.page1 -> {
                view_pager_main.currentItem = 0
                home_img.setImageResource(R.drawable.tab_home)
                home_tv.setTextColor(colorSelected)
            }
            R.id.search -> {
                view_pager_main.currentItem = 1
                search_img.setImageResource(R.drawable.tab_explore)
                search_tv.setTextColor(colorSelected)
            }
            R.id.present -> {
                view_pager_main.currentItem = 2
                present_img.setImageResource(R.drawable.tab_recommend)
                present_tv.setTextColor(colorSelected)
            }
            R.id.about -> {
                view_pager_main.currentItem = 3
                about_img.setImageResource(R.drawable.tab_profile)
                about_tv.setTextColor(colorSelected)
            }
        }
    }

    private fun resetButton() {
        home_img.setImageResource(R.drawable.tab_home_normal)
        search_img.setImageResource(R.drawable.tab_explore_normal)
        present_img.setImageResource(R.drawable.tab_recommend_normal)
        about_img.setImageResource(R.drawable.tab_profile_normal)

        home_tv.setTextColor(colorUnSelected)
        search_tv.setTextColor(colorUnSelected)
        present_tv.setTextColor(colorUnSelected)
        about_tv.setTextColor(colorUnSelected)
    }
}
