package com.example.posterlifeapp.viewModel

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.composephoto.camera.CameraCapture
import com.example.posterlifeapp.R
import com.example.posterlifeapp.viewModel.EditImageActivity
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
            Row(Modifier.align(Alignment.BottomCenter).fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(
                    modifier = Modifier
                        .padding(30.dp)
                        .width(110.dp),
                    onClick = {
                        imageUri = emptyImgURI
                    },
                    enabled = true
                ) {
                    Text("Retry", fontSize = 15.sp)
                }
                Button(
                    modifier = Modifier
                        .padding(30.dp)
                        .width(110.dp),
                    onClick = {
                        val intent = Intent(context, EditImageActivity::class.java)
                        intent.putExtra("imageUri", imageUri.path)
                        context.startActivity(intent)
                    }
                ) {
                    Text("Continue", fontSize = 15.sp)
                }
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
