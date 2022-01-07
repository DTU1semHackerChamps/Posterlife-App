package com.example.posterlifeapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.impl.utils.ContextUtil.getApplicationContext
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter.State.Empty.painter
import coil.compose.rememberImagePainter
import com.example.composephoto.camera.CameraCapture
import com.example.posterlifeapp.ui.theme.PosterLifeAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.ExperimentalCoroutinesApi

class CameraActivity : ComponentActivity() {
    @ExperimentalCoroutinesApi
    @ExperimentalCoilApi
    @ExperimentalPermissionsApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PosterLifeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainContent(Modifier.fillMaxSize())
                }
            }
        }
    }
}

/**
 * Heavily inspired by David Pisoni's repo
 * https://github.com/gefilte/compose-photo-integration/tree/step-4-capture-image
 */
@ExperimentalCoilApi
@ExperimentalCoroutinesApi
@ExperimentalPermissionsApi
@Composable
fun MainContent(modifier: Modifier = Modifier){
    val emptyImgURI = Uri.parse("file://dev/null")
    var imageUri by remember { mutableStateOf(emptyImgURI) }
    val context = LocalContext.current
    if(imageUri != emptyImgURI){
        Box(modifier = modifier){
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_back_black_24dp),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .clickable(
                        onClick = {
                            imageUri = emptyImgURI
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

            Button(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                onClick = {
                    val intent = Intent(context,PhotoEditor::class.java)
                    intent.putExtra("imageUri", imageUri.toString())
                    context.startActivity(intent)

                }
            ) {
                Text("Continue")
            }

        }
    } else {
        CameraCapture(
            modifier = modifier,
            onImageFile = { file ->
                imageUri = file.toUri()
            }
        )
    }
}
@ExperimentalCoilApi
@Preview(showBackground = true)
@Composable
@ExperimentalPermissionsApi
@ExperimentalCoroutinesApi
fun CameraPreview(){
    MainContent(Modifier.fillMaxSize())
}
