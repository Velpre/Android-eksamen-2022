package com.example.rootfinder.db

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image")
data class Image(
    val image: Bitmap,
    val rootSource: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

}