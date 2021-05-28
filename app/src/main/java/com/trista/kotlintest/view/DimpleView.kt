package com.trista.kotlintest.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.trista.kotlintest.bean.Particle
import java.util.*
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

/**
 *  @author : zhouff
 *  date : 2020/10/20 10:29
 *  description :
 */
class DimpleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var particleList = mutableListOf<Particle>()
    private var paint = Paint()
    private var centerX: Float = 0f
    private var centerY: Float = 0f
    private var random = Random()
    private var path = Path()
    private var animator = ValueAnimator.ofFloat(0f, 1f)
    private val pathMeasure = PathMeasure()//路径，用于测量扩散圆某一处的X,Y值
    private var pos = FloatArray(2) //扩散圆上某一点的x,y
    private val tan = FloatArray(2)//扩散圆上某一点切线
    private var radius = 200f

    public fun setRadious(r: Float) {
        radius = r
        invalidate()
    }


    init {
        animator.duration = 2000
        animator.repeatCount = -1
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener {
            updateParticle(it.animatedValue as Float)
            invalidate()
        }
    }

    private fun updateParticle(value: Float) {

        particleList.forEach { particle ->
            if (particle.offset > particle.maxOffset) {
                particle.offset = 0
                particle.speed = (random.nextInt(10) + 5).toFloat()
            }
            particle.alpha = ((1f - particle.offset / particle.maxOffset) * 225f).toInt()
            particle.x = (centerX + cos(particle.angle) * (radius + particle.offset)).toFloat()
            if (particle.y > centerY) {
                particle.y = (sin(particle.angle) * (radius + particle.offset) + centerY).toFloat()
            } else {
                particle.y = (centerY - sin(particle.angle) * (radius + particle.offset)).toFloat()
            }
            particle.offset += particle.speed.toInt()
        }
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.color = Color.BLACK
        paint.isAntiAlias = true
//        var time = measureTimeMillis {
        particleList.forEach {
            paint.alpha = it.alpha
            canvas?.drawCircle(it.x, it.y, it.radius, paint)
        }
//        }
//        Log.e("dimple", "绘制时间$time ms")

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = (w / 2).toFloat()
        centerY = (h / 2).toFloat()
        path.addCircle(centerX, centerY, radius, Path.Direction.CCW)
        pathMeasure.setPath(path, false)
        var nextX = 0f
        var nextY = 0f
        var angle = 0.00
        var speed = 0f
        var offSet = 0
        var maxOffset = 0f
        for (i in 0..2000) {
            pathMeasure.getPosTan(i / 2000f * pathMeasure.length, pos, tan)
            nextX = pos[0] + random.nextInt(6) - 3f
            nextY = pos[1] + random.nextInt(6) - 3f
            angle = acos(((pos[0] - centerX) / radius).toDouble())
            speed = random.nextInt(2) + 2f
            offSet = random.nextInt(200)
            maxOffset = random.nextInt(200).toFloat()
            particleList.add(
                Particle(
                    nextX,
                    nextY,
                    2f,
                    speed.toFloat(),
                    100,
                    offSet,
                    angle,
                    maxOffset
                )
            )
        }
        animator.start()
    }
}