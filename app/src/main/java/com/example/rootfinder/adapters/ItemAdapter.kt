package com.example.rootfinder.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.rootfinder.FullscreenActivity
import com.example.rootfinder.ImageResult
import com.example.rootfinder.R
import kotlinx.android.synthetic.main.item_custom_row.view.*

class ItemAdapter(private val context: Context, private val data: ArrayList<ImageResult>) :
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // displaying images + Passing data via intent/parcelable to fullscreenActivity.
        val url = data[position].url
        val imgData = data[position]
        Glide.with(context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.placeholder)
            .into(holder.imageView).view.setOnClickListener {
                val intent =Intent(context, FullscreenActivity::class.java)
                val bundle = Bundle()
                bundle.putParcelable("resultSet", imgData )
                intent.putExtra("imgData",bundle)
                context.startActivity(intent)
            }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    // A ViewHolder describes an item view and metadata about its place within the RecyclerView.
    // getting views from xml file
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.iv_image
    }
}