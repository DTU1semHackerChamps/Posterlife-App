package com.example.posterlifeapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ContentViewModel: ViewModel() {
    val cartAmount = mutableStateOf(0)

    var title = mutableStateOf("")
    val titleList = listOf("Inspiration", "Profil", "Del", "Indkøbskurv")




}


