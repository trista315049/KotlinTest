package com.trista.kotlintest.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 *  @author : zhouff
 *  date : 2020/10/12 15:23
 *  description :
 */
abstract class BaseFragment : Fragment() {
    private var isLoaded: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(setLayoutId(), container, false)
        initView(view)
        initData()
        return view
    }


    abstract fun setLayoutId(): Int
    abstract fun initView(view:View)
    abstract fun initData()
}