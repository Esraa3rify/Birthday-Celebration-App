package com.example.remindname.camera

import android.util.Log
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.Tracker
import com.google.android.gms.vision.face.Face
import java.lang.ref.WeakReference

class EyesTracker :
    Tracker<Face>() {

    private val THRESHOLD = 0.75f

    private var blinkListener: WeakReference<OnEyeBlink> = WeakReference(null)
    private val statusTracker = StatusTracker {
        Log.d("TAG", "onBlinkDetected: ")

        blinkListener.get()?.onEyeBlink()
    }

    fun addBlinkListener(eyeBlink: OnEyeBlink) {
        blinkListener = WeakReference(eyeBlink)
    }


    override fun onUpdate(detections: Detector.Detections<Face>, face: Face) {
        if (face.isLeftEyeOpenProbability > THRESHOLD || face.isRightEyeOpenProbability > THRESHOLD) {
            statusTracker.onStatusChange(EyeState.OPEN)
//            Log.i(Constraints.TAG, "onUpdate: Open Eyes Detected")
        } else {
            statusTracker.onStatusChange(EyeState.CLOSED)
//            Log.i(Constraints.TAG, "onUpdate: Close Eyes Detected")
        }
    }

    override fun onMissing(detections: Detector.Detections<Face>) {
        super.onMissing(detections)
        statusTracker.onStatusChange(EyeState.NOT_FOUND)
//        Log.i(Constraints.TAG, "onUpdate: Face Not Detected!")
    }

    override fun onDone() {
        super.onDone()
    }

}