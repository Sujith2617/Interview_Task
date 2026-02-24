package com.example.interviewtask.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interviewtask.data.offline.GenratedImageEntity
import com.example.interviewtask.data.repository.UploadRepository
import com.example.interviewtask.presentation.uploadToResultScreens.UploadUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okhttp3.MultipartBody


class UploadViewmodel (private val repository: UploadRepository): ViewModel(){


    var state by mutableStateOf<UploadUiState>(UploadUiState.Idle)
        private set

   // val saveState by mutableStateOf("")
    var lastReqId by mutableStateOf<String?>(null)
    var lastImageUrl by mutableStateOf<String?>(null)


    val savedImages : StateFlow<List<GenratedImageEntity>> = repository.getAllImages().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    fun uploadImage(
        part: MultipartBody.Part,
        folderName : String,
        fileName:String
    ) {

        viewModelScope.launch {

            state = UploadUiState.Loading

            try {



                val uploadResponse =  repository.uploadAndGetResult(folder = folderName,fileName = fileName , appName = "NaturePhotoFramesandEditor", imagePart = part)

                if (uploadResponse.isSuccessful) {
                    Log.d("UPLOAD", "Success: ${uploadResponse.body()}")



                    state = UploadUiState.Loading
                    val requestId = uploadResponse.body()?.request_id.toString()


                    delay(10_000)


                    getResultImage(appName = "NaturePhotoFramesandEditor",requestId,)

                }
                else{

                }

            }catch (e: Exception){



            }


        }


    }

    private suspend fun getResultImage(appName: String,requestId:String) {

        try {


            val response = repository.getResultImage(
                appName = appName,
                requestId = requestId
            )



            if (response.isSuccessful) {
                val imageUrl =
                    "http://157.20.190.28:1112/examples/results/$appName/$requestId"

                lastReqId =requestId
                lastImageUrl =imageUrl


                state = UploadUiState.Success(imageUrl)

                Log.d("IMAGE GENERATED", "Success: ${response.body()}")
            }else {
                state =
                    UploadUiState.Error("Result fetch failed: ${response.code()}")
            }

        }catch (e: Exception){
            state = UploadUiState.Error(e.message?:"result error")
        }
    }



    fun insertImage( imageUrl:String){
        viewModelScope.launch {
        repository.insertImage(imageUrl)
    }
    }

    fun deleteImages(image : GenratedImageEntity){
        viewModelScope.launch {
            repository.deleteImage(image)
        }
    }

}
