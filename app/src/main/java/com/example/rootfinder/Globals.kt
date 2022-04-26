package com.example.rootfinder

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast

fun uriToBitmap(context: Context, id: Int?, uri: String?): Bitmap {
    return MediaStore.Images.Media.getBitmap(context.contentResolver, Uri.parse(uri))
}

fun getBitmap(context: Context, id: Int?, uri: String?, decoder: (Context, Int?, String?) -> Bitmap): Bitmap {
    return decoder(context, id, uri)
}

fun shortToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

const val API_URL: String = "http://api-edu.gtl.ai/api/v1/imagesearch/"
const val UPLOAD_URL: String = API_URL + "upload"