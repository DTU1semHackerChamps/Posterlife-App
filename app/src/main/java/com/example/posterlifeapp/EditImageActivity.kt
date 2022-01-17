package com.example.posterlifeapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants
import java.io.File


class EditImageActivity : Activity() {
    lateinit var imgedit : ImageView

    var path : String? = ""
    lateinit var inputImageUri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.imageediting)

        imgedit=findViewById(R.id.imgedit)

        val imageUri = intent.getStringExtra("imageUri")
        path = imageUri

        val imgfile : File = File(path)

        if(imgfile.exists())
        {
            val bitmp : Bitmap = BitmapFactory.decodeFile(imgfile.absolutePath)
            imgedit.setImageBitmap(bitmp)
        }

        inputImageUri=Uri.fromFile(File(path))

        startEditing()


        /*
        setContent {
            PosterLifeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    //val intent = getIntent()
                    val imageUri = intent.getStringExtra("imageUri")
                    Box() {
                        Image(
                            painter = painterResource(id = R.drawable.ic_close_black_24dp),
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .clickable(
                                    onClick = {

                                    },
                                    enabled = true
                                )
                                .size(50.dp)
                                .padding(5.dp),
                        contentDescription = "Back"

                        )

                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = rememberImagePainter(imageUri),
                            contentDescription = "Captured image"
                        )
                        Row(modifier = Modifier,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            EditingButton(string = "Beskær", icon = R.drawable.ic_baseline_crop_24)
                            EditingButton(string = "Filter", icon = R.drawable.ic_baseline_filter_b_and_w_24)
                        }
                    }


                }
            }
        }
        */
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK)
        {
            when(requestCode)
            {
                200->
                {
                    var outputUri: Uri? = data!!.data
                    imgedit.setImageURI(outputUri)
                    Log.d(TAG, "Uri result: "+ outputUri.toString())
                    val sendBackIntent = Intent(this,CustomPhotoPreviewActivity::class.java)
                    sendBackIntent.putExtra("imageUri",outputUri.toString())
                    this.startActivity(sendBackIntent)
                }

            }
        }
    }

    /**
     * function for starting the editing API
     * also calls functions for customizing the UI
     */
    @SuppressLint("ResourceType")
    fun startEditing()
    {
        var dsPhotoEditorIntent : Intent = Intent(this,DsPhotoEditorActivity::class.java)
        dsPhotoEditorIntent.setData(inputImageUri)

        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, "Posterlife")
        val toolsToHide = intArrayOf( DsPhotoEditorActivity.TOOL_DRAW, DsPhotoEditorActivity.TOOL_FRAME, DsPhotoEditorActivity.TOOL_PIXELATE, DsPhotoEditorActivity.TOOL_ROUND, DsPhotoEditorActivity.TOOL_STICKER, DsPhotoEditorActivity.TOOL_VIGNETTE, DsPhotoEditorActivity.TOOL_WARMTH)


        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_MAIN_BACKGROUND_COLOR, android.graphics.Color.parseColor("#3D3D3A"))
        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_TOOL_BAR_BACKGROUND_COLOR, android.graphics.Color.parseColor("#FF202020"))
        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_TOOL_CROP_DRAWABLE, R.drawable.crop)
        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_TOOL_FILTER_DRAWABLE, R.drawable.filter)
        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_TOOL_TEXT_DRAWABLE, R.drawable.text)
        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_TOOL_CONTRAST_DRAWABLE, R.drawable.contrast)
        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_TOOL_EXPOSURE_DRAWABLE, R.drawable.exposure)
        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_TOOL_SATURATION_DRAWABLE, R.drawable.saturation)
        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_TOP_BUTTON_CANCEL_DRAWABLE, R.drawable.back_arrow)
        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_TOP_BUTTON_APPLY_DRAWABLE, R.drawable.done)
        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_CUSTOM_VIEW, true)
        //dsPhotoEditorIntent.putExtra(DsPhotoEditorActivity.LAYOUT_INFLATER_SERVICE, R.layout.activity_ds_photo_editor)

        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE, toolsToHide)
        startActivityForResult(dsPhotoEditorIntent, 200)
    }

}
/*
@Composable
fun EditingButton(string: String, icon: Int)
{
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(130.dp)
            .height(50.dp)
            .clickable {

            },
        elevation = 8.dp,
        backgroundColor = MaterialTheme.colors.secondary
    )
    {
        Image(painter = painterResource(id = icon), contentDescription = string)
        Text(
            text = string,
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center,
        )
    }


}

 */


