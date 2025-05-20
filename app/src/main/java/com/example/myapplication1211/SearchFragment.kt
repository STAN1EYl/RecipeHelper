package com.example.myapplication1211

import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchEditText: EditText
    private lateinit var adapter: ImageAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewSearch)

        val spanCount = if (isTablet()) 3 else 2
        recyclerView.layoutManager = GridLayoutManager(context, spanCount)
        progressBar = view.findViewById(R.id.progressBarSearch)
        searchEditText = view.findViewById(R.id.searchEditText)


        adapter = ImageAdapter(emptyList()) { imageData -> onFavoriteClicked(imageData) }
        recyclerView.adapter = adapter


        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()
                if (query.isNotEmpty()) {
                    searchData(query)
                } else {
                    loadAllData()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })


        loadAllData()

        return view
    }

    private fun isTablet(): Boolean {
        val screenLayout = resources.configuration.screenLayout and
                Configuration.SCREENLAYOUT_SIZE_MASK
        return screenLayout == Configuration.SCREENLAYOUT_SIZE_LARGE
    }


    private fun searchData(query: String) {
        progressBar.visibility = View.VISIBLE
        RetrofitClient.apiService.searchData(query).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse != null && apiResponse.status == "success") {
                        val imageList = apiResponse.data
                        updateAdapter(imageList)
                        progressBar.visibility = View.GONE
                    } else {
                        Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Search failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
            }
        })
    }

    private fun loadAllData() {
        RetrofitClient.apiService.getImages().enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse != null && apiResponse.status == "success") {
                        val imageList = apiResponse.data
                        updateAdapter(imageList)
                    } else {
                        Toast.makeText(context, "Failed to load data", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Error loading data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateAdapter(imageList: List<ImageData>) {
        adapter = ImageAdapter(imageList) { imageData -> onFavoriteClicked(imageData) }
        recyclerView.adapter = adapter
    }

    private fun onFavoriteClicked(imageData: ImageData) {
        if (FavoritesManager.isFavorite(imageData)) {
            FavoritesManager.removeFromFavorites(imageData)
            Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show()
        } else {
            FavoritesManager.addToFavorites(imageData)
            Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show()
        }
    }
}
