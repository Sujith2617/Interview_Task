package com.example.interviewtask.presentation.uploadToResultScreens.settingScreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun SettingRow(item : SettingItem){

    Row( modifier = Modifier
        .fillMaxWidth()
        .clickable(enabled = !item.hasSwitch) {

        }
        .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Icon(painter = painterResource(item.icon), contentDescription = item.title)

        Spacer(modifier = Modifier.width(16.dp))

        Text(item.title, modifier = Modifier.weight(1f))


    }
}

