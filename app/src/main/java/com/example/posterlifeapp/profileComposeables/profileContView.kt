package com.example.posterlifeapp.profileComposeables

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.posterlifeapp.profileComposeables.ui.theme.PosterLifeAppTheme

class profileContView {

}

@Composable
fun ProfileInfo()
{
    var editProfile by remember { mutableStateOf(0) }
    val notification = rememberSaveable { mutableStateOf("") }
    if(notification.value.isNotEmpty()){
        Toast.makeText(LocalContext.current, notification.value, Toast.LENGTH_LONG).show()
        notification.value = ""
    }


    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .padding(8.dp)
    )
    {
        Row(modifier= Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End)
        {
            if(editProfile == 1) {
                Text(text = "Gem",
                    modifier = Modifier.clickable {
                        notification.value = "Profil opdateret"
                        editProfile = 0
                    }
                )
            } else {
                Text(text = "Rediger",
                    modifier = Modifier.clickable {
                        notification.value = "Redigere Profil"
                        editProfile = 1
                    }
                )
            }
        }
    }
}





