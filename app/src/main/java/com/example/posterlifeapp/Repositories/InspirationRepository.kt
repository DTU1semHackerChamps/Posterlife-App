package com.example.posterlifeapp.Repositories

import android.content.res.AssetManager
import com.example.posterlifeapp.model.Poster
import com.google.gson.Gson
import com.google.gson.JsonSerializationContext
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class InspirationRepository() {

    fun loadJSONPosterData(): List<Poster>? {
        val poster: Poster = Gson().fromJson("posterlife.json", Poster::class.java)
        val list = mutableListOf<Poster>()
        list.add(poster)
        return list
    }
}