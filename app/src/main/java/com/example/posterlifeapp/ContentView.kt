package com.example.posterlifeapp

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.booleanResource
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.posterlifeapp.Repositories.Utils
import com.example.posterlifeapp.model.Poster
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import java.net.URL
import io.paperdb.Paper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.JdkConstants

class ContentView {
}

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun InspirationScreen(assets: AssetManager, viewModel: ContentViewModel) {

        val util = Utils(assets)
        val posters: List<Poster>
        util.postersFromAPI()
        posters = util.posters


        LazyVerticalGrid(
            cells = GridCells.Adaptive(minSize = 180.dp),
        ) {
            items(posters.size) { index ->
                SinglePicAndText(
                    imageID = posters[index].imageUrl,
                    title = posters[index].title,
                    viewModel
                )
            }
        }

    }


    @Composable
    fun SinglePicAndText(imageID: String, title: String, viewModel: ContentViewModel) {
        val image: Painter = rememberImagePainter(
            data = imageID,
            builder = {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
            })
        var dialogState = remember { mutableStateOf(false) }
        Column(modifier = Modifier
            .padding(10.dp)
            .clickable { dialogState.value = !dialogState.value }
        ) {
            Image(
                painter = image,
                modifier = Modifier
                    .size(265.dp)
                    .shadow(
                        elevation = 20.dp,
                        clip = true
                    ),
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
            if (dialogState.value) {
                AlertDialog(
                    backgroundColor = Color.Transparent,
                    onDismissRequest = {
                        dialogState.value = false
                    },

                    text = {
                        // Column( modifier = Modifier.fillMaxSize()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .scale(1.2f)
                                .weight(1f),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Image(painter = image, contentDescription = title)
                        }

                        // }

                    },
                    buttons = {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
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
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(4.dp),
                                onClick = { dialogState.value = false }
                            ) {
                                Text("Tilbage")
                            }
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(4.dp),
                                onClick = {
                                    GlobalScope.launch(Dispatchers.Main) {
                                        async {
                                            SyncCart(title, viewModel)
                                        }
                                        dialogState.value = false
                                    }
                                }
                            ) {
                                Text("Tilføj til kurv")
                            }
                        }
                    }
                )
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun InspirationScreenPreview() {
        //InspirationScreen()
    }

    @Composable
    fun ProfileScreen() {
        val profilesIC = listOf(
            "Ønskeliste",
            "Ordrer",
            "Mine Designs",
            "Hurtigt køb",
            "GDPR",
            "Betalingsoplysninger"
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
    fun ProfileScreenPreview() {
        ProfileScreen()
    }

    @Composable
    fun ShareScreen() {

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
            itemsIC.forEach { item ->

                SocialList(id = item.icon, name = item.title)
                Divider(
                    thickness = 2.dp,
                    color = Color.Gray
                )
            }

        }
    }

    @Composable
    fun SocialList(id: Int, name: String) {

        Row(
            modifier = Modifier
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
    fun ShareScreenPreview() {
        ShareScreen()
    }

    @Composable
    //@Preview
    fun DisplayCart(assets: AssetManager){
        val util = Utils(assets)
        val posters: List<Poster>
        util.postersFromAPI()
        posters = util.posters
    if(Paper.book().read<List<String>>("Titles")!!.isNotEmpty()){
            Column(
                modifier = Modifier.padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10)),
                    horizontalArrangement = Arrangement.Start
                ) {
                    ElementInCart(imageID = posters[0].imageUrl, title = posters[0].title)
                }
            }
        }
    }
    @Composable
    fun ElementInCart(imageID: String, title: String ){
        val image: Painter = rememberImagePainter(
            data = imageID,
            builder = {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
            })

        Row(
            Modifier
                .background(Color.LightGray)
                .fillMaxWidth()
                .padding(4.dp)
            ,
        ) {
            Image(
                painter = image,
                modifier = Modifier
                    .size(100.dp),
                alignment = Alignment.Center,
                contentDescription = title
            )
            Text(
                text = title + "\nPris: ",
            )
            Box(modifier = Modifier
                .size(25.dp)
                .clip(CircleShape)
                .background(Color.Red)
                ){
                Text( modifier = Modifier
                    .align(Alignment.TopCenter),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    text = "-",
                )
            }
        }
    }

    suspend fun SyncCart(title: String, viewModel: ContentViewModel) {
        var titles = mutableListOf<String>()
        if (Paper.book().read<List<String>>("Titles") != null) {
            titles = Paper.book().read<List<String>>("Titles") as MutableList<String>
        }
        titles.add(title)
        if (Paper.book().read<List<String>>("Titles") != null) {
            Paper.book().delete("Titles")
        }
        Paper.book().write("Titles", titles)
        viewModel.cartAmount = Paper.book().read<List<String>>("Titles")!!.size


        val hej = Paper.book().read<List<String>>("Titles")
        Log.d(TAG, "SyncCart: $hej")
    }

