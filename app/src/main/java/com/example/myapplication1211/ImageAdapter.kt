package com.example.myapplication1211

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ImageAdapter(
    private val imageList: List<ImageData>,
    private val onFavoriteClick: (ImageData) -> Unit
) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textView: TextView = itemView.findViewById(R.id.textView)
        val favoriteIcon: ImageView = itemView.findViewById(R.id.favoriteIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageData = imageList[position]


        holder.textView.text = imageData.name

        if (imageData.image.isNullOrEmpty()) {

            Picasso.get()
                .load(R.drawable.placeholder_image)
                .into(holder.imageView)
        } else {

            Picasso.get()
                .load(imageData.image)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(holder.imageView)
        }


        val isFavorite = FavoritesManager.isFavorite(imageData)
        holder.favoriteIcon.setImageResource(
            if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
        )


        holder.favoriteIcon.setOnClickListener {
            onFavoriteClick(imageData)
        }


        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("name", imageData.name)
                putExtra("image", imageData.image)
                putExtra("ingredients", imageData.ingredients)
                putExtra("cookTime", imageData.cookTime)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = imageList.size
}
