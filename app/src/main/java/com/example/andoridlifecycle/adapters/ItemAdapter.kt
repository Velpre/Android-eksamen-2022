package com.example.andoridlifecycle.adapters

import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.andoridlifecycle.FullscreenActivity
import com.example.andoridlifecycle.MainActivity
import com.example.andoridlifecycle.R
import com.example.andoridlifecycle.db.Image
import com.example.andoridlifecycle.db.ImageRepository
import kotlinx.android.synthetic.main.item_custom_row.view.*
import kotlin.concurrent.thread

class ItemAdapter(private val context: Context, private val urls: ArrayList<String>) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    /**
     * Inflates the custom view which is designed in xml layout file
     */
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

        // her kommer et array av bildeadresser
        val url = urls[position]
        // Glide med url

        Glide.with(context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.placeholder)
            .into(holder.imageView).view.setOnClickListener {
                val intent =Intent(context, FullscreenActivity::class.java)
                intent.putExtra("image",url)
                context.startActivity(intent)

            /*  thread {
                    imageToDb((it as AppCompatImageView).drawable)
                }*/
            }
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