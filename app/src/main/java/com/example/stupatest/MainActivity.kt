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
import com.example.stupatest.ViewExtension.hide
import com.example.stupatest.ViewExtension.show
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mCamera: Camera? = null
    private var mPreview: CameraPreview? = null

    private val TAG = "MainActivityDebug"

    private var starCount = 0

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

                    Log.d(TAG, "onCreate: starCount: $starCount")

                    if (starCount in 0..1){
                        view.performClick()

                        val x =  motionEvent.x
                        val y =motionEvent.y

                        Log.d(TAG, "onCreate: topBar Coordinates $x $y")

                        when(starCount){
                            0 -> {
                                starOne.show()
                                starOne.x = x
                                starOne.y = y
                                starCount = 1
                                starTwo.hide()
                                starThree.hide()
                                starFour.hide()
                            }

                            1 -> {
                                starTwo.show()
                                starTwo.x = x
                                starTwo.y = y
                                starCount = 2
                                starThree.hide()
                                starFour.hide()
                            }
                        }
                    }

                    return@setOnTouchListener true
                }
                MotionEvent.ACTION_DOWN -> {
                    if (starCount in 0..1)
                        view.performClick()
                    return@setOnTouchListener true
                }
            }

            return@setOnTouchListener false
        }

        bottomBar.setOnTouchListener { view, motionEvent ->
            when(motionEvent.action){
                MotionEvent.ACTION_UP -> {

                    if (starCount in 2..3){
                        view.performClick()

                        val x =  motionEvent.x
                        val y =motionEvent.y

                        Log.d(TAG, "onCreate: topBar Coordinates $x $y")

                        when(starCount){

                            2 -> {
                                starThree.show()
                                starThree.x = x
                                starThree.y = y
                                starCount = 3
                                starFour.hide()
                            }

                            3 -> {
                                starFour.show()
                                starFour.x = x
                                starFour.y = y
                                starCount = 4
                            }
                        }
                    }

                    return@setOnTouchListener true
                }
                MotionEvent.ACTION_DOWN -> {
                    if (starCount in 2..3)
                        view.performClick()
                    return@setOnTouchListener true
                }
            }

            return@setOnTouchListener false
        }


    }

    private fun setupBars() {

        topBar.show()
        bottomBar.show()

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

    override fun onResume() {
        super.onResume()
        starCount = 0
    }

}