package com.example.andoridlifecycle

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.edmodo.cropper.CropImageView
import com.example.andoridlifecycle.db.Image
import com.example.andoridlifecycle.db.ImageRepository
import kotlinx.android.synthetic.main.fragment1.*
import kotlin.concurrent.thread





class UploadImageFragment : Fragment() {

    public lateinit var image: CropImageView

    public var imageUri: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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

            var bitmap_image = getBitmap(requireContext(), null, imageUri, ::UriToBitmap)


            image.layoutParams = image.layoutParams.apply {

                width = bitmap_image.width
                height = bitmap_image.height
            }

            image.setImageBitmap(bitmap_image)
            image.background = BitmapDrawable(resources, bitmap_image)
        }



}