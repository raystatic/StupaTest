package com.example.stupatest

import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mCamera: Camera? = null
    private var mPreview: CameraPreview? = null

    private val TAG = "MainActivityDebug"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!isCameraSupported()){
            Toast.makeText(this, "Camera not supported.. Exiting application", Toast.LENGTH_SHORT).show()
        }

        mCamera = getCameraInstance()

        mPreview = mCamera?.let {
            CameraPreview(this, it)
        }

        mPreview?.also {
            camera_preview.addView(it)
        }

        setupBars()

        topBar.setOnTouchListener { view, motionEvent ->
            when(motionEvent.action){
                MotionEvent.ACTION_UP -> {

                    view.performClick()

                    val x =  motionEvent.x.toInt()
                    val y =motionEvent.y.toInt()

                    Log.d(TAG, "onCreate: topBar Coordinates $x $y")

                    return@setOnTouchListener true
                }

                MotionEvent.ACTION_DOWN -> {

                    view.performClick()

                    val x =  motionEvent.x.toInt()
                    val y =motionEvent.y.toInt()

                    Log.d(TAG, "onCreate: down topBar Coordinates $x $y")

                    return@setOnTouchListener true
                }
            }

            return@setOnTouchListener false
        }


    }

    private fun setupBars() {

        topBar.visibility = View.VISIBLE
        bottomBar.visibility = View.VISIBLE

        val reqMargin = this.resources.displayMetrics.heightPixels/4

        val topBarParams = topBar.layoutParams as ConstraintLayout.LayoutParams
        topBarParams.setMargins(0,reqMargin,0,0)

        val bottomBarParams = bottomBar.layoutParams as ConstraintLayout.LayoutParams
        bottomBarParams.setMargins(0,0,0, reqMargin)

        topBar.layoutParams = topBarParams

        bottomBar.layoutParams = bottomBarParams

    }


    private fun isCameraSupported():Boolean = this.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)

    private fun getCameraInstance(): Camera? {
        return try {
            Camera.open()
        } catch (e: Exception) {
            null
        }
    }

}