package com.example.stupatest

import android.graphics.Point
import android.view.View

object ViewExtension {
    fun View.show(){
        this.visibility = View.VISIBLE
    }

    fun View.hide(){
        this.visibility = View.GONE
    }

    fun getLocationOnScreen(): Point {
        val location = IntArray(2)
        this.getLocationOnScreen()
        return Point(location[0],location[1])
    }
}