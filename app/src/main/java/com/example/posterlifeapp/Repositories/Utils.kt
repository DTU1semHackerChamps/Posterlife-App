package com.example.posterlifeapp.Repositories

import android.content.ContentValues.TAG
import android.content.res.AssetManager
import android.util.Log
import com.example.posterlifeapp.model.Poster
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class Utils( val assets : AssetManager) {

    fun postersFromAPI()
    {
        val jsonString = assets.open("posterlife.json")
        jsonString.read()
        jsonString.close()
        val typeToken = object : TypeToken<List<Poster>>() {}.type
        val poster = Gson().fromJson<List<Poster>>(jsonString, typeToken)

    }

}