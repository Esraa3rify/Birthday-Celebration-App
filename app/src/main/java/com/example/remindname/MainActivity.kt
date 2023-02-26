package com.example.remindname

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

import com.example.remindname.camera.CameraHelper
import com.example.remindname.fragments.FirstFragment
import com.example.remindname.fragments.RecognitionFragment

class MainActivity : AppCompatActivity() {

    var cameraHelper: CameraHelper?=null
    val cameraTimerHandler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forcedActivityFullScreen()
        setContentView(R.layout.activity_main)
        cameraHelper = CameraHelper(this, this)
        changeFragment(FirstFragment())
    }

    private fun forcedActivityFullScreen()
    {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }
    fun changeFragment(fragment: Fragment,args:Bundle?=null)
    {
        fragment.arguments = args
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container,fragment)
            .addToBackStack(null)
            .commit()
    }
    fun hideKeyboard()
    {
        currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
        forcedActivityFullScreen()
    }
    fun destroyAllTimerHandlers()
    {
        cameraTimerHandler.removeCallbacksAndMessages(null)
    }
}