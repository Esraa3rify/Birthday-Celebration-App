package com.example.remindname.repo

import com.example.remindname.model.AddNewFaceResponseModel
import com.example.remindname.model.FindFaceResponseModel
import com.example.remindname.networking.Networking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File


class FaceRecognitionRepo {

    companion object {
        private const val IMAGE_MULTIPART_KEY = "image"
    }
    suspend fun findFace(capturedFile: File): Response<FindFaceResponseModel>
    {
        val requestFile: RequestBody = capturedFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val body: MultipartBody.Part = MultipartBody.Part.createFormData(IMAGE_MULTIPART_KEY, capturedFile.name, requestFile)
        return Networking.getFaceRecognitionService().findFace(body)
    }

    suspend fun addNewFace(capturedFile: File, faceName:String): Response<AddNewFaceResponseModel>
    {
        val requestFile: RequestBody = capturedFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val body: MultipartBody.Part = MultipartBody.Part.createFormData(IMAGE_MULTIPART_KEY, capturedFile.name, requestFile)
        return Networking.getFaceRecognitionService().addFace(faceName,body)
    }
}