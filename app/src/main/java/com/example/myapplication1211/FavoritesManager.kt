package com.example.myapplication1211

object FavoritesManager {
    private val favoriteList = mutableListOf<ImageData>()

    fun addToFavorites(imageData: ImageData) {
        if (!favoriteList.contains(imageData)) {
            favoriteList.add(imageData)
        }
    }

    fun removeFromFavorites(imageData: ImageData) {
        favoriteList.remove(imageData)
    }

    fun isFavorite(imageData: ImageData): Boolean {
        return favoriteList.contains(imageData)
    }


    fun getFavorites(): List<ImageData> {
        return favoriteList
    }
}
