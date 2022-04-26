package com.example.rootfinder.db

import android.content.Context

class ImageRepository(context: Context) {
    var db: ImageDao = AppDatabase.getInstance(context)?.imageDao()!!

    fun getAllImages(): List<Image> {
        return db.getAll()
    }

    fun insertAll(image: Image) {
        return db.insertAll(image)
    }

    fun delete(image: Image) {
        return db.delete(image)
    }

    fun getById(id: Int): Image {
        return db.getById(id)
    }
}