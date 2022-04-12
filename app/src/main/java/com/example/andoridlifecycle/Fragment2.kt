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
        GlobalScope.launch(Dispatchers.IO) {
            val urls = loadDataApi("https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/PNG_transparency_demonstration_1.png/640px-PNG_transparency_demonstration_1.png")

           launch(Main){
               delay(7000)
               listOfUrls = urls
               println(listOfUrls.toString())
           }
            //setUrlsOnMainThread(urls)
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
                           // Log.i("url", url.toString())
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

        // Setting up RCV with Grid.
        recyclerView.layoutManager = GridLayoutManager(context,3)

        // Adapter class is initialized and url list is passed.

        // TODO Tror listOfURls må passes async, altså etter res er kommet.


        val itemAdapter = ItemAdapter(requireContext(), listOfUrls)

        // adapter instance is set to the recyclerview to inflate the items.
        recyclerView.adapter = itemAdapter

        return view
    }

}

