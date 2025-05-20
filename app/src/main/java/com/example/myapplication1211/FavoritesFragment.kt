package com.example.myapplication1211

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoritesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ImageAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewFavorites)

        val spanCount = if (isTablet()) 3 else 2
        recyclerView.layoutManager = GridLayoutManager(context, spanCount)


        progressBar = view.findViewById(R.id.progressBarFavorites)


        updateFavorites()

        return view
    }

    private fun isTablet(): Boolean {
        val screenLayout = resources.configuration.screenLayout and
                Configuration.SCREENLAYOUT_SIZE_MASK
        return screenLayout == Configuration.SCREENLAYOUT_SIZE_LARGE
    }

    private fun updateFavorites() {

        progressBar.visibility = View.VISIBLE


        val favoriteList = FavoritesManager.getFavorites()


        if (favoriteList.isEmpty()) {
            Toast.makeText(context, "No favorites found", Toast.LENGTH_SHORT).show()
        }


        adapter = ImageAdapter(favoriteList) { imageData ->
            onFavoriteClicked(imageData)
        }


        recyclerView.adapter = adapter


        progressBar.visibility = View.GONE
    }

    private fun onFavoriteClicked(imageData: ImageData) {

        if (FavoritesManager.isFavorite(imageData)) {
            FavoritesManager.removeFromFavorites(imageData)
            Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show()
        } else {
            FavoritesManager.addToFavorites(imageData)
            Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show()
        }


        updateFavorites()
    }

    override fun onResume() {
        super.onResume()
        updateFavorites()
    }
}
