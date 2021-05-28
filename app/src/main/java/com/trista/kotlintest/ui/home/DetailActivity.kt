package com.trista.kotlintest.ui.home

import android.os.Bundle
import android.webkit.WebView
import com.trista.kotlintest.R
import com.trista.kotlintest.base.BaseActivity

class DetailActivity : BaseActivity() {
    private var web: WebView? = null

    override fun setLayoutId(): Int {
        return R.layout.activity_home_detail
    }

    override fun initView(savedInstanceState: Bundle?) {
        web = findViewById(R.id.web)
    }

    override fun initData() {
     var link =  intent.getStringExtra("link")
        web?.loadUrl(link)
    }

}
