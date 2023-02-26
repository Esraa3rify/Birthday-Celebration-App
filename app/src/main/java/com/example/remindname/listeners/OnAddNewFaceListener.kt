package com.example.remindname.listeners

import com.example.remindname.model.AddNewFaceResponseModel

interface OnAddNewFaceListener {
    fun onAddNewFaceSuccess(addNewFaceResponseModel: AddNewFaceResponseModel)
    fun onAddNewFaceFailed(msg:String)
}