package com.example.andoridlifecycle

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.andoridlifecycle.db.Image
import com.example.andoridlifecycle.db.ImageRepository
import kotlin.concurrent.thread

class FullscreenSavedActivity : AppCompatActivity() {
    private lateinit var fullScreenImage: ImageView
    private lateinit var deleteBtn: Button
    private lateinit var backBtn: Button
    private lateinit var image: Image

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen_saved)
        val extras = intent.extras

        fullScreenImage = findViewById(R.id.imageViewFullScreenSaved)
        deleteBtn = findViewById(R.id.delete_btn)
        backBtn = findViewById(R.id.back_btn_saved)

        thread {
            getImage(extras?.get("id") as Int)
            runOnUiThread {
                fullScreenImage.setImageBitmap(image.image)
            }
        }

        deleteBtn.setOnClickListener {
            thread {
                deleteImage()
            }
            finish()
        }

        backBtn.setOnClickListener { finish() }
    }

    private fun deleteImage() {
        val db = ImageRepository(applicationContext)
        db.delete(image)

        println("image deleted")
        finish()
    }

    private fun getImage(id: Int) {
        val db = ImageRepository(applicationContext)
        val result = db.getById(id)
        image = result
    }
}