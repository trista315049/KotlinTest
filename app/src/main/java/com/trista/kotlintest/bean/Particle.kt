package com.trista.kotlintest.bean

/**
 *  @author : zhouff
 *  date : 2020/10/20 10:26
 *  description :
 */
class Particle (
    var x: Float ,
    var y: Float ,
    var radius: Float ,
    var speed: Float ,
    var alpha: Int,
    var offset:Int,//当前移动距离
    var angle:Double,//粒子角度
    var maxOffset:Float = 300f


)