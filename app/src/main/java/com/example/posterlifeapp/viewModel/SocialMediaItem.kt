package com.example.posterlifeapp.viewModel

import com.example.posterlifeapp.R

sealed class SocialMediaItem(var route: String, var icon: Int, var title: String)
{
    object FacebookIC: NavigationItem("Facebook", R.drawable.ic_iconmonstr_facebook_4, "Facebook")
    object TwitterIC: NavigationItem("Twitter", R.drawable.ic_iconmonstr_twitter_1, "Twitter")
    object InstagramIC: NavigationItem("Instagram", R.drawable.ic_iconmonstr_instagram_11, "Instagram")
    object Pinterest: NavigationItem("Pinterest", R.drawable.ic_iconmonstr_pinterest_1, "Pinterest")
    object Email: NavigationItem("Email", R.drawable.ic_iconmonstr_email_2, "Email")

}