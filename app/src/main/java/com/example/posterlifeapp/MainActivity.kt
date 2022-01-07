package com.example.posterlifeapp

import android.content.ContentValues.TAG
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.res.AssetManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.TextView

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.posterlifeapp.Repositories.Utils
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.composephoto.camera.CameraCapture
import com.example.posterlifeapp.ui.theme.PosterLifeAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import io.paperdb.Paper
import kotlinx.coroutines.ExperimentalCoroutinesApi

typealias LumaListener = (luma: Double) -> Unit


class MainActivity : ComponentActivity() {

    private lateinit var jsonAssests: AssetManager
    private lateinit var util : Utils
    private val viewModel: ContentViewModel by viewModels()
    private val title = mutableStateOf("string")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Paper.init(this)

        setContent {
            jsonAssests = applicationContext.assets
            PosterLifeAppTheme {
                // A surface container using the 'background' color from the theme
//                Surface(color = MaterialTheme.colors.background) {
//                    Greeting("Android")
//                }
                MainScreen()
            }
        }
    }


    @Composable
    fun MainScreen() {
        val navController = rememberNavController()
        Scaffold(
            topBar = { TopBar() },
            bottomBar = { BottomNaigationBar(navController) },
            floatingActionButton = { NewPosterButton() }
        ) {
            Navigation(navController)

        }
    }

    @Preview(showBackground = true)
    @Composable
    fun MainScreenPreview() {
        MainScreen()
    }


    @Composable
    fun TopBar() {
        TopAppBar(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = Color.White,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(modifier = Modifier.height(32.dp)) {

            Box( modifier = Modifier
                .align(Alignment.TopEnd)) {
                Image(painter = painterResource(id = R.drawable.ic_shopping_cart_white_24dp),
                    modifier = Modifier
                        .background(Color.Transparent, shape = CircleShape)
                        .padding(5.dp, 5.dp, 13.dp, 5.dp)
                        .clickable { }
                        .scale(1.5f),
                    contentDescription = "Indkøbskurv")
                Box(modifier = Modifier
                    .size(15.dp)
                    .clip(CircleShape)
                    .background(Color.Red)
                    .align(Alignment.BottomCenter)
                    .fillMaxSize(),){
                    Text( modifier = Modifier
                        .align(Alignment.Center),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        text = viewModel.cartAmount.toString(),
                    )
                }
            }


            Row(Modifier.fillMaxSize()) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        text = title.value,
                        fontSize = 24.sp,

                    )

                }
            }

        }
    }

    @Preview(showBackground = true)
    @Composable
    fun TopBarPreview() {
        TopBar()
    }

    @Composable
    fun BottomNaigationBar(navController: NavController) {
        val items = listOf(
            NavigationItem.Inspiration,
            NavigationItem.Profile,
            NavigationItem.Share
        )
        BottomNavigation(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = Color.White
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            items.forEach { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painterResource(id = item.icon),
                            contentDescription = item.title
                        )
                    },
                    label = { Text(text = item.title) },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.White.copy(0.4f),
                    alwaysShowLabel = true,
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }

                    }
                )
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun BottomNavigationBarPreview() {
        //BottomNaigationBar()
    }

@Composable
fun NewPosterButton() {
    val context = LocalContext.current
    ExtendedFloatingActionButton(
        text = { Text(text = "Ny Plakat") },
        onClick = {
            context.startActivity(Intent(context,CameraActivity::class.java))
        },
        icon = { Icon(Icons.Filled.Add, "") }
    )

    }


    @Composable
    fun Navigation(navController: NavHostController) {
        NavHost(navController, startDestination = NavigationItem.Inspiration.route) {
            composable(NavigationItem.Inspiration.route) {
                InspirationScreen(jsonAssests, viewModel)
                title.value = viewModel.titleList[0]
                Log.d(TAG, "Navigation: ${viewModel.title}")
            }
            composable(NavigationItem.Profile.route) {
                ProfileScreen()
                title.value = viewModel.titleList[1]
                Log.d(TAG, "Navigation: ${viewModel.title}")
            }
            composable(NavigationItem.Share.route) {
                ShareScreen()
                title.value = viewModel.titleList[2]
                Log.d(TAG, "Navigation: ${viewModel.title}")
            }

        }
    }
}

