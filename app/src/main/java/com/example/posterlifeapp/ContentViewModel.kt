package com.example.posterlifeapp

import androidx.lifecycle.ViewModel

class ContentViewModel: ViewModel() {
    var cartAmount: Int = 0

    var title: String = "Inspiration"
    val titleList = listOf("Inspiration", "Profil", "Del", "Indkøbskurv")

    fun getCartAmount(): Number{
        return cartAmount
    }


}


