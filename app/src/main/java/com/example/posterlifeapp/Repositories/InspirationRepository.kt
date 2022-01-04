package com.example.posterlifeapp.Repositories

import android.content.res.AssetManager
import com.example.posterlifeapp.model.Poster
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class InspirationRepository(JSONassets: AssetManager) {

    var json = JSONassets

    fun loadJSONPosterData(): List<Poster>? {
        var file = json.open("posterlife.json")
        val formArray = ByteArray(file.available())
        file.read(formArray)
        file.close()
        val sType = object : TypeToken<List<Poster>>() {}.type
        return Gson().fromJson(String(formArray), sType)
    }
}