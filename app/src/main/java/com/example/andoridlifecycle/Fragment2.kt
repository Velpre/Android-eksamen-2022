package com.example.andoridlifecycle

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.androidnetworking.AndroidNetworking
import kotlinx.coroutines.launch
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
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
            lifecycleScope.launch(Dispatchers.IO) {
                val urls = loadDataApi("https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/PNG_transparency_demonstration_1.png/640px-PNG_transparency_demonstration_1.png")

                launch(Main) {
                    delay(7000)
                    listOfUrls = urls
                    println(listOfUrls.toString())
                    val itemAdapter = ItemAdapter(requireContext(), listOfUrls)
                    val recyclerView: RecyclerView =
                        requireView().findViewById(R.id.recycler_view_items)
                    recyclerView.layoutManager = GridLayoutManager(context, 3)
                    recyclerView.adapter = itemAdapter

                }
            }

    }// onCreate ends

    // Step 1: API CALL
    private fun loadDataApi(url: String): ArrayList<String> {
        AndroidNetworking.get("http://api-edu.gtl.ai/api/v1/imagesearch/bing")
            .addQueryParameter("url", url)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray) {
                    for (i in 0 until response.length()) {
                        try {
                            val obj = response.getJSONObject(i)
                            val url = obj.getString("thumbnail_link")
                            listOfUrls.add(url)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                            Toast.makeText(context, "Problems getting data", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                override fun onError(anError: ANError) {
                    Log.e("error in fetching data", anError.toString())
                    Toast.makeText(context, "Problems getting data", Toast.LENGTH_SHORT).show()
                }
            })
        return listOfUrls
    }

    // setting list of urs to item view
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment2, container, false)

        return view
    }

}

