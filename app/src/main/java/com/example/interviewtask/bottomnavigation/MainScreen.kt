package com.example.interviewtask.bottomnavigation


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.interviewtask.presentation.uploadToResultScreens.ImageSelection
import com.example.interviewtask.presentation.uploadToResultScreens.settingScreens.SettingsScreen
import com.example.interviewtask.viewmodel.TemplateViewModel
import com.example.interviewtask.viewmodel. UploadViewmodel
import com.example.interviewtask.presentation.uploadToResultScreens.ProcessingScreen
import com.example.interviewtask.presentation.uploadToResultScreens.ResultScreen


@Composable
fun MainScreen(viewModel: TemplateViewModel,viewmodelUpload: UploadViewmodel){

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigation(navController)
        }
    ) {paddingValues ->

        NavHost(navController,
            startDestination = Screens.Home.route,
            modifier = Modifier.padding(paddingValues)){


            composable(Screens.Home.route) {
                HomeScreen(viewModel,navController)
            }
            composable(Screens.Creations.route) {
                CreationsScreen(navController,viewmodelUpload)
            }
            composable(Screens.Settings.route) {
                SettingsScreen()
            }

            composable(Screens.ImageSelection.route, arguments =
                listOf(
                    navArgument("uri"){type = NavType.StringType}
                )){ backStackEntry ->

                val uri = backStackEntry.arguments?.getString("uri")

                ImageSelection(uri,navController,viewmodelUpload)

            }

            composable(Screens.Processing.route){
                ProcessingScreen(navController,viewmodelUpload)
            }

            composable(Screens.Result.route, arguments =
            listOf(
                navArgument("imageUrl"){ NavType.StringType}

            )){ backStackEntry ->
                val uri = backStackEntry.arguments?.getString("imageUrl")
                ResultScreen(uri,viewmodelUpload)
            }




        }


    }



}



@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    //MainScreen()
}