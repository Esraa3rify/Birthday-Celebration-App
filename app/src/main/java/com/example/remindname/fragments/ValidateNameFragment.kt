package com.example.remindname.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.remindname.MainActivity
import com.example.remindname.R
import com.example.remindname.listeners.OnAddNewFaceListener
import com.example.remindname.listeners.OnFindFaceListener
import com.example.remindname.model.AddNewFaceResponseModel
import com.example.remindname.model.FindFaceResponseModel
import com.example.remindname.viewmodels.FaceRecognitionViewModel
import kotlinx.android.synthetic.main.fragment_enter_name.*
import kotlinx.android.synthetic.main.fragment_validate_name.*
import java.io.File


class ValidateNameFragment : Fragment() ,OnAddNewFaceListener {
    private lateinit var mainActivity: MainActivity
    private lateinit var vm: FaceRecognitionViewModel
    private var imagePath:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_validate_name, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainActivity = activity as MainActivity
        vm = ViewModelProvider(this)[FaceRecognitionViewModel::class.java]

        val textargs2: TextView =requireView().findViewById(R.id.text)
        val args=this.arguments
        val inputData=args?.get("full_name")
        textargs2.text=inputData.toString()

        yes_btn.setOnClickListener {
            mainActivity.hideKeyboard()
            arguments?.let {
              val imgPath = it.getString("image_path")!!
                val fullName = it.getString("full_name")!!
                this.imagePath = imgPath
                vm.addNewFace(File(imgPath),fullName,this)
              vm.addNewFaceProgressLiveData().observe(viewLifecycleOwner){

                //  mainActivity.changeFragment(AgeFragment())

              }

              //{
//                progress->
//                 progress_bar.visibility = progress
//
//
//            }
            }




        }


      validate_name_constraint_layout.setOnClickListener {
            mainActivity.hideKeyboard()
        }
    }

    override fun onAddNewFaceSuccess(addNewFaceResponseModel: AddNewFaceResponseModel) {
        mainActivity.changeFragment(AgeFragment())
    }

    override fun onAddNewFaceFailed(msg: String) {
      //  progress_bar.visibility = View.GONE
//        val fragment = AgeFragment()
//        mainActivity.changeFragment(fragment,Bundle().apply {
//           // putSerializable("face_data",addNewFaceResponseModel)
//            putString("image_path",imagePath)

        mainActivity.changeFragment(AgeFragment())

      //  })
    }



}