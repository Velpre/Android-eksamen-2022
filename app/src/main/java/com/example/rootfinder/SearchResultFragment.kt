package com.example.rootfinder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.androidnetworking.AndroidNetworking
import android.util.Log
import android.widget.ProgressBar
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.example.rootfinder.adapters.ItemAdapter
import kotlinx.android.synthetic.main.fragment2.*
import org.json.JSONArray
import org.json.JSONException

class SearchResultFragment(private var imageURL: String) : Fragment() {
    private var resultList = ArrayList<ImageResult>()
    private lateinit var spinner: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidNetworking.initialize(context)

        doImageSearch(imageURL, "bing")
        doImageSearch(imageURL, "tineye")
        doImageSearch(imageURL, "google")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spinner = view.findViewById(R.id.progressSpinner)

        showSpinner()
    }

    override fun onStop() {
        super.onStop()
        AndroidNetworking.cancelAll()
    }

    private fun doImageSearch(imgUrlToSearch: String, searchProvider: String) {
        AndroidNetworking.get(API_URL + searchProvider)
            .addQueryParameter("url", imgUrlToSearch)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray) {
                    hideSpinner()

                    if(response.length() == 0) {
                        if(resultList.size == 0){
                            no_result_text.isInvisible = false
                        }
                        Log.i("response", "No results from server from $searchProvider")
                        return
                    }

                    for (i in 0 until response.length()) {
                        try {

                            val obj = response.getJSONObject(i)
                            val url = obj.getString("image_link")
                            val storeLink = obj.getString("store_link")
                            val result =  ImageResult(url,storeLink)
                            resultList.add(result)

                            val itemAdapter = ItemAdapter(requireContext(), resultList)
                            val recyclerView: RecyclerView =
                                requireView().findViewById(R.id.recycler_view_items)
                            recyclerView.layoutManager = GridLayoutManager(context, 3)
                            recyclerView.adapter = itemAdapter

                        } catch (e: JSONException) {
                            e.printStackTrace()

                        }
                    }
                }

                override fun onError(anError: ANError) {
                    Log.e("error in fetching data", anError.toString())

                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment2, container, false)
    }

    private fun showSpinner() {
        spinner.visibility = View.VISIBLE
    }

    private fun hideSpinner() {
        spinner.visibility = View.GONE
    }
}

