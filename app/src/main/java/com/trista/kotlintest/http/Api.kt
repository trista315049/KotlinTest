package com.trista.kotlintest.http

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *  @author : zhouff
 *  date : 2020/10/12 15:54
 *  description :
 */
interface Api {
    @GET("/article/list/{page}/json")
    fun getHomeList(@Path("page") id: Int): Call<BaseResult<HomeListInfo>>

    @GET("/banner/json")
    fun getBanner(): Call<BaseResult<ArrayList<BannerInfo>>>
}