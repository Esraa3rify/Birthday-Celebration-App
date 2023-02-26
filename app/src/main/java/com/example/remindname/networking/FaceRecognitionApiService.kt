package com.example.remindname.networking

import com.example.remindname.model.AddNewFaceResponseModel
import com.example.remindname.model.FindFaceResponseModel
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface FaceRecognitionApiService {

    @Multipart
    @POST(EndPoint.addFace)
    suspend fun addFace(
        @Query("face_name") post: String,
        @Part image: MultipartBody.Part
    ): Response<AddNewFaceResponseModel>

    @Multipart
    @POST(EndPoint.findFace)
    suspend fun findFace(
        @Part recognisedFace : MultipartBody.Part,
    ) : Response<FindFaceResponseModel>

}