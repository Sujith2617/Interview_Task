package com.example.interviewtask.bottomnavigation.presentations

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.interviewtask.viewmodel.UploadViewmodel


@Composable
fun CreationsScreen(navController: NavController,viewmodel: UploadViewmodel) {

    val context = LocalContext.current
    val images by viewmodel.savedImages.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (images.isEmpty())
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No saved images")
            }


        else {

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp)
            ) {

                items(images) { image ->

                    Column ( horizontalAlignment = Alignment.CenterHorizontally) {
                        Card(
                            modifier = Modifier
                                .padding(8.dp).wrapContentWidth()
                        ) {
                            AsyncImage(
                                model = image.imageUrl,
                                contentDescription = null,
                                modifier = Modifier
                                    .height(200.dp)
                            )
                        }

                        Button({viewmodel.deleteImages(image)
                            Toast.makeText(context,"Deleted", Toast.LENGTH_SHORT).show()
                        } , colors = ButtonDefaults.buttonColors(containerColor = Color.Black)) {

                            Icon(Icons.Default.Delete, contentDescription = "delete", modifier = Modifier.size(20.dp))

                        }
                    }
                }
            }
        }
    }
}
