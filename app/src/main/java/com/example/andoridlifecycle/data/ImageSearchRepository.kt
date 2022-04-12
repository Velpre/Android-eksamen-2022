package com.example.andoridlifecycle.data

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import org.json.JSONArray
import org.json.JSONException
import java.io.File
import java.util.concurrent.Executors
import org.json.JSONObject

import com.androidnetworking.interfaces.JSONObjectRequestListener

import com.androidnetworking.interfaces.UploadProgressListener
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope


class ImageSearchRepository(context: Context) {

    suspend fun getJSON(url: String): List<String> {
        var loading = true
        val listOfUrls = ArrayList<String>()
        coroutineScope {
            AndroidNetworking.get("http://api-edu.gtl.ai/api/v1/imagesearch/bing")
                .setTag("get_url")
                .addQueryParameter("url", url)
                .setExecutor(Executors.newSingleThreadExecutor())
                .build()
                .getAsJSONArray(object : JSONArrayRequestListener {
                    override fun onResponse(response: JSONArray) {

                        for (i in 0 until response.length()) {
                            try {
                                //val url = ImgUrl() - settes til klasse senere?
                                val obj = response.getJSONObject(i)

                                Log.i("url", url)

                                val temp = obj.getString("thumbnail_link")
                                listOfUrls.add(temp)

                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                            loading = false
                        }
                    }

                    override fun onError(anError: ANError) {
                        Log.e("feil", anError.toString())
                    }
                })
        }
        while(AndroidNetworking.isRequestRunning("get_url")){}

        // crashes?!?!?
        return listOfUrls

    }

    fun postJSON(uri: Uri) {
        val file = File(uri.toString())

        AndroidNetworking.upload("http://api-edu.gtl.ai/api/v1/imagesearch/upload")
            .addMultipartFile("image", file)
            .setTag("uploadTest")
            .setPriority(Priority.HIGH)
            .build()
            .setUploadProgressListener { bytesUploaded, totalBytes ->
                // do anything with progress
            }
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    Log.i("post", response.toString())
                }

                override fun onError(error: ANError) {
                    Log.i("post", error.toString())
                }
            })
    }
}