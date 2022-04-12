package com.example.andoridlifecycle

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.androidnetworking.interfaces.StringRequestListener
import com.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.item_custom_row.*
import kotlinx.android.synthetic.main.item_custom_row.view.*
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class Fragment1 : Fragment() {

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


            // uploadImage(createFileFromBitmap(bitmap_image))

            image.layoutParams = image.layoutParams.apply {

                width = bitmap_image.width
                height = bitmap_image.height
            }

            image.setImageBitmap(bitmap_image)
            image.background = BitmapDrawable(resources, bitmap_image)
        }





}