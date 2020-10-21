package com.example.stupatest

import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import com.example.stupatest.ViewExtension.hide
import com.example.stupatest.ViewExtension.show
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mCamera: Camera? = null
    private var mPreview: CameraPreview? = null

    private val TAG = "MainActivityDebug"

    private var starCount = 0

    private var starList = mutableListOf<StarData>()

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

        subscribeToIbservers()


        topBar.setOnTouchListener { view, motionEvent ->
            when(motionEvent.action){
                MotionEvent.ACTION_UP -> {

                    Log.d(TAG, "onCreate: starCount: $starCount")

                    view.performClick()

                    val x =  motionEvent.x
                    val y =motionEvent.y

                    Log.d(TAG, "onCreate: topBar Coordinates $x $y")

                    starList.add(StarData(x,y))

                    LiveDataHelper.getInstance()?.setStarCount(starList)

                    starCount++

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

                    view.performClick()

                    val x =  motionEvent.x
                    val y =motionEvent.y
                    Log.d(TAG, "onCreate: topBar Coordinates $x $y")
                    starList.add(StarData(x,y))

                    LiveDataHelper.getInstance()?.setStarCount(starList)
                    starCount++

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

        btnUndo.setOnClickListener {
            if (starCount>0){
                starCount--
                starList.removeAt(starList.size-1)
                LiveDataHelper.getInstance()?.setStarCount(starList)
            }
        }


    }

    private fun subscribeToIbservers() {
        LiveDataHelper.getInstance()?.starCount?.observe(this, Observer {
            Log.d(TAG, "subscribeToIbservers: starCount: $starCount")
            starList.forEach {
//                val star = ImageView(this)
//                star.setImageResource(R.drawable.ic_star_yellow)
                starFour.show()
                starFour.x = it.x
                starFour.y = it.y
            }
        })
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