package com.example.andoridlifecycle

import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.example.andoridlifecycle.adapters.ItemAdapter
import com.example.andoridlifecycle.data.ImageSearchViewmodel


class Fragment2() : Fragment() {

    // global members
    private var listOfUrls = ArrayList<String>()
    private val model: ImageSearchViewmodel = ImageSearchViewmodel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidNetworking.initialize(requireContext())

        model.getUrls().observe(this, Observer<List<String>>{ Strings ->
            Log.i("wtf", Strings.toString())
        })
    }// onCreate ends

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
        val model:ImageSearchViewmodel = ImageSearchViewmodel()
        val itemAdapter = ItemAdapter(requireContext(), listOfUrls)
        // adapter instance is set to the recyclerview to inflate the items.
        recyclerView.adapter = itemAdapter

        return view
    }

}

