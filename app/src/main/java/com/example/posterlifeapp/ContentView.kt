package com.example.posterlifeapp

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.nfc.Tag
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toDrawable
import coil.Coil
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.posterlifeapp.Repositories.InspirationRepository
import com.example.posterlifeapp.Repositories.Utils

class ContentView {
}


@ExperimentalFoundationApi
@Composable
fun InspirationScreen(assets : AssetManager){
    val util = Utils(assets)
    val posters = util.postersFromAPI()

    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        modifier = Modifier.padding(bottom = 50.dp)
    ){
        items(posters.size) { index ->
            SinglePicAndText(imageID = util.posterBitMap(posters[index].imageUrl),
                title = posters[index].title)
        }
    }
}


@Composable
fun SinglePicAndText(imageID: Bitmap, title: String) {

    Column(modifier = Modifier.padding(4.dp)) {
        Image(
            bitmap = imageID.asImageBitmap(),
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
    //InspirationScreen()
}

@Composable
fun ProfileScreen() {
    val profilesIC = listOf("Ønskeliste", "Ordrer", "Mine Designs", "Hurtigt køb", "GDPR", "Betalingsoplysninger")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.Start

    ) {
        Divider(
            thickness = 2.dp,
            color = Color.Gray
        )
        profilesIC.forEach { item ->
            Text(text = item, Modifier.padding(start = 10.dp))

            Divider(
                thickness = 2.dp,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview(){
    ProfileScreen()
}

@Composable
fun ShareScreen(){

    val itemsIC = listOf(
        SocialMediaItem.FacebookIC,
        SocialMediaItem.TwitterIC,
        SocialMediaItem.Pinterest,
        SocialMediaItem.InstagramIC,
        SocialMediaItem.Email
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.Start
        ) {

        Divider(
            thickness = 2.dp,
            color = Color.Gray
        )
        itemsIC.forEach{ item ->

            SocialList(id = item.icon, name = item.title )
            Divider(
                thickness = 2.dp,
                color = Color.Gray
            )
        }

    }
}
@Composable
fun SocialList(id: Int, name: String)
{

    Row (modifier = Modifier
        .height(40.dp)
        .padding(start = 120.dp)
    ) {
        Image(
            painter = painterResource(id = id),
            contentDescription = name,
            modifier = Modifier.size(40.dp)
        )
        Spacer(
            modifier = Modifier.padding(8.dp)
        )
        Text(text = name, fontSize = 25.sp)
    }

}

@Preview(showBackground = true)
@Composable
fun ShareScreenPreview(){
    ShareScreen()
}
