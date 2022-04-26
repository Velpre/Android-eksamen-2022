package com.example.rootfinder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rootfinder.adapters.ItemAdapterDb
import com.example.rootfinder.db.Image
import com.example.rootfinder.db.ImageRepository
import kotlin.concurrent.thread

class SavedResultFragment : Fragment() {
    var itemAdapter: ItemAdapterDb? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        thread {
            loadDataDb()
        }
    }

    /*
    * Reloading data when fragment is resumed.
    * */
    override fun onResume() {
        super.onResume()

        thread {
            loadDataDb()
        }
    }

    /*
    * retries all images stored, updates UI on the UI-thread
    * */
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment3, container, false)
    }
}
