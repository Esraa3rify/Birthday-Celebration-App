package com.example.remindname.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class AddNewFaceResponseModel(
    @SerializedName("success")
    var success : String?  = null,

    @SerializedName("status")
    var status : String?  = null,

    @SerializedName("face_id")
    var faceId : String?  = null,

    @SerializedName("face_name")
    var faceName : String?  = null,

    @SerializedName("detection_confidence")
    var detectionConfidence : Double?  = null,

    @SerializedName("bboxes")
    var bboxes : ArrayList<ArrayList<Int>> = arrayListOf(),

    @SerializedName("saved_image_file")
    var savedImageFile : Boolean?  = null,

    var localFilePath:String?=null

): Serializable