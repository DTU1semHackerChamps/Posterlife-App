package com.example.posterlifeapp.Repositories

import android.content.ContentValues.TAG
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import com.example.posterlifeapp.model.Poster
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection
import java.util.*

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

    fun posterBitMap(url: String) : Bitmap
    {
        val url = URL(url)
        val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
        return bmp
    }
}