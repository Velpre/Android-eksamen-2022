package com.example.andoridlifecycle

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edmodo.cropper.CropImageView
import com.example.andoridlifecycle.adapters.ItemAdapter
import com.example.andoridlifecycle.db.ImageRepository
import kotlin.concurrent.thread

class SavedResultFragment : Fragment() {
    public  var image: ImageView? = null
    private lateinit var db: ImageRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment1, container, false)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun initDb() {
        db = ImageRepository(requireContext())
        thread {
            initDb()
            image = view?.findViewById<ImageView>(R.id.best)
            val listOfImages = db.getAllImages()
            val listOfUrls = ArrayList<String>()
            val img = listOfImages[0].image

            image?.setImageBitmap(img)

        }
    }

}