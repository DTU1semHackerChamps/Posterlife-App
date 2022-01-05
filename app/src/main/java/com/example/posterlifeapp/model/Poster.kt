package com.example.posterlifeapp.model

import com.google.gson.annotations.SerializedName

data class Poster (
    @SerializedName("Title")
    var title: String,
    @SerializedName("Description")
    var description: String,
    @SerializedName("ImageUrl")
    var imageUrl: String,
    @SerializedName("Price 70x100")
    var price70x100: Int,
    @SerializedName("Price 50x70")
    var price50x70: Int,
    @SerializedName("Price A3")
    var priceA3: Int
    )