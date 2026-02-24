package com.example.interviewtask.bottomnavigation


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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.example.interviewtask.viewmodel.TemplateViewModel
import com.example.interviewtask.viewmodel.UploadViewmodel


data class BottomNavigationItems(
    val screens: Screens,
    val selectedIcon : ImageVector,
    val unselectedIcon : ImageVector
)

@Composable
fun BottomNavigation(navController: NavController){


    val items = listOf(
        BottomNavigationItems(
            Screens.Home,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        BottomNavigationItems(
            Screens.Creations,
            selectedIcon = Icons.Filled.List,
            unselectedIcon = Icons.Filled.List
        ),
        BottomNavigationItems(
            Screens.Settings,
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings
        )
    )


    val currentRoute =
        navController.currentBackStackEntryAsState().value?.destination?.route


    NavigationBar {
        items.forEachIndexed { index, items ->

            val isSelected =  currentRoute == items.screens.route

            NavigationBarItem(
                selected = isSelected ,

                onClick = {
                    navController.navigate(items.screens.route){
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = {

                    Icon(imageVector =
                        if (isSelected){
                            items.selectedIcon
                        }else{
                            items.unselectedIcon },

                        contentDescription = "icons")
                },
                label = {
                    Text(items.screens.route)
                }
            )

        }
    }

}

@Composable
fun HomeScreen(viewModel: TemplateViewModel,navController: NavController){

    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("App Title", fontSize = 15.sp, fontWeight = FontWeight.Bold)
    TemplatesCategoryScreen(viewModel,navController)
}}

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
