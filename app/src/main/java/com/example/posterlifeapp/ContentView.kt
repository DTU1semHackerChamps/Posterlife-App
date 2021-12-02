package com.example.posterlifeapp

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    var dialogState = remember { mutableStateOf(false) }
    Column(modifier = Modifier
        .padding(4.dp)
        .clickable { dialogState.value = !dialogState.value }) {
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
        if (dialogState.value) {
            AlertDialog(
                backgroundColor = Color.Transparent,
                onDismissRequest = {
                    dialogState.value = false
                },

                text = {
                   // Column( modifier = Modifier.fillMaxSize()) {
                        Row(modifier = Modifier.fillMaxWidth().weight(1f)){
                            Image(painter = image, contentDescription = title )
                        }

                   // }

                },
                buttons = {
                    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = title,
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp
                        )
                    }
                    Row(
                        modifier = Modifier.padding(all = 8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Button(
                            modifier = Modifier.fillMaxWidth().weight(1f).padding(4.dp),
                            onClick = { dialogState.value = false }
                        ) {
                            Text("Tilbage")
                        }
                        Button(
                            modifier = Modifier.fillMaxWidth().weight(1f).padding(4.dp),
                            onClick = {
                                dialogState.value = false
                                //TODO gå til betaling
                            }
                        ) {
                            Text("Bestil")
                        }
                    }
                }
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
