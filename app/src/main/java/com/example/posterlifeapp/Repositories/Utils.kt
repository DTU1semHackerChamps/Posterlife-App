package com.example.posterlifeapp.Repositories

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import com.example.posterlifeapp.model.Poster
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.coroutineScope
import java.io.File
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path

class Utils( val assets : AssetManager){

    lateinit var posters : List<Poster>

    fun postersFromAPI(): List<Poster>
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


    fun postersFromInternal() : List<Poster>?
    {
        val posterFileArray: Array<File>?
        var posterList = mutableListOf<Poster>()
        val directory = File("/sdcard/Pictures/posterlifeapp")
        Log.d(TAG, "postersFromInternal: $directory")

        if(directory.exists())
        {
            posterFileArray = directory.listFiles()
            if(posterFileArray != null) {
                for (posterFile in posterFileArray) {
                    val poster : Poster = Poster(
                        posterFile.name,"",posterFile.toUri().toString(),
                        100,100,100,
                        BitmapFactory.decodeFile(posterFile.toURI().path)
                    )
                    posterList.add(poster)
                }
                return posterList.toList()
            }
        }

        return posterList.toList()
    }

    fun createPosterBitMaps()
    {

        for (poster in posters) {
            val url = URL(poster.imageUrl)
            val connection = url.openConnection()
            connection.connect()
            val input = connection.getInputStream()
            val bm = BitmapFactory.decodeStream(input)
            if (bm == null) println("WHat?")
            poster.bitmap = bm
        }

    }
}