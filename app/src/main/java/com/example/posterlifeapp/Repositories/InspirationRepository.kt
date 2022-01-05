package com.example.posterlifeapp.Repositories

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import com.example.posterlifeapp.model.Poster
import com.google.gson.Gson
import com.google.gson.JsonSerializationContext
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.io.InputStream
import java.lang.reflect.Type

class InspirationRepository(JSONassets: AssetManager) {

    var json = JSONassets

    fun loadFromJson(): List<Poster>
    {
        var file = json.open("posterlife.json")
        val formArray = ByteArray(file.available())
        file.read(formArray)
        file.close()
        val sType = object : TypeToken<List<Poster>>() {}.type
        return Gson().fromJson(String(formArray), sType)
    }
}