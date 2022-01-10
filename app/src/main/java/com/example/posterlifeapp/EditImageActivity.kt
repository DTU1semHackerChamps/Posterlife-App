package com.example.posterlifeapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants
import com.example.posterlifeapp.ui.theme.PosterLifeAppTheme
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
        System.err.println(inputImageUri)
        edit_trial()
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
                }

            }
        }
    }

    public fun edit_trial()
    {
        var dsPhotoEditorIntent : Intent = Intent(this,DsPhotoEditorActivity::class.java)
        dsPhotoEditorIntent.setData(inputImageUri)

        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, "posterlifeapp")

        val toolsToHide = arrayOf<Int>(DsPhotoEditorActivity.TOOL_ORIENTATION, DsPhotoEditorActivity.TOOL_CROP)

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


