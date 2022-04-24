package com.example.andoridlifecycle

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
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
    private lateinit var b1: Button;
    private lateinit var b2: Button;
    private lateinit var b3: Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidNetworking.initialize(applicationContext)

        b1 = findViewById(R.id.b1)
        b2 = findViewById(R.id.b2)
        b3 = findViewById(R.id.b3)

        setButtonOnClick(b1)
        setButtonOnClick(b2)
        setButtonOnClick(b3)
    }

    private fun setButtonOnClick(button: Button) {
        button.setOnClickListener {
            resetColors()
            it.setBackgroundColor(Color.parseColor("#152238"))
            switchFragment(it)
        }
    }

    private fun resetColors() {
        b1.setBackgroundColor(Color.parseColor("#8B4000"))
        b2.setBackgroundColor(Color.parseColor("#8B4000"))
        b3.setBackgroundColor(Color.parseColor("#8B4000"))
    }

    private fun switchFragment(v: View) {
        fragmentManager = supportFragmentManager

        if (Integer.parseInt(v.tag.toString()) == 1) {
            fragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_main,
                    UploadImageFragment(),
                    "Fragment1"
                )
                .commit()
        } else if (Integer.parseInt(v.tag.toString()) == 3) {
            fragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_main,
                    SavedResultFragment(),
                    "Fragment3"
                )
                .commit()
        } else {
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

        val findView = fragmentManager.findFragmentByTag("Fragment1") as UploadImageFragment

        if (findView.imageUri == null) {
            Toast.makeText(this, "Please choose image", Toast.LENGTH_SHORT).show()
        } else {
            val croppedImage = findView.image.croppedImage
            uploadImage(createFileFromBitmap(croppedImage))
            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()
        }
    }

    // Sends file to server
    private fun uploadImage(file: File) {
        // actual api
        val apiUrl = "http://api-edu.gtl.ai/api/v1/imagesearch/upload"

        AndroidNetworking.upload(UPLOAD_URL)
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
    private fun createFileFromBitmap(bitmap: Bitmap): File {
        // unsure correct context!!!
        val f = File(applicationContext.filesDir, "img.png")

        f.createNewFile()

        //Convert bitmap to byte array
        val bos = ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20 /*ignored for PNG*/, bos);
        val bitmapData: ByteArray = bos.toByteArray();

        //write the bytes in file
        val fos = FileOutputStream(f);
        fos.write(bitmapData);
        fos.flush();
        fos.close();
        return f;
    }
}