package com.example.remindname.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FindFaceResponseModel(

    @SerializedName("success")
    var success : String? = null,

    @SerializedName("status")
    var status : String? = null,

    @SerializedName("face_id")
    var faceId : String? = null,

    @SerializedName("face_name")
    var faceName : String? = null,

    @SerializedName("detection_confidence")
    var detectionConfidence : Double? = null,

    @SerializedName("recognition_confidence_CosSim" )
    var recognitionConfidenceCosSim : Double? = null,

    var localFilePath:String?=null

): Serializable