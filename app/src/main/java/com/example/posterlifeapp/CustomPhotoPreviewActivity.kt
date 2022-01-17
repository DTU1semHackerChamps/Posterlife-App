package com.example.posterlifeapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.posterlifeapp.ui.theme.PosterLifeAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi

class CustomPhotoPreviewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PosterLifeAppTheme {
                // A surface container using the 'background' color from the theme
                val imageUri = intent.getStringExtra("imageUri")
                PreviewPhoto(Uri.parse(imageUri))
            }
        }
    }
}


@Composable
fun PreviewPhoto(imageUri: Uri)
{
    val context = LocalContext.current
    val sendBackIntent = Intent(context,MainActivity::class.java)
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = rememberImagePainter(data = imageUri),
            contentDescription = "Edited image",
            alignment = Alignment.Center,
            modifier = Modifier.padding(10.dp)
        )
        Row(verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(onClick =
            {
            context.startActivity(sendBackIntent)
            },
            ) {
                Text(text = "Continue")
            }
            Spacer(modifier = Modifier.width(10.dp))

            Button(onClick = { /*TODO*/ }) {
                Text(text = "Order")
            }
        }
    }



}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PosterLifeAppTheme {
        //PreviewPhoto(null)
    }
}