package com.example.posterlifeapp

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ContentView {
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InspirationScreen(){
    val posters = PosterList()
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        modifier = Modifier.padding(bottom = 33.dp)
    ){
        items(posters.size) { index ->
            SinglePicAndText(imageID = posters[index].poster, title = posters[index].title)
        }
    }



}

@Composable
fun SinglePicAndText(imageID: Int, title: String) {
    val image: Painter = painterResource(imageID)
    Column(modifier = Modifier.padding(4.dp)) {
        Image(
            painter = image,
            contentDescription = title,
//            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Row() {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun InspirationScreenPreview(){
    InspirationScreen()
}

@Composable
fun ProfileScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Red)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Profile View",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview(){
    ProfileScreen()
}

@Composable
fun ShareScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Blue)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Share View",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ShareScreenPreview(){
    ShareScreen()
}
