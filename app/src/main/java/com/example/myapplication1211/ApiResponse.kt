package com.example.myapplication1211

import android.os.Parcel
import android.os.Parcelable

data class ApiResponse(
    val data: List<ImageData>,
    val status: String
)

data class ImageData(
    val name: String,
    val image: String,
    val ingredients: String,
    val cookTime: String,
    val source: String,
    val recipeYield: String,
    val prepTime: String?,
    val description: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,      // name
        parcel.readString()!!,      // image
        parcel.readString()!!,      // ingredients
        parcel.readString()!!,      // cookTime
        parcel.readString()!!,      // source
        parcel.readString()!!,      // recipeYield
        parcel.readString(),        // prepTime (nullable)
        parcel.readString()         // description (nullable)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(image)
        parcel.writeString(ingredients)
        parcel.writeString(cookTime)
        parcel.writeString(source)
        parcel.writeString(recipeYield)
        parcel.writeString(prepTime)
        parcel.writeString(description)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<ImageData> {
        override fun createFromParcel(parcel: Parcel): ImageData {
            return ImageData(parcel)
        }

        override fun newArray(size: Int): Array<ImageData?> {
            return arrayOfNulls(size)
        }
    }
}
