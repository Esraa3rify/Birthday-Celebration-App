package com.example.remindname.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.remindname.MainActivity
import com.example.remindname.R
import kotlinx.android.synthetic.main.fragment_enter_name.*

class EnterNameFragment : Fragment() {
    private lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enter_name, container, false)
    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        mainActivity = activity as MainActivity
//        val imagePath = arguments?.getString("image_path")
//        imagePath?.let {image->
//            proceedBtn.setOnClickListener {
//                mainActivity.hideKeyboard()
//                val fullName = etVisitorName.text.toString()
//
//                gotoValidateFragment(image, fullName)
//            }
//        }
//
////        et_user_fullname.addTextChangedListener {
////            it?.let { editable->
////               proceedBtn.isEnabled = editable.isNotBlank()
////            }
////        }
//      enter_name_constraint_layout.setOnClickListener {
//            mainActivity.hideKeyboard()
//        }
//
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity = activity as MainActivity

//        // Getting the user input
//        val text = etVisitorName.text.toString()
//
//        val bundle=Bundle()
//        bundle.putString("data",text)
//
//        val fragment=Validate_NameFragment()
//        fragment.arguments=bundle
//        // Setting On Click Listener

        mainActivity.hideKeyboard()
        val imagePath = arguments?.getString("image_path")
        imagePath?.let { image ->

            proceedBtn.setOnClickListener {

                mainActivity.hideKeyboard()
                val fullName = etVisitorName.text.toString()

                gotoValidateFragment(image, fullName)



            }
        }
        enter_name_constraint_layout.setOnClickListener {
            mainActivity.hideKeyboard()
        }
    }

    private fun gotoValidateFragment(imagePath: String, fullName: String)
    {
        mainActivity = activity as MainActivity
        val ValidateNameFragment = ValidateNameFragment()
        mainActivity.changeFragment(ValidateNameFragment,
            Bundle().apply {
                putString("image_path",imagePath)
                putString("full_name",fullName)
            })
    }
    }
