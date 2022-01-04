package com.example.posterlifeapp.model

import com.google.gson.annotations.SerializedName

data class Poster (
    @SerializedName("Title")
    val title: String,
    @SerializedName("Description")
    val description: String,
    @SerializedName("ImageUrl")
    val imageUrl: String,
    @SerializedName("Price 70x100")
    val price70x100: Int,
    @SerializedName("Price 50x70")
    val price50x70: Int,
    @SerializedName("Price A3")
    val priceA3: Int
        )