package com.example.posterlifeapp

import android.content.res.AssetManager
import android.graphics.Bitmap
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.posterlifeapp.Repositories.Utils
import com.example.posterlifeapp.model.Poster
import com.google.accompanist.coil.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import java.net.URL

class ContentView {
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InspirationScreen(assets : AssetManager ){

    val util = Utils(assets)
    val posters :List<Poster>
    util.postersFromAPI()
    posters = util.posters


    LazyVerticalGrid(
        cells = GridCells.Adaptive(minSize = 140.dp),
    ){
        items(posters.size) { index ->
            SinglePicAndText(imageID = posters[index].imageUrl,
                title = posters[index].title)
        }
    }

}



@Composable
fun SinglePicAndText(imageID: String, title: String) {

    Column(modifier = Modifier.padding(10.dp)) {
        Image(painter = rememberImagePainter(
            data = imageID,
            builder = {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
            }),
            modifier = Modifier.size(140.dp),
            alignment = Alignment.Center,
            contentDescription = title
        )
        Row() {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontSize = 18.sp
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
