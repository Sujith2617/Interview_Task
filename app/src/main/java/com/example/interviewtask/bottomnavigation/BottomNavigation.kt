package com.example.interviewtask.bottomnavigation


import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.graphics.vector.ImageVector

import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState


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

