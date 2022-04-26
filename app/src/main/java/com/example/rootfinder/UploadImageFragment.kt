package com.example.rootfinder

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.edmodo.cropper.CropImageView

class UploadImageFragment : Fragment() {
    lateinit var image: CropImageView
    var imageUri: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment1, container, false)

        image = view.findViewById<CropImageView>(R.id.image)
        image.setOnClickListener {
            var i = Intent()

            i.action = Intent.ACTION_GET_CONTENT
            i.type = "*/*"

            startForResult.launch(i)
        }

        return view
    }

    var startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

            imageUri = it.data?.data.toString()
            println("uri $imageUri")

            val bitmap_image = getBitmap(requireContext(), null, imageUri, ::UriToBitmap)

            image.layoutParams = image.layoutParams.apply {
                width = MATCH_PARENT
                height = MATCH_PARENT
            }

            image.setImageBitmap(bitmap_image)
            image.background = null
        }

}