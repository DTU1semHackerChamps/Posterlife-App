package com.example.posterlifeapp

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import ja.burhanrashid52.photoeditor.PhotoEditor
import ja.burhanrashid52.photoeditor.PhotoEditorView

class PhotoEditor : AppCompatActivity() {
    lateinit var mPhotoEditor : PhotoEditor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mPhotoEditorView = findViewById<PhotoEditorView>(R.id.photoEditorView)
        mPhotoEditorView.source.setImageResource(R.drawable.blank_image)
        setContentView(R.layout.activity_photo_editor)

        // custom font
        val mTextRobotoTF : Typeface? = ResourcesCompat.getFont(this, R.font.roboto_medium)

        mPhotoEditor = PhotoEditor.Builder(this,mPhotoEditorView)
            .setPinchTextScalable(true)
            .setClipSourceImage(true)
            .setDefaultTextTypeface(mTextRobotoTF)
            .build()
    }

}