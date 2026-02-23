package com.example.interviewtask.presentation.uploadToResultScreens.settingScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.interviewtask.R


@Composable
fun SettingsScreen() {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Row(verticalAlignment = Alignment.CenterVertically)
        {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "arrow")
            Text("Settings", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(6.dp))
        }

        LazyColumn() {


            items(settingsItemsList) { item ->

                SettingRow(item)
            }

        }
    }
}


data class SettingItem(
    val title: String,
    val icon : Int,
    val hasSwitch : Boolean = false
)


val settingsItemsList = listOf(
    SettingItem("Language", R.drawable.outline_language_24 ,),
    SettingItem("Creations", R.drawable.outline_files_24,),
    SettingItem("Rate Us", R.drawable.outline_thumb_up_24,),
    SettingItem("Share App", R.drawable.baseline_share_24,),
    SettingItem("Privacy Policy", R.drawable.outline_security_24,
    )
)