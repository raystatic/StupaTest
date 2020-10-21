package com.example.stupatest

import android.content.Context
import android.hardware.Camera
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.lang.Exception

class CameraPreview(
    context: Context,
    private val mCamera:Camera
):SurfaceView(context), SurfaceHolder.Callback{

    private val TAG = "CameraPreviewDebug"

    private val mHolder:SurfaceHolder = holder.apply {
        addCallback(this@CameraPreview)
        setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        if (mHolder.surface == null) {
            return
        }

        try {
            mCamera.stopPreview()
        } catch (e: Exception) {

        }

        mCamera.apply {
            try {
                setPreviewDisplay(mHolder)
                startPreview()
            } catch (e: Exception) {
                Log.d(TAG, "Error starting camera preview: ${e.message}")
            }
        }

    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {

    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        mCamera.apply {
            try {
                setPreviewDisplay(p0)
                startPreview()
            }catch (e:Exception){
                Log.d(TAG, "surfaceChanged: Error setting camera preview: ${e.message}")
            }
        }
    }
}