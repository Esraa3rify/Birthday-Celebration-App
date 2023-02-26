package com.example.remindname.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.lifecycle.ViewModelProvider
import com.example.remindname.MainActivity
import com.example.remindname.R
import com.example.remindname.listeners.OnFindFaceListener
import com.example.remindname.model.FindFaceResponseModel
import com.example.remindname.viewmodels.FaceRecognitionViewModel
import kotlinx.android.synthetic.main.fragment_recognition.*

import java.io.File





class RecognitionFragment : Fragment(), ImageCapture.OnImageSavedCallback, OnFindFaceListener{
    private lateinit var vm: FaceRecognitionViewModel
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recognition, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mainActivity.cameraHelper?.unbindAll()
        mainActivity.destroyAllTimerHandlers();
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainActivity = activity as MainActivity
        vm = ViewModelProvider(this)[FaceRecognitionViewModel::class.java]
        mainActivity.cameraHelper?.let {
            with(it)
            {
                setImageSaveCallback(this@RecognitionFragment)
                initStartCamera(
                    searching_face_preview_view.display.rotation,
                    searching_face_preview_view.surfaceProvider
                )
         mainActivity.cameraTimerHandler.postDelayed({takePicture()},1000)
            }
        }
    }
    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
        val file = mainActivity.cameraHelper?.currentSavedFile
        val msg = "Photo capture succeeded: ${file?.absolutePath}"
        file?.let {
            findFace(it)
        }?:Toast.makeText(mainActivity, "Photo not saved!", Toast.LENGTH_LONG).show()

        searching_face_preview_view.post {
            Toast.makeText(mainActivity, msg, Toast.LENGTH_LONG).show()
        }
    }

    override fun onError(exception: ImageCaptureException) {
        val msg = "Photo capture failed: ${exception.message}"
        searching_face_preview_view.post {
            Toast.makeText(mainActivity, msg, Toast.LENGTH_LONG).show()
        }
    }

    private fun findFace(file: File)
    {
        mainActivity.runOnUiThread {
            vm.findFace(file,this)
            vm.findFaceProgressLiveData().observe(this) { showProgress ->
                progress_bar.visibility = showProgress
            }
        }
    }

    override fun onFaceFoundSuccess(findFaceResponseModel: FindFaceResponseModel) {
        val fragment =  EnterNameFragment()
        mainActivity.changeFragment(fragment,
            Bundle().apply {
                putSerializable("face_data",findFaceResponseModel)
                putString("image_path",mainActivity.cameraHelper?.currentSavedFile?.absolutePath)
            }
        )
    }

    override fun onFaceNotFailed(msg:String) {

        mainActivity.changeFragment(BlowOutFragment())

    }

}