package com.example.andoridlifecycle

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.androidnetworking.AndroidNetworking

import androidx.fragment.app.FragmentManager
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {

    private lateinit var url: String;
    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidNetworking.initialize(applicationContext)


    } // onCreate ends


    fun switchFragment(v: View) {
        fragmentManager = supportFragmentManager

        if (Integer.parseInt(v.getTag().toString()) == 1) {
            fragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_main,
                    UploadImageFragment(),
                    "Fragment1"
                )
                .commit()
        } else if (Integer.parseInt(v.getTag().toString()) == 3){
            fragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_main,
                    SavedResultFragment(),
                    "SavedResultFragment"
                )
                .commit()
        }else {
            if (hasServerResponse()) (
                    fragmentManager
                        .beginTransaction()
                        .replace(
                            R.id.fragment_main,
                            SearchResultFragment(url),
                            "Fragment2"
                        )
                        .commit()
                    ) else {
                shortToast(applicationContext, "waiting for server response!")
            }
        }
    }

    private fun hasServerResponse() = this::url.isInitialized

    fun submit(view: View) {
        var imageUri =
            (fragmentManager.findFragmentByTag("Fragment1") as UploadImageFragment).imageUri.toString()
        //var rect = (fragmentManager.findFragmentByTag("Fragment1") as Fragment1).actualCropRect!!
        //var imgW = (fragmentManager.findFragmentByTag("Fragment1") as Fragment1).image.width
        //var imgH = (fragmentManager.findFragmentByTag("Fragment1") as Fragment1).image.height
        val croppedImage =
            (fragmentManager.findFragmentByTag("Fragment1") as UploadImageFragment).image.croppedImage

        // sending post to server.
        uploadImage(createFileFromBitmap(croppedImage))

        Log.i("fileImg", imageUri)
        Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()
    }

    // Sends file to server
    fun uploadImage(file: File) {
        // mock api
        val mock = "https://infinite-river-92056.herokuapp.com/upload"

        // actual api
        val apiUrl = "http://api-edu.gtl.ai/api/v1/imagesearch/upload"

        AndroidNetworking.upload(apiUrl)
            .addMultipartFile("image", file)
            .setTag("uploadTest")
            .setPriority(Priority.HIGH)
            .addMultipartParameter("key", "value")
            .setTag("uploadTest")
            .build()
            .getAsString(object : StringRequestListener {
                override fun onResponse(response: String?) {
                    println("response: $response")
                    if (response != null) {
                        url = response
                    }
                }

                override fun onError(error: ANError) {
                    // handle error
                    println("upload error: ${error.errorBody}")
                }
            })
    }

    /*
    * creates file.
    * creates ByteArrayOutputStream
    * compresses bitmap by making it JPEG, passing inn outputstream
    * turns output stream to bytearray
    * writes array to file
    * returns file.
    * */
    fun createFileFromBitmap(bitmap: Bitmap): File {
        // unsure correct context!!!
        val f = File(applicationContext.filesDir, "img.png")

        f.createNewFile()

        //Convert bitmap to byte array
        val bos = ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20 /*ignored for PNG*/, bos);
        val bitmapdata: ByteArray = bos.toByteArray();

        //write the bytes in file
        val fos = FileOutputStream(f);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();
        return f;
    }
}