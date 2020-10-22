package com.example.stupatest.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class ChartView(context: Context, attrs: AttributeSet): View(context, attrs) {

    var X1:Float = 0f
    var Y1:Float = 0f
    var X2:Float = 0f
    var Y2:Float = 0f
    var X3:Float = 0f
    var Y3:Float = 0f
    var X4:Float = 0f
    var Y4:Float = 0f

    private lateinit var paint:Paint
    private lateinit var path: Path
    init {
        paint = Paint()

        path = Path()
    }



    override fun onDraw(canvas: Canvas?) {

        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4.5f

        path.reset()
        path.moveTo(X1, Y1)
        path.lineTo(X2, Y2)
        path.lineTo(X3, Y3)
        path.lineTo(X4, Y4)
        path.lineTo(X1, Y1)

        canvas?.drawPath(path, paint)

    }

}