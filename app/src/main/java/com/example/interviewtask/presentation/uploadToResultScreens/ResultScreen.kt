package com.example.interviewtask.presentation.uploadToResultScreens


import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.interviewtask.viewmodel. UploadViewmodel


@Composable
fun ResultScreen (uri: String?,viewmodel: UploadViewmodel){


    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

        Card(modifier = Modifier.padding(top = 10.dp).wrapContentWidth().height(430.dp)) {

            AsyncImage(uri, contentDescription = "templates")

        }

        Button({
            viewmodel.lastReqId?.let { reqid->
                viewmodel.lastImageUrl?.let { url ->
                    viewmodel.insertImage(url)
                }
            }

            Toast.makeText(context,"Saved to collection ", Toast.LENGTH_SHORT).show()

        }, modifier = Modifier.padding(top = 10.dp)) {
            Text("Save image")
        }

    }

}