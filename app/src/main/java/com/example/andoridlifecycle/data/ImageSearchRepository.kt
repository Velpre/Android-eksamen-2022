package com.example.andoridlifecycle.data

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import org.json.JSONArray
import org.json.JSONException
import java.util.concurrent.Executors

class ImageSearchRepository(context: Context) {

    fun getJSON(url: String): ArrayList<String> {

        val listOfUrls: ArrayList<String> = ArrayList()
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
                            val url = obj.getString("thumbnail_link")

                            listOfUrls.add(url)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                }

                override fun onError(anError: ANError) {
                    Log.e("feil", anError.toString())
                }
            })
        while(AndroidNetworking.isRequestRunning("get_url")){

        }
        return listOfUrls
    }
}