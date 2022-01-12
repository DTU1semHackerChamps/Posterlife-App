package com.example.posterlifeapp.Repositories

import android.content.ContentValues.TAG
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.posterlifeapp.model.Poster
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.coroutineScope
import java.io.File
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL
import java.nio.file.Files

class Utils( val assets : AssetManager){

    lateinit var posters : List<Poster>

    fun postersFromAPI()
    {
        val jsonString = assets.open("posterlife.json")
        val size = jsonString.available()
        val foo = ByteArray(size)
        jsonString.read(foo)
        jsonString.close()
        val js = String(foo)
        val typeToken = object : TypeToken<List<Poster>>() {}.type

        posters = Gson().fromJson(js, typeToken)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun postersFromInternal()
    {
        val posterFileArray: Array<File>?

        val dirPath = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        val dirrePath = MediaStore.Images.Media.DISPLAY_NAME
        Log.d(TAG, "postersFromInternal: $dirrePath")
        val directory = File(dirPath.toString())
        if(directory.exists() && directory.isDirectory)
        {
            posterFileArray = directory.listFiles()
            for (uri in posterFileArray)
            {

                Log.d(TAG, "postersFromInternal: ")
            }
        }



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