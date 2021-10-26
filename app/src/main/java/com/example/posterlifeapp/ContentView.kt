package com.example.posterlifeapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ContentView {
}

@Composable
fun InspirationScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Green)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Inspiration View",
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
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        ) {
        
        items(5) {
            Spacer(modifier = Modifier.padding(14.dp))
            Row (modifier = Modifier.height(50.dp)){
                Image(
                    painter = painterResource(id = R.drawable.ic_share_black_24dp),
                    contentDescription = "",
                )
                Spacer(
                    modifier = Modifier.padding(8.dp)
                )
                Text(text = "Item", fontSize = 20.sp)

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShareScreenPreview(){
    ShareScreen()
}
