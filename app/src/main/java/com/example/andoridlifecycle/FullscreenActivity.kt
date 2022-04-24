package com.example.andoridlifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class FullscreenActivity : AppCompatActivity() {
    private lateinit var fullScreenImage : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen)
        val extras = intent.extras

        fullScreenImage = findViewById(R.id.imageViewFullScreen)

        Glide.with(applicationContext)
            .load(extras?.get("image"))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.placeholder)
            .into(fullScreenImage)


    }
}