package com.example.stupatest

import android.view.View

object ViewExtension {
    fun View.show(){
        this.visibility = View.VISIBLE
    }

    fun View.hide(){
        this.visibility = View.GONE
    }
}