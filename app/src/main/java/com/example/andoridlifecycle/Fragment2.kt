package com.example.andoridlifecycle

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.androidnetworking.AndroidNetworking
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.example.andoridlifecycle.adapters.ItemAdapter
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import org.json.JSONArray
import org.json.JSONException


class Fragment2() : Fragment() {

    // global members
    private var listOfUrls = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidNetworking.initialize(context)

        // Coroutines
        GlobalScope.launch {
            val urls = async {
                loadDataApi("https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/PNG_transparency_demonstration_1.png/640px-PNG_transparency_demonstration_1.png")
            }
            setUrlsOnMainThread(urls)
            println(listOfUrls.toString())

        }
    }// onCreate ends

    // Step 1: API CALL
    private fun loadDataApi(url : String) : ArrayList<String> {

        AndroidNetworking.get("http://api-edu.gtl.ai/api/v1/imagesearch/bing")
            .addQueryParameter("url", url)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray) {

                    for (i in 0 until response.length()) {
                        try {
                            //val url = ImgUrl() - settes til klasse senere?
                            val obj = response.getJSONObject(i)
                            val url = obj.getString("thumbnail_link")
                            Log.i("url", url.toString())
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
        return listOfUrls
    }

    // Step 2: setting values to main thread
    private suspend fun setUrlsOnMainThread(urls: Deferred<ArrayList<String>>) {
        delay(7000)
        withContext(Main){
            urls.await().apply{
                listOfUrls = urls.await()
            }
        }
    }

    // setting list of urs to item view
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment2, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view_items)

        // Adapter class is initialized and url list is passed.
        val itemAdapter = ItemAdapter(requireContext(), listOfUrls)

        // Setting up RCV w Grid. PS:Starts with id for recyclerView in xml.
        recyclerView.layoutManager = GridLayoutManager(context,3)
       // recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)

        // adapter instance is set to the recyclerview to inflate the items.
        recyclerView.adapter = itemAdapter

        return view
    }

}

