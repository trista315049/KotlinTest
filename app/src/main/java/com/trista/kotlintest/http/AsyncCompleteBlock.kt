package com.trista.kotlintest.http

/**
 *  @author : zhouff
 *  date : 2020/10/12 16:19
 *  description :
 */
open interface AsyncCompleteBlock<T> {
    fun onComplete(result: Boolean, data: T?, errMessage: String?)
}