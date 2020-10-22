package com.example.stupatest

import android.content.pm.PackageManager
import android.hardware.Camera
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import com.example.stupatest.ViewExtension.hide
import com.example.stupatest.ViewExtension.show
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.abs

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
                MotionEvent.ACTION_DOWN -> {
                    if (starList.size < 2){
                        view.performClick()
                        val loc = IntArray(2)
                        view.getLocationOnScreen(loc)

                        val x = abs(abs(motionEvent.x.toInt() - loc[0]) - 50)
                        val y = abs(abs(motionEvent.y.toInt() - loc[1]) - 50)
                        Log.d(TAG, "onCreate: topBar Coordinates $x $y")
                        starList.add(StarData(x.toFloat(),y.toFloat()))

                        LiveDataHelper.getInstance()?.setStarCount(starList)
                        starCount++

                        return@setOnTouchListener true
                    }
                }
            }

            return@setOnTouchListener false
        }



        bottomBar.setOnTouchListener { view, motionEvent ->

            when(motionEvent.action){
                MotionEvent.ACTION_DOWN -> {

                    if (starList.size < 4){
                        view.performClick()
                        val loc = IntArray(2)
                        view.getLocationOnScreen(loc)

                        val x = abs(abs(motionEvent.x.toInt() - loc[0]) - 50)
                        val y = abs(abs(motionEvent.y.toInt() - loc[1]) - 50)
                        Log.d(TAG, "onCreate: topBar Coordinates $x $y")
                        starList.add(StarData(x.toFloat(),y.toFloat()))

                        LiveDataHelper.getInstance()?.setStarCount(starList)
                        starCount++

                        return@setOnTouchListener true
                    }
                }
            }

            return@setOnTouchListener false
        }

        btnUndo.setOnClickListener {
            if (starList.size>0){
                starCount--
                starList.removeAt(starList.size-1)
                LiveDataHelper.getInstance()?.setStarCount(starList)
            }
        }


    }

    private fun subscribeToIbservers() {
        LiveDataHelper.getInstance()?.starCount?.observe(this, Observer {
            Log.d(TAG, "subscribeToIbservers: starCount: ${starList.size} ${it.size} $it")
            frameStars.removeAllViews()
            it.forEach {
                val star = ImageView(this)
                star.setImageResource(R.drawable.ic_star_yellow)
                val scale = this.resources.displayMetrics.density
//                star.layoutParams = ViewGroup.LayoutParams(50,50)
                frameStars.addView(star)
                star.requestLayout()
                star.scaleType = ImageView.ScaleType.FIT_CENTER
                star.layoutParams.width = (50 * scale).toInt()
                star.layoutParams.height = (50 * scale).toInt()
                star.x = it.x
                star.y = it.y
              //  Log.d(TAG, "subscribeToIbservers: dims ${it.x} ${it.y} ${star.translationX} ${star.translationY} ")
            }
        })
    }

    private fun setupBars() {

        topBar.show()
        bottomBar.show()

        val reqMargin = this.resources.displayMetrics.heightPixels/4

        val topBarParams = topBar.layoutParams as RelativeLayout.LayoutParams
        topBarParams.topMargin = reqMargin

        val bottomBarParams = bottomBar.layoutParams as RelativeLayout.LayoutParams
        bottomBarParams.bottomMargin = reqMargin

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