package com.example.myapplication1211

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        val name = intent.getStringExtra("name")
        val image = intent.getStringExtra("image")
        val ingredients = intent.getStringExtra("ingredients")
        val cookTime = intent.getStringExtra("cookTime")


        val nameTextView: TextView = findViewById(R.id.textViewName)
        val imageView: ImageView = findViewById(R.id.imageViewDetail)
        val ingredientsTextView: TextView = findViewById(R.id.textViewIngredients)
        val cookTimeTextView: TextView = findViewById(R.id.textViewCookTime)


        nameTextView.text = name
        ingredientsTextView.text = ingredients
        cookTimeTextView.text = cookTime


        if (image.isNullOrEmpty()) {
            // if URL empty then load the error image
            Picasso.get()
                .load(R.drawable.error_image)
                .into(imageView)
            Toast.makeText(this, "Image not found, loading initial image", Toast.LENGTH_SHORT).show()
        } else {
            // if found the url correctly then load the image
            Picasso.get()
                .load(image)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(imageView)
        }
    }
}
