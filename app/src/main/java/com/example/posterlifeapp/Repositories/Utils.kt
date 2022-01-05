package com.example.posterlifeapp.Repositories

import android.content.ContentValues.TAG
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.posterlifeapp.model.Poster
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class Utils( val assets : AssetManager) {

    fun postersFromAPI() : List<Poster>
    {
        val jsonString = assets.open("posterlife.json")
        val size = jsonString.available()
        val foo = ByteArray(size)
        jsonString.read(foo)
        jsonString.close()
        val js = String(foo)
        val typeToken = object : TypeToken<List<Poster>>() {}.type

        return Gson().fromJson(js, typeToken)
    }

    fun posterBitMap(url: String) : InputStream
    {
        println(url)
        val nurl = URL(url)
        val connection = nurl.openConnection()
        connection.connect()
        val input = connection.getInputStream()
        return input
    }
}