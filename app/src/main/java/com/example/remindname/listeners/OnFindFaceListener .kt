package com.example.remindname.listeners

import com.example.remindname.model.FindFaceResponseModel


interface OnFindFaceListener {
    fun onFaceFoundSuccess(findFaceResponseModel: FindFaceResponseModel)
    fun onFaceNotFailed(msg:String)
}