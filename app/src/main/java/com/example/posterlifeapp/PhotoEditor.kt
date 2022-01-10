package com.example.posterlifeapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import ja.burhanrashid52.photoeditor.PhotoEditor
import ja.burhanrashid52.photoeditor.PhotoEditorView
import java.io.IOException

var mPhotoEditor: PhotoEditor? = null

class PhotoEditor : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_editor)
        val mPhotoEditorView = findViewById<PhotoEditorView>(R.id.photoEditorView)



        //mPhotoEditorView.source.setImageResource(R.drawable.blank_image)


        // custom font
        val mTextRobotoTF : Typeface? = ResourcesCompat.getFont(this, R.font.roboto_medium)

        mPhotoEditor = PhotoEditor.Builder(this,mPhotoEditorView)
            //.setPinchTextScalable(true)
            //.setClipSourceImage(true)
            //.setDefaultTextTypeface(mTextRobotoTF)
            .build()
    }

}