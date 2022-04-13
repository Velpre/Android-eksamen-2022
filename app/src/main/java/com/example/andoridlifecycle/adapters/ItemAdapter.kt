package com.example.andoridlifecycle.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.andoridlifecycle.R
import kotlinx.android.synthetic.main.item_custom_row.view.*

class ItemAdapter(private val context: Context, private val urls: ArrayList<String>) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_custom_row,
                parent,
                false
            )
        )
    }

    //Binder alle items i ArrayListen , som er urls, til en Imageview
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // spinner while loading
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 6f
        circularProgressDrawable.centerRadius = 40f
        circularProgressDrawable.start()

        // Glide med url
        val url = urls[position]
        Glide.with(context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(circularProgressDrawable)
            .into(holder.imageView)
    }

    // Gets the number of items in the list
    override fun getItemCount(): Int {
        return urls.size
    }

    // A ViewHolder describes an item view and metadata about its place within the RecyclerView.
    // her må vi hente de elementene vi vil fra via id fra xml fila
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holder imageView som deklarert med id i custom xml layout
        val imageView: ImageView = view.iv_image
    }
}