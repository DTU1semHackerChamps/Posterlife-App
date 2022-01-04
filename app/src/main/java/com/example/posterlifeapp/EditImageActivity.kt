package com.example.posterlifeapp

import android.graphics.Paint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.posterlifeapp.ui.theme.PosterLifeAppTheme


class EditImageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                    }


                }
            }
        }
    }
}

