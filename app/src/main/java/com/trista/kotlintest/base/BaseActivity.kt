package com.trista.kotlintest.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 *  @author : zhouff
 *  date : 2020/10/12 15:12
 *  description :
 */
abstract  class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setTheme(getAppTheme())
        setContentView(setLayoutId())
        initActivity(savedInstanceState)
    }

    private fun initActivity(savedInstanceState: Bundle?){
        initView(savedInstanceState)
        initData()
    }

    abstract fun setLayoutId(): Int
    abstract fun initView(savedInstanceState: Bundle?)
    abstract fun initData()




}