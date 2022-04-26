package com.example.rootfinder

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.rootfinder.db.Image
import com.example.rootfinder.db.ImageRepository
import kotlin.concurrent.thread

class FullscreenSavedActivity : AppCompatActivity() {
    private lateinit var fullScreenImage: ImageView
    private lateinit var deleteBtn: Button
    private lateinit var backBtn: Button
    private lateinit var rootBtn: Button
    private lateinit var image: Image

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen_saved)
        val extras = intent.extras

        fullScreenImage = findViewById(R.id.imageViewFullScreenSaved)
        deleteBtn = findViewById(R.id.delete_btn)
        backBtn = findViewById(R.id.back_btn_saved)
        rootBtn = findViewById(R.id.link_btn_saved)

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

        rootBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(image.rootSource))
            startActivity(intent)
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