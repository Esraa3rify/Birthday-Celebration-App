package com.example.remindname.ui.fragments.ageFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.remindname.ui.activities.MainActivity
import com.example.remindname.R
import com.example.remindname.viewmodels.FaceRecognitionViewModel
import kotlinx.android.synthetic.main.fragment_age.*


class AgeFragment : Fragment(){
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
        return inflater.inflate(R.layout.fragment_age, container, false)
    }
//
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        mainActivity = activity as MainActivity
//        val imagePath = arguments?.getString("image_path")
//        imagePath?.let {image->
//            ageProceedBtn.setOnClickListener {
//                mainActivity.hideKeyboard()
//                val age = etVisitorAge.text.toString()
//
//              //  gotoValidateFragment(image, age)
//                mainActivity.changeFragment(ValidateAgeFragment())
//            }
//        }
//
////        et_user_fullname.addTextChangedListener {
////            it?.let { editable->
////               proceedBtn.isEnabled = editable.isNotBlank()
////            }
////        }
//     age_constraint_layout.setOnClickListener {
//            mainActivity.hideKeyboard()
//        }
//
//    }
//

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity = activity as MainActivity


        // Setting On Click Listener
        ageProceedBtn.setOnClickListener {

            // Getting the user input
            val text = etVisitorAge.text.toString()

            val bundle=Bundle()
            bundle.putString("data",text)

            val fragment= ValidateAgeFragment()
            fragment.arguments=bundle


            fragmentManager?.beginTransaction()?.replace(R.id.fragment_container,fragment)?.commit()



            age_constraint_layout.setOnClickListener {
             //   mainActivity.hideKeyboard()
            }
        }

    }

//
//    private fun gotoValidateFragment(imagePath: String, age: String)
//    {
//        mainActivity = activity as MainActivity
//        val ValidateAgeFragment = ValidateAgeFragment()
//        mainActivity.changeFragment(ValidateAgeFragment,
//            Bundle().apply {
//                putString("image_path",imagePath)
//                putString("age",age)
//            })
//    }
}