package com.example.remindname.camera

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.MultiProcessor
import com.google.android.gms.vision.face.FaceDetector
import java.io.IOException

class FaceCameraIntegrator(
    private val context: Context,
    private val onEyeBlink: OnEyeBlink
) {

    init {
        initCameraSource()
    }

    lateinit var cameraSource: CameraSource
    private fun initCameraSource() {
        val detector = FaceDetector.Builder(context)
            .setTrackingEnabled(true)
            .setClassificationType(FaceDetector.ACCURATE_MODE)
            .setMode(FaceDetector.FAST_MODE)
            .build()
        val faceTracker = FaceTrackerDaemon(context)
        faceTracker.addListener(onEyeBlink)
        detector.setProcessor(
            MultiProcessor.Builder(faceTracker).build()
        )

        cameraSource = CameraSource.Builder(context, detector)
            .setRequestedPreviewSize(1024, 768)
            .setFacing(CameraSource.CAMERA_FACING_FRONT)
            .setRequestedFps(30.0f)
            .build()
        try {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            cameraSource.start()
        } catch (e: IOException) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

}