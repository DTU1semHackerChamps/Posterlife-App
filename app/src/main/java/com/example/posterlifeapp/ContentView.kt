package com.example.posterlifeapp

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.icu.text.CaseMap
import android.util.Log
import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.*
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.filled.ArrowDownward
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.SemanticsProperties.EditableText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
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
import java.nio.file.WatchEvent
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


    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun ProfileScreen() {
        val profilesIC = listOf(
            "Person Oplysninger",
            "Ordrer",
            "Mine Designs",
            "Betalingsoplysninger"
        )

        Column(
            modifier =  Modifier
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_posterlife_logo_sort_svg),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .size(120.dp)
            )

            Spacer(modifier = Modifier.height(80.dp))
            LazyVerticalGrid(cells = GridCells.Fixed(2),

                )
            {
                items(profilesIC.size){ index ->
                    ProfButton(string = profilesIC[index])
                }
            }
            Spacer(modifier = Modifier.height(50.dp))


            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfButton(string = "GDPR")
            }

        }


        /*
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

         */
    }

    @Composable
    fun ProfButton(string: String)
    {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .width(180.dp)
                .height(50.dp)
                .clickable {

                },
            elevation = 8.dp,
            backgroundColor = MaterialTheme.colors.secondary
        )
        {
            Text(
                text = string,
                modifier = Modifier.padding(13.dp),
                textAlign = TextAlign.Center
                )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun ProfileScreenPreview() {
        //ProfileScreen()
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

    @ExperimentalComposeUiApi
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

    @ExperimentalComposeUiApi
    @Composable
    fun ElementInCart(imageID: String, title: String ){
        val image: Painter = rememberImagePainter(
            data = imageID,
            builder = {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
            })
        val textState = remember { mutableStateOf(TextFieldValue("1")) }

        Card(
            shape = RoundedCornerShape(10),
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .shadow(
                    elevation = 10.dp
                )
        ) {
            Row(
                modifier = Modifier.padding(4.dp)
            ) {
                Column(
                    modifier = Modifier,
                ) {
                    Image(
                        painter = image,
                        modifier = Modifier
                            .size(100.dp),
                        alignment = Alignment.Center,
                        contentDescription = title
                    )
                }
                Column() {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                        ,
                    ) {
                        Text(
                            text = title + "\nPris: ",
                        )
                    }
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                    ){
                        FloatingActionButton(
                            modifier = Modifier
                                .scale(0.5f),
                            backgroundColor = Color(49,54,57),
                            onClick = {
                                var temp = textState.value.text.toInt()
                                temp--
                                if(temp >= 0){
                                    textState.value = TextFieldValue(temp.toString())
                                }


                            }) {
                            Icon(Icons.Filled.Remove, "",
                                modifier = Modifier.scale(1.5f),
                                tint = Color.White
                            )
                        }

                        val keyboardController = LocalSoftwareKeyboardController.current
                        val focusManager = LocalFocusManager.current
                        OutlinedTextField(
                            value = textState.value,
                            onValueChange = {
                                textState.value = it
                                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier
                                .width(55.dp),
                            singleLine = true,
                            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                            keyboardActions = KeyboardActions(
                                onDone = {keyboardController?.hide(); focusManager.clearFocus(true)})
                        )
                        FloatingActionButton(
                            modifier = Modifier
                                .scale(0.5f),
                            backgroundColor = Color(49,54,57),
                            onClick = {
                                var temp = textState.value.text.toInt()
                                temp++
                                textState.value = TextFieldValue(temp.toString())
                            }) {
                            Icon(Icons.Filled.Add, "",
                                modifier = Modifier.scale(1.5f),
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }

    @ExperimentalComposeUiApi
    @Preview
    @Composable
    fun ElementInCartPreview(){
        ElementInCart(imageID = "https://posterlife.dk/wp-content/uploads/2019/05/Levende_soeren_ulrik_thomsen.jpg", "\"Levende · Søren Ulrik Thomsen (1981)\"")
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

