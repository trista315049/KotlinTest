package com.trista.kotlintest.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 *  @author : zhouff
 *  date : 2021/5/10 16:03
 *  description :
 */
class HeartView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var mMeasureWidth: Int = 0
    private var mWidthMode: Int = 0
    private var mMeasureHeight: Int = 0
    private var mHeightMode: Int = 0
    private var paint: Paint? = null

    init {
        paint = Paint()//实例画笔
        paint.let {
            it?.isAntiAlias = true//抗锯齿
            it?.strokeWidth = 2.0f//画笔宽度
            it?.color = Color.LTGRAY//画笔颜色
            it?.setStyle(Paint.Style.FILL)//画笔样式
        }

    }

    fun setSelect(isSelect:Boolean){
        paint?.color = if (isSelect) Color.RED else Color.LTGRAY
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        mHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        mMeasureWidth = MeasureSpec.getSize(widthMeasureSpec);
        mMeasureHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (mWidthMode == MeasureSpec.AT_MOST && mHeightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200, 200);
        } else if (mWidthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200, mMeasureHeight);
        } else if (mHeightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mMeasureWidth, 200);
        } else {
            setMeasuredDimension(mMeasureWidth, mMeasureHeight);
        }

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var w: Float = width*1.0f
        var h: Float = height*1.0f
        var path = Path()
        path.moveTo(w / 2, h / 4);
        path.cubicTo(
            (w * 6) / 7,
            h / 9,
            (w * 12) / 13,
            (h * 2) / 5,
            w / 2,
            (h * 7) / 12
        );
        paint?.let { canvas?.drawPath(path, it) }
        //右半面
        var path2 = Path()
        path2.moveTo(w / 2, h / 4);
        path2.cubicTo(
            w / 7,
            h / 9,
            w / 13,
            (h * 2) / 5,
            w / 2,
            (h * 7) / 12
        )
        paint?.let { canvas?.drawPath(path2, it) }

    }

}
