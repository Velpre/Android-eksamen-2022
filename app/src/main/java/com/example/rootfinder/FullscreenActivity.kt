package com.example.rootfinder

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.rootfinder.db.Image
import com.example.rootfinder.db.ImageRepository
import kotlin.concurrent.thread

class FullscreenActivity : AppCompatActivity() {
    private lateinit var fullScreenImage : ImageView
    private lateinit var saveBtn : Button
    private lateinit var backBtn : Button
    private lateinit var linkBtn : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen)
        val extras = intent.extras
        val bundle = intent.getBundleExtra("imgData")
        val data = bundle?.getParcelable<ImageResult>("resultSet")


        fullScreenImage = findViewById(R.id.imageViewFullScreen)
        saveBtn = findViewById(R.id.save_btn)
        backBtn = findViewById(R.id.back_btn)
        linkBtn = findViewById(R.id.link_btn)

        Glide.with(applicationContext)
            .load(data?.url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.placeholder)
            .into(fullScreenImage)

        // onclick
        saveBtn.setOnClickListener{
            thread{
            imageToDb(fullScreenImage.drawable)
            }
            Toast.makeText(applicationContext,"imaged saved", Toast.LENGTH_SHORT).show()
        }

        backBtn.setOnClickListener{
            finish()
        }
        linkBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(data?.store_link))
            startActivity(intent)
        }

    }
    private fun imageToDb(inImg: Drawable) {
        val bitmap = inImg.toBitmap()
        val db = ImageRepository(applicationContext)
        db.insertAll(Image(bitmap))
        val result = db.getAllImages()
        println("result from db: $result")

    }
}