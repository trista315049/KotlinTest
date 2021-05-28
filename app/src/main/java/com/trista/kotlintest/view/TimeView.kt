package com.trista.kotlintest.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.View
import java.util.*


/**
 *  @author : zhouff
 *  date : 2021/5/28 11:24
 *  description :
 */
class TimeView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var paint: Paint = Paint()
    private val mDegreeCount = 60  //刻度总数
    private val mTimeDegreeHourLength = 12f //刻度盘小时刻度长度;
    private val mTimeDegreeMintueLength = 8f //刻度盘小时刻度长度;
    private var padding = 3
    private var hour: Int? = null
    private var minute: Int? = null
    private var second: Int? = null
    private var w = 0
    init {
        paint.strokeWidth = 3.0f
        paint.color = Color.BLACK
        paint.isAntiAlias = true
    }

    private var refreshThread: Thread? = null
    private var mHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when (msg?.what) {
                0 -> {
                    invalidate()
                }
            }

        }
    }

    private fun getCurrentTime() {
        val calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
        second = calendar.get(Calendar.SECOND)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        //启动线程 刷新界面
        refreshThread = Thread(Runnable {
            while (true) {
                try {
                    Thread.sleep(1000)
                    mHandler.sendEmptyMessage(0)
                } catch (e: InterruptedException) {
                    break
                }
            }
        })
        refreshThread?.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mHandler.removeCallbacksAndMessages(null)
        //中断线程
        refreshThread?.interrupt()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        getCurrentTime()

        paint.style = Paint.Style.STROKE
        //画圆
        canvas?.drawCircle(
            width / 2.0f,
            height / 2.0f,
            if (width > height) height / 2.0f else width / 2.0f,
            paint
        )
        paint.style = Paint.Style.FILL
        //画圆中心点
        canvas?.drawCircle(width / 2.0f, height / 2.0f, 5.0f, paint)
        //画刻度
        canvas?.let { canvasDegree(it) }
        //画时分秒针
        canvas?.let { canvasTimePoint(it) }

    }

    private fun canvasTimePoint(canvas: Canvas) {
        canvas.save()
         w = if (measuredWidth > measuredHeight) measuredHeight else measuredWidth
        drawSecond(canvas)
        paint.strokeWidth = 4f
        drawMinute(canvas)
        paint.strokeWidth = 6f
        drawHour(canvas)

    }

    /**
     * 绘制秒针
     */
    private fun drawSecond(canvas: Canvas?) {
        //秒针长半径 (表针会穿过表心 所以需要根据两个半径计算起始和结束半径)
        val longR = w / 2 - 60
//        val shortR = 60
        val shortR = 30
        val startX = (measuredWidth / 2 - shortR * Math.sin(second!!.times(Math.PI / 30))).toFloat()
        val startY =
            (measuredHeight / 2 + shortR * Math.cos(second!!.times(Math.PI / 30))).toFloat()
        val endX = (measuredWidth / 2 + longR * Math.sin(second!!.times(Math.PI / 30))).toFloat()
        val endY = (measuredHeight / 2 - longR * Math.cos(second!!.times(Math.PI / 30))).toFloat()
        canvas?.drawLine(startX, startY, endX, endY, paint)
    }

    /**
     * 绘制分针
     */
    private fun drawMinute(canvas: Canvas?) {
        //半径比秒针小一点
        val longR = w / 2 - 80
        val shortR = 50
//        val startX = (measuredWidth / 2 - shortR * Math.sin(minute!!.times(Math.PI / 30))).toFloat()
//        val startY = (measuredHeight / 2 + shortR * Math.cos(minute!!.times(Math.PI / 30))).toFloat()
        val startX = (measuredWidth / 2).toFloat()
        val startY = (measuredHeight / 2).toFloat()
        val endX = (measuredWidth / 2 + longR * Math.sin(minute!!.times(Math.PI / 30))).toFloat()
        val endY = (measuredHeight / 2 - longR * Math.cos(minute!!.times(Math.PI / 30))).toFloat()
        canvas?.drawLine(startX, startY, endX, endY, paint)
    }


    /**
     * 绘制时针
     */
    private fun drawHour(canvas: Canvas?) {
        //半径比秒针小一点
        val longR = w / 2 - 100
        val shortR = 40
//        val startX = (measuredWidth / 2 - shortR * Math.sin(hour!!.times(Math.PI / 6))).toFloat()
//        val startY = (measuredHeight / 2 + shortR * Math.cos(hour!!.times(Math.PI / 6))).toFloat()
        val startX = (measuredWidth / 2).toFloat()
        val startY = (measuredHeight / 2).toFloat()
        val endX = (measuredWidth / 2 + longR * Math.sin(hour!!.times(Math.PI / 6))).toFloat()
        val endY = (measuredHeight / 2 - longR * Math.cos(hour!!.times(Math.PI / 6))).toFloat()
        canvas?.drawLine(startX, startY, endX, endY, paint)
    }


    /**
     * 绘制刻度（表盘为例）：
     */
    private fun canvasDegree(canvas: Canvas) {
        var mCircleCenterX = width / 2.0f
        var mCircleCenterY = height / 2.0f
        var  r = mCircleCenterX
        if (width > height) {
            r = mCircleCenterY
        }
        canvas.save()
        for (i in 0 until mDegreeCount) {
            //区分整点与非整点
            if (i % 5 == 0) {
                paint.color = Color.BLACK
                paint.strokeWidth = 5.0f
                paint.textSize = 40f
                canvas.drawLine(
                    mCircleCenterX, mCircleCenterY - r + padding, mCircleCenterX,
                    mCircleCenterY - r + padding + mTimeDegreeHourLength, paint
                );

                var dergree = if (i == 0) "12" else (i / 5).toString()
                canvas.drawText(
                    dergree,
                    mCircleCenterX - paint.measureText(dergree) / 2,
                    mCircleCenterY - r + padding + 50,
                    paint
                )
            } else {
                paint.color = Color.GRAY
                paint.strokeWidth = 3.0f
                paint.textSize = 20f

                canvas.drawLine(
                    mCircleCenterX, mCircleCenterY - r + padding, mCircleCenterX,
                    mCircleCenterY - r + padding + mTimeDegreeMintueLength, paint
                )
            }
            //每次画布围绕圆心旋转的的度数。
            canvas.rotate(360.0f / mDegreeCount, mCircleCenterX, mCircleCenterY)
        }

    }


}