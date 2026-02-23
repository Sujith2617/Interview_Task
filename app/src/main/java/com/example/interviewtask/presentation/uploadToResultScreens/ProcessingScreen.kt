package com.example.interviewtask.presentation.uploadToResultScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.interviewtask.UploadViewmodel
import com.example.interviewtask.bottomnavigation.Screens

@Composable
fun ProcessingScreen(navController: NavController,viewmodel: UploadViewmodel){

    val state = viewmodel.state

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {


        when(state){
            is UploadUiState.Idle->{

            }

            is UploadUiState.Loading -> {

                    CircularProgressIndicator()

                    Text("Processing Image...")

            }


            is UploadUiState.Error -> {

                    Text("Processing failed. Please try another image.")


            }

            is UploadUiState.Success -> {
               navController.navigate(Screens.Result.passUrl(state.url)){
                   popUpTo(Screens.ImageSelection.route)
               }
            }

        }




    }


}