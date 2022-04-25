package com.example.andoridlifecycle

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.andoridlifecycle.adapters.ItemAdapterDb
import com.example.andoridlifecycle.db.Image
import com.example.andoridlifecycle.db.ImageRepository
import kotlinx.android.synthetic.main.fragment1.*
import kotlin.concurrent.thread


class SavedResultFragment : Fragment() {
    var itemAdapter: ItemAdapterDb? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        thread {
            loadDataDb()
        }
    }// onCreate ends

    override fun onResume() {
        super.onResume()

        thread {
            loadDataDb()
        }
    }

    // Step 1: API CALL
    private fun loadDataDb() {
        val repo = ImageRepository(requireContext())
        val result: List<Image> = repo.getAllImages()

        activity?.runOnUiThread {
            itemAdapter = ItemAdapterDb(requireContext(), result)
            val recyclerView: RecyclerView =
                requireView().findViewById(R.id.recycler_view_items_db)
            recyclerView.layoutManager = GridLayoutManager(context, 3)
            recyclerView.adapter = itemAdapter
        }
    }

    // setting the fragment..I THINK hehe.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment3, container, false)
    }

}

