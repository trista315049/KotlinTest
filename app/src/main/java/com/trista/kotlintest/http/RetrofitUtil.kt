package com.trista.kotlintest.http

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 *  @author : zhouff
 *  date : 2020/10/12 15:56
 *  description :
 */
class RetrofitUtil private constructor() {
    companion object {
        private var instance: RetrofitUtil? = null
            get() {
                if (field == null) {
                    field = RetrofitUtil()
                }
                return field
            }

        fun get(): RetrofitUtil {
            //细心的小伙伴肯定发现了，这里不用getInstance作为为方法名，是因为在伴生对象声明时，内部已有getInstance方法，所以只能取其他名字
            return instance!!
        }
    }

    val baseUrl: String = "https://www.wanandroid.com"
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(Api::class.java)


}