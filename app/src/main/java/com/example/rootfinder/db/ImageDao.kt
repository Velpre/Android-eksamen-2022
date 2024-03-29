package com.example.rootfinder.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ImageDao {
    @Query("SELECT * FROM image")
    fun getAll(): List<Image>

    @Query("SELECT * FROM image WHERE id = :id")
    fun getById(id: Int): Image

    @Insert
    fun insertAll(vararg images: Image)

    @Delete
    fun delete(image: Image)
}