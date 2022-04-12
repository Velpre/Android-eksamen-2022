package com.example.andoridlifecycle.db

import android.content.Context

class ImageRepository(context: Context) {
    var db: ImageDao = AppDatabase.getInstance(context)?.imageDao()!!

    //Fetch All the Users
    fun getAllImages(): List<Image> {
        return db.getAll()
    }

    fun insertAll(image: Image) {
        return db.insertAll(image)
    }
}