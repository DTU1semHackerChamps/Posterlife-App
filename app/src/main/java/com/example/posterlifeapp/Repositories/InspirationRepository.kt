package com.example.posterlifeapp.Repositories

import android.content.res.AssetManager
import com.example.posterlifeapp.model.Poster
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class InspirationRepository() {

    val reader = JSONObject("posterlife.json")

    fun loadJSONPosterData(): List<Poster>? {
        var file = reader.getJSONArray()
        val formArray = ByteArray(file.available())
        file.read(formArray)
        file.close()
        val sType = object : TypeToken<List<Poster>>() {}.type
        return Gson().fromJson(String(formArray), sType)
    }
}