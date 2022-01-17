package com.example.posterlifeapp.viewModel

import com.example.posterlifeapp.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String){
    object Inspiration : NavigationItem("inspiration", R.drawable.ic_lightbulb_black_24dp, "Inspiration")
    object Profile : NavigationItem("profile", R.drawable.ic_person_black_24dp, "Profile")
    object Share : NavigationItem("share", R.drawable.ic_share_black_24dp, "Share")
    object Cart : NavigationItem("cart", R.drawable.ic_share_black_24dp, "Cart")
}
