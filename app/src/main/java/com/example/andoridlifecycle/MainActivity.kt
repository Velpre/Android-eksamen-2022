package com.example.andoridlifecycle

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import com.androidnetworking.AndroidNetworking

import androidx.fragment.app.FragmentManager
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {
    private lateinit var imgUrlFromServer: String;
    private lateinit var fragmentManager: FragmentManager
    private lateinit var navigationButtons: List<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidNetworking.initialize(applicationContext)

        setButtonOnClick()
    }

    private fun setButtonOnClick() {
        navigationButtons = listOf(findViewById(R.id.b1), findViewById(R.id.b2), findViewById(R.id.b3))

        navigationButtons.forEach {
            it.setOnClickListener { button ->
                resetColors()
                setColorClicked(button)
                navigateToFragment(button)
            }
        }
    }

    private fun setColorClicked(button: View) {
        button.setBackgroundColor(Color.parseColor("#152238"))
    }

    private fun resetColors() {
        navigationButtons.forEach { it.setBackgroundColor(Color.parseColor("#0C4160")) }
    }

    private fun navigateToFragment(v: View) {
        val tag = Integer.parseInt(v.tag.toString())

        when (tag) {
            1 -> switchFragment(UploadImageFragment())
            3 -> switchFragment(SavedResultFragment())
            else -> {
                if (hasServerResponse()) {
                    switchFragment(SearchResultFragment(imgUrlFromServer))
                } else {
                    shortToast(applicationContext, "waiting for server response!")
                }
            }
        }
    }

    private fun switchFragment(fragment: Fragment) {
        fragmentManager = supportFragmentManager
        fragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left,R.anim.enter_left_to_right, R.anim.exit_right_to_left,)
            .replace(
                R.id.fragment_main,
                fragment,
                fragment.javaClass.simpleName
            )
            .commit()
    }

    private fun hasServerResponse() = this::imgUrlFromServer.isInitialized

    fun submit(view: View) {
        val uploadImageFragment =
            fragmentManager.findFragmentByTag("UploadImageFragment") as UploadImageFragment

        if (uploadImageFragment.imageUri == null) {
            Toast.makeText(this, "Please choose image", Toast.LENGTH_SHORT).show()
        } else {
            val croppedImage = uploadImageFragment.image.croppedImage
            uploadImage(createFileFromBitmap(croppedImage))
            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()
        }
    }

    /*
    * Sends image with POST request to API
    * Receives image url from server and
    * assigns it to variable
    * */
    private fun uploadImage(file: File) {
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
                        imgUrlFromServer = response
                    }
                }

                override fun onError(error: ANError) {
                    // handle error
                    // TODO: display error to user
                    println("upload error: ${error.errorBody}")
                }
            })
    }

    /*
    * creates file.
    * creates ByteArrayOutputStream
    * compresses bitmap by making it JPEG, passing inn output-stream
    * turns output stream to bytearray
    * writes array to file
    * returns file.
    * */
    private fun createFileFromBitmap(bitmap: Bitmap): File {
        val f = File(applicationContext.filesDir, "img.png")

        f.createNewFile()

        //Convert bitmap to byte array
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, bos)
        val bitmapData: ByteArray = bos.toByteArray()

        //write the bytes in file
        val fos = FileOutputStream(f)
        fos.write(bitmapData)
        fos.flush()
        fos.close()

        return f
    }
}