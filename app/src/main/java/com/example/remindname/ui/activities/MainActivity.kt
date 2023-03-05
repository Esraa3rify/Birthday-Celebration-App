package com.example.remindname.ui.activities

import android.content.Context

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.remindname.R
import com.example.remindname.camera.CameraHelper
import com.example.remindname.ui.fragments.FirstFragment
import com.robotemi.sdk.Robot
import com.robotemi.sdk.listeners.OnRobotReadyListener


class MainActivity : AppCompatActivity(),OnRobotReadyListener {

 private lateinit var sRobot: Robot

    var cameraHelper: CameraHelper?=null
    val cameraTimerHandler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       //forcedActivityFullScreen()
        setContentView(R.layout.activity_main)
        cameraHelper = CameraHelper(this, this)

        // Initialize robot instance
        sRobot = Robot.getInstance();
       // cameraHelper = CameraHelper(this, this)
        changeFragment(FirstFragment())
    }


    override fun onStart() {
        super.onStart()
        sRobot!!.addOnRobotReadyListener(this);

        // Add robot event listeners
       // Robot.getInstance().addOnRobotReadyListener(this);
    }
//
    override fun onStop() {
        super.onStop()

        // Remove robot event listeners
        sRobot!!.removeOnRobotReadyListener(this);
    }
//
//    private fun forcedActivityFullScreen()
//    {
//        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                or View.SYSTEM_UI_FLAG_FULLSCREEN
//                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
//    }

    fun changeFragment(fragment: Fragment,args:Bundle?=null)
    {
        fragment.arguments = args
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container,fragment)
            .addToBackStack(null)
            .commit()
    }


    override fun onRobotReady(isReady: Boolean) {
        if (isReady) {

            sRobot.hideTopBar() // hide temi's top action bar when skill is active

            // When robot is ready
//            val ttsRequest = TtsRequest.create("Hello World, that is Celebration message", false)
//            sRobot.speak(ttsRequest)
        }
    }
//    fun hideKeyboard()
//    {
//        currentFocus?.let { view ->
//            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
//            imm?.hideSoftInputFromWindow(view.windowToken, 0)
//        }
//       forcedActivityFullScreen()
//    }


    fun destroyAllTimerHandlers()
    {
        cameraTimerHandler.removeCallbacksAndMessages(null)
    }
}