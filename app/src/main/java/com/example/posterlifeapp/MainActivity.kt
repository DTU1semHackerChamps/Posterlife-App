package com.example.posterlifeapp

import android.content.res.Resources
import android.content.Intent
import android.net.Uri
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.posterlifeapp.ui.theme.PosterLifeAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.ExperimentalCoroutinesApi

typealias LumaListener = (luma: Double) -> Unit


class MainActivity2 : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PosterLifeAppTheme {
                // A surface container using the 'background' color from the theme
//                Surface(color = MaterialTheme.colors.background) {
//                    Greeting("Android")
//                }
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopBar()},
        bottomBar = { BottomNaigationBar(navController)},
        floatingActionButton = { NewPosterButton()}
    ) {
        Navigation(navController)

    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview(){
    MainScreen()
}



@Composable
fun TopBar() {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White,
        modifier = Modifier.fillMaxWidth()
    ){
        Box(modifier = Modifier.height(32.dp)){

            Row(Modifier.fillMaxSize()) {

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    text = stringResource(id = R.string.app_name),
                    fontSize = 24.sp
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
fun BottomNaigationBar(navController: NavController){
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
        items.forEach{ item ->
            BottomNavigationItem(
                icon = {Icon(painterResource(id = item.icon), contentDescription = item.title)},
                label = { Text(text = item.title)},
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route){
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
fun NewPosterButton(){
    val context = LocalContext.current
    ExtendedFloatingActionButton(
        text = { Text(text = "Ny Plakat") },
        onClick = { context.startActivity(Intent(context, CameraActivity::class.java)) },
        icon ={ Icon(Icons.Filled.Add,"")}
    )

}


@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItem.Inspiration.route) {
        composable(NavigationItem.Inspiration.route) {
            InspirationScreen()

        }
        composable(NavigationItem.Profile.route) {
            ProfileScreen()
        }
        composable(NavigationItem.Share.route) {
            ShareScreen()
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
    if(imageUri != emptyImgURI){
        Box(modifier = modifier){
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = rememberImagePainter(imageUri),
                contentDescription = "Captured image"
            )
            Button(
                modifier = Modifier.align(Alignment.BottomCenter),
                onClick = {
                    imageUri = emptyImgURI
                }
            ) {
               Text("Remove image")
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

