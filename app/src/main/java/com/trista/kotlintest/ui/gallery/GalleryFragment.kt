package com.trista.kotlintest.ui.gallery

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.trista.kotlintest.R
import com.trista.kotlintest.base.BaseFragment
import com.trista.kotlintest.util.GlideCircleTransform

class GalleryFragment : BaseFragment() {

    private lateinit var galleryViewModel: GalleryViewModel
    private var textView: TextView? = null
    private var imageView: ImageView? = null

    override fun setLayoutId(): Int { return R.layout.fragment_gallery   }
    override fun initView(view: View) {
        textView = view.findViewById(R.id.text_gallery)
        imageView = view.findViewById(R.id.image)
    }
    override fun initData() {
        galleryViewModel = ViewModelProviders.of(this).get(GalleryViewModel::class.java)
        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
            textView?.text = it
        })
        loadImage()
    }

    private fun loadImage() {
        Glide.with(activity)
            .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603170184299&di=ee28742229cc78e3e1a5992d3615fcf2&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F64%2F52%2F01300000407527124482522224765.jpg")
            .transform(GlideCircleTransform(activity))
            .into(imageView)
//        选装动画
        var rotateAnimator : ObjectAnimator = ObjectAnimator.ofFloat( imageView,View.ROTATION, 0f, 360f)
        rotateAnimator.duration = 6000
        rotateAnimator.repeatCount = -1
        rotateAnimator.interpolator = LinearInterpolator()
        rotateAnimator.start()
//        粒子动画
        liziAnimator()
    }

    /**
     * 圆形生产粒子
        粒子速度不同，也就是随机。
        粒子透明度不断降低，直到最后消散。
        粒子沿着到圆心的反方向扩散。
     */
    private fun liziAnimator() {

    }

}
