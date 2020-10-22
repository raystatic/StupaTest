package com.example.stupatest.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.stupatest.other.LiveDataHelper
import com.example.stupatest.R
import com.example.stupatest.models.ShapeCoords
import com.example.stupatest.models.StarData
import com.example.stupatest.other.ViewExtension.show
import com.example.stupatest.viewmodels.ShapeViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.abs

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var mCamera: Camera? = null
    private var mPreview: CameraPreview? = null

    private val TAG = "MainActivityDebug"

    private var starCount = 0

    private var starList = mutableListOf<StarData>()

    private val vm:ShapeViewModel by viewModels()

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
                        starList.add(
                            StarData(
                                x.toFloat(),
                                y.toFloat()
                            )
                        )

                        LiveDataHelper.getInstance()
                            ?.setStarCount(starList)
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
                        starList.add(
                            StarData(
                                x.toFloat(),
                                y.toFloat()
                            )
                        )

                        LiveDataHelper.getInstance()
                            ?.setStarCount(starList)
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

        btnSignOut.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

            val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

            mGoogleSignInClient.signOut()
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }

        btnOk.setOnClickListener {

            if (starList.size == 4){
                val shapes = mutableListOf<StarData>()
                starList.forEach {
                    shapes.add(it)
                }

                vm.insertShape(ShapeCoords(shapes))

                startActivity(Intent(this,ChartActivity::class.java))
                finish()

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