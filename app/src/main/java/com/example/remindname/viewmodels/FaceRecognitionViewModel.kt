package com.example.remindname.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remindname.fragments.ValidateNameFragment
import com.example.remindname.listeners.OnFindFaceListener
import com.example.remindname.repo.FaceRecognitionRepo
import kotlinx.coroutines.launch
import java.io.File


class FaceRecognitionViewModel: ViewModel() {
    private lateinit var findFaceProgressLiveData: MutableLiveData<Int>
    private lateinit var addNewFaceProgressLiveData: MutableLiveData<Int>

    private val faceRecognitionRepo = FaceRecognitionRepo()

    fun findFaceProgressLiveData(): LiveData<Int> = findFaceProgressLiveData
    fun addNewFaceProgressLiveData(): LiveData<Int> = addNewFaceProgressLiveData

    fun findFace(capturedFile: File, onFindFaceListener: OnFindFaceListener)
    {
        findFaceProgressLiveData = MutableLiveData(View.VISIBLE)
        viewModelScope.launch {
            val response = faceRecognitionRepo.findFace(capturedFile)
            val findFaceResponseModel = response.body()
            findFaceProgressLiveData.postValue(View.GONE)
            if(findFaceResponseModel!=null) {
                findFaceResponseModel.localFilePath = capturedFile.absolutePath
                if(findFaceResponseModel.faceName!=null) {
                    onFindFaceListener.onFaceFoundSuccess(findFaceResponseModel)
                }else{
                    onFindFaceListener.onFaceNotFailed(response.message())
                }
            }
        }
    }
//
    fun addNewFace(capturedFile: File, faceName:String, onAddNewFaceListener: ValidateNameFragment)
    {
        addNewFaceProgressLiveData = MutableLiveData(View.VISIBLE)
        viewModelScope.launch {
            val response = faceRecognitionRepo.addNewFace(capturedFile,faceName)
            val addNewFaceResponseModel = response.body()
            addNewFaceProgressLiveData.postValue(View.GONE)
            if(addNewFaceResponseModel!=null) {
                addNewFaceResponseModel.localFilePath = capturedFile.absolutePath
                if(addNewFaceResponseModel.faceName!=null) {
                    onAddNewFaceListener.onAddNewFaceSuccess(addNewFaceResponseModel)
                }else{
                    onAddNewFaceListener.onAddNewFaceFailed(response.message())
                }
            }
        }
    }

}