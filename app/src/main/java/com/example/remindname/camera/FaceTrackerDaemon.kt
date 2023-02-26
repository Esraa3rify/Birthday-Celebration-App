package com.example.remindname.camera

import android.content.Context
import com.google.android.gms.vision.MultiProcessor
import com.google.android.gms.vision.Tracker
import com.google.android.gms.vision.face.Face



class FaceTrackerDaemon(val context: Context) :
    MultiProcessor.Factory<Face> {
    private val eyeTracker = EyesTracker()
    override fun create(face: Face): Tracker<Face> {
        return eyeTracker
    }

    fun addListener(onEyeBlink: OnEyeBlink) {
        eyeTracker.addBlinkListener(onEyeBlink)
    }


}