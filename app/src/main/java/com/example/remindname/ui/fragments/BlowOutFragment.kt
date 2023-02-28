package com.example.remindname.ui.fragments

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.remindname.ui.activities.MainActivity
import com.example.remindname.R
import kotlinx.android.synthetic.main.fragment_blow_out.*


class BlowOutFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blow_out, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity = activity as MainActivity

        val music: MediaPlayer = MediaPlayer.create(activity, R.raw.birth)



        music.start()

        btn_blow_out.setOnClickListener {
            mainActivity.changeFragment(ThankYouFragment())
        }




    }
}