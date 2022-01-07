package com.example.posterlifeapp

import androidx.lifecycle.ViewModel

class ContentViewModel: ViewModel() {
    var cartAmount: Int = 0

    fun getCartAmount(): Number{
        return cartAmount
    }


}


