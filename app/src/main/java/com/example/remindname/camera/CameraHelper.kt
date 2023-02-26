package com.example.remindname.camera

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager


import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import com.example.remindname.R
import java.util.concurrent.Executors


class CameraHelper(private val activity: Activity,private val lifecycleOwner: LifecycleOwner) {
    private lateinit var outputDirectory: File
    private var imageCapture: ImageCapture? = null
    private val cameraExecutor = Executors.newSingleThreadExecutor()
    private var imagePreview: Preview? = null
    private  val IMAGE_FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS"
    private  val PHOTO_EXTENSION = ".jpg"
    private  val REQUEST_CODE_PERMISSIONS = 10
    private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    private var mImageSavedCallback:ImageCapture.OnImageSavedCallback?=null
    lateinit var currentSavedFile:File
    private lateinit var  cameraProviderFuture:ListenableFuture<ProcessCameraProvider>
    fun setImageSaveCallback(imageSavedCallback: ImageCapture.OnImageSavedCallback)
    {
        mImageSavedCallback=imageSavedCallback;
    }
    private fun createFile() =
        File(
            outputDirectory, SimpleDateFormat(IMAGE_FILENAME, Locale.US)
                .format(System.currentTimeMillis()) + PHOTO_EXTENSION
        )

    fun initStartCamera(targetDisplayRotation: Int,surfaceProvider: Preview.SurfaceProvider) {
        outputDirectory = getOutputDirectory()
        if (allPermissionsGranted()) {
            startCamera(targetDisplayRotation,surfaceProvider)
        } else {
            activity.requestPermissions(
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(activity, it) == PackageManager.PERMISSION_GRANTED
    }

    fun unbindAll()
    {
        if(this::cameraProviderFuture.isInitialized) {
            cameraProviderFuture.get().unbindAll()
        }
    }
    private fun startCamera(targetDisplayRotation: Int,surfaceProvider: Preview.SurfaceProvider) {

        cameraProviderFuture =  ProcessCameraProvider.getInstance(activity)
        val cameraSelector = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_FRONT).build()
        cameraProviderFuture.addListener({
            imagePreview = Preview.Builder().apply {
                setTargetAspectRatio(AspectRatio.RATIO_16_9)
                setTargetRotation(targetDisplayRotation)
            }.build()

            imageCapture = ImageCapture.Builder().apply {
                setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                setFlashMode(ImageCapture.FLASH_MODE_AUTO)
            }.build()
            val cameraProvider = cameraProviderFuture.get()
            val camera = cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, imagePreview,imageCapture)
            imagePreview?.setSurfaceProvider(surfaceProvider)
        }, ContextCompat.getMainExecutor(activity))
    }

    fun takePicture() {
        currentSavedFile = createFile()
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(currentSavedFile).build()
        mImageSavedCallback?.let {
            imageCapture?.takePicture(outputFileOptions, cameraExecutor, it)
        }
    }
    /*    private fun Image.toBitmap(rotationDegrees: Int): Bitmap {
            val buffer = planes[0].buffer
            val bytes = ByteArray(buffer.remaining())
            buffer.get(bytes)
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size, null)
            val matrix = Matrix()
            matrix.postRotate(rotationDegrees.toFloat())
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        }*/
    private fun getOutputDirectory(): File {
        val mediaDir = activity.externalMediaDirs.firstOrNull()?.let {
            File(it, activity.resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else activity.filesDir
    }
}