package com.trista.kotlintest.ui.home

import android.content.Context
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.trista.kotlintest.http.*
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.loader.ImageLoader
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    fun getBanner(homeBanner:Banner?){
        RetrofitUtil.get().api.getBanner().enqueue(object :Callback<BaseResult<ArrayList<BannerInfo>>>{
            override fun onFailure(call: Call<BaseResult<ArrayList<BannerInfo>>>, t: Throwable) {
                println(t?.localizedMessage)
                homeBanner?.visibility = GONE
            }

            override fun onResponse(
                call: Call<BaseResult<ArrayList<BannerInfo>>>,
                response: Response<BaseResult<ArrayList<BannerInfo>>>
            ) {
                val userInfoEntity:ArrayList<BannerInfo> = response?.body()?.data!!
                if (userInfoEntity.size>0){
                    homeBanner?.visibility = VISIBLE
                    var arrayImageUrl:ArrayList<String> = ArrayList<String>()
                    var arrayTitle:ArrayList<String> = ArrayList<String>()
                    userInfoEntity.forEach {
                        arrayImageUrl.add(it.imagePath)
                        arrayTitle.add(it.title)
                    }
                    initBanner(homeBanner,arrayImageUrl,arrayTitle)
                } else homeBanner?.visibility = GONE

            }

        })
    }

    private fun initBanner(homeBanner:Banner?,arrayImageUrl:ArrayList<String>?,arrayTitle:ArrayList<String>) {
        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        homeBanner?.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
        //设置图片加载器，图片加载器在下方
        homeBanner?.setImageLoader(MyLoader())
        //设置图片网址或地址的集合
        homeBanner?.setImages(arrayImageUrl)
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        homeBanner?.setBannerAnimation(Transformer.Default)
        //设置轮播图的标题集合
        homeBanner?.setBannerTitles(arrayTitle)
        //设置轮播间隔时间
        homeBanner?.setDelayTime(3000)
        //设置是否为自动轮播，默认是“是”。
        homeBanner?.isAutoPlay(true)
        //设置指示器的位置，小点点，左中右。
        homeBanner?.setIndicatorGravity(BannerConfig.CENTER)
        //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。

        homeBanner?.setOnBannerListener {
            Log.d("=*=", "第几张" + it.dec())
        }
        //必须最后调用的方法，启动轮播图。
        homeBanner?.start()

    }
    //自定义的图片加载器
    private inner class MyLoader : ImageLoader() {
        override fun displayImage(context: Context, path: Any, imageView: ImageView) {
            Glide.with(context).load(path as String).centerCrop().into(imageView)
        }
    }

    /**
     * 首页文章列表
     */
    fun getHomeList(page: Int, callback: AsyncCompleteBlock<HomeListInfo>) {
        RetrofitUtil.get().api.getHomeList(page).enqueue(object : Callback<BaseResult<HomeListInfo>> {
            override fun onFailure(call: Call<BaseResult<HomeListInfo>>, t: Throwable) {
                callback.onComplete(false, null, t?.localizedMessage)
            }
            override fun onResponse(
                call: Call<BaseResult<HomeListInfo>>,
                response: Response<BaseResult<HomeListInfo>>
            ) {
                val userInfoEntity = response?.body()//获取请求数据实体
                println(userInfoEntity.toString())//打印请求结果
                callback.onComplete(true, userInfoEntity?.data, "")
            }

        })
    }
}