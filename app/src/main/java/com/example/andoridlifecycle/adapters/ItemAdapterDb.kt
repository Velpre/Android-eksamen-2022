package com.example.andoridlifecycle.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.andoridlifecycle.FullscreenSavedActivity
import com.example.andoridlifecycle.R
import com.example.andoridlifecycle.db.Image
import kotlinx.android.synthetic.main.item_custom_row2.view.*

class ItemAdapterDb(private val context: Context, private val images: List<Image>) :
    RecyclerView.Adapter<ItemAdapterDb.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_custom_row2,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = images[position]

        holder.imageView.setImageBitmap(image.image)

        holder.imageView.setOnClickListener {
            val intent = Intent(context, FullscreenSavedActivity::class.java)
            //intent.putExtra("image", image.image)
            intent.putExtra("id", image.id)

            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return images.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imageView: ImageView = view.iv_image_db
    }
}