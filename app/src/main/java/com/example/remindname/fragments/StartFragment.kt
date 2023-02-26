package com.example.remindname.fragments



import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.icu.number.NumberFormatter.with
import android.media.ThumbnailUtils
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast

import com.example.remindname.MainActivity
import com.example.remindname.R
import com.example.remindname.ml.ModelUnquant
import kotlinx.android.synthetic.main.fragment_start.*
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder


class StartFragment : Fragment() {

    var imageView: ImageView? = null
    // lateinit var result:TextView
    var imageSize = 224
    private val Image_Capture_Code = 3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity = activity as MainActivity



        text.setOnClickListener(View.OnClickListener {
            val cInt = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cInt, Image_Capture_Code)
        })


        strt2_btn.setOnClickListener(View.OnClickListener {

            val mainActivity = activity as MainActivity
mainActivity.changeFragment(BlowOutFragment())
        })

        change_data_btn.setOnClickListener(View.OnClickListener {

            val mainActivity = activity as MainActivity
            mainActivity.changeFragment(EnterNameFragment())

        })
    }



    fun classifyImage(image: Bitmap) {
        try {
            val model = ModelUnquant.newInstance(requireContext())
            // Creates inputs for reference.
            Log.i(ContentValues.TAG,"result5")

            val inputFeature0 =
                TensorBuffer.createFixedSize(intArrayOf(1,224, 224, 3), DataType.FLOAT32)
            val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
            Log.i(ContentValues.TAG,"result4")

            byteBuffer.order(ByteOrder.nativeOrder())
            val intValues = IntArray(imageSize * imageSize)
            image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
            var pixel = 0
            Log.i(ContentValues.TAG,"result3")

            //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
            for (i in 0 until imageSize) {
                for (j in 0 until imageSize) {
                    val `val` = intValues[pixel++] // RGB
                    byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 1))
                    byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 1))
                    byteBuffer.putFloat((`val` and 0xFF) * (1f / 1))
                }
            }
            Log.i(ContentValues.TAG,"result2")

            inputFeature0.loadBuffer(byteBuffer)
            // Runs model inference and gets result.
            val outputs:  ModelUnquant.Outputs = model.process(inputFeature0)
            val outputFeature0: TensorBuffer = outputs.getOutputFeature0AsTensorBuffer()
            val confidences = outputFeature0.floatArray
            Log.i(ContentValues.TAG,"result1")

            // find the index of the class with the biggest confidence.
            var maxPos = 0
            var maxConfidence = 0f
            for (i in confidences.indices) {
                Log.i(ContentValues.TAG,"res")
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i]
                    maxPos = i
                }
            }
            val classes = arrayOf("Class 1", "Class 2")

            textView.text = classes[maxPos]

            if(maxPos==0){

                val mainActivity = activity as MainActivity
                mainActivity.changeFragment(BlowOutFragment())            }

            Log.i(ContentValues.TAG,"result ${textView.text}")
            // Releases model resources if no longer used.
            model.close()
        } catch (e: Exception) {

            Toast.makeText(requireContext(), "classifyImage: ${e.message}", Toast.LENGTH_SHORT).show()

            e.printStackTrace()
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {


        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 3) {

                var image = data!!.extras!!["data"] as Bitmap?
                val dimension = Math.min(image!!.width, image.height)
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
                imageView?.setImageBitmap(image)
                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)
                classifyImage(image)

            } else {
                val dat = data!!.data

                var image: Bitmap? = null
                try {
                    image =
                        MediaStore.Images.Media.getBitmap(activity?.contentResolver, dat)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                imageView?.setImageBitmap(image)
                image = Bitmap.createScaledBitmap(image!!, imageSize, imageSize, false)
                classifyImage(image)


            }
            super.onActivityResult(requestCode, resultCode, data)
        }

    }
}