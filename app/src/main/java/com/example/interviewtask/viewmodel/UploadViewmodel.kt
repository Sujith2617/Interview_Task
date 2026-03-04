package com.example.interviewtask.viewmodel

import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interviewtask.data.offline.GenratedImageEntity
import com.example.interviewtask.data.repository.UploadRepository
import com.example.interviewtask.presentation.uploadToResultScreens.UploadUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request


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

    fun saveImageToGallery(context: Context, imageUrl: String) {

       // state = UploadUiState.Saving   // 👈 show saving state

        viewModelScope.launch(Dispatchers.IO) {

            val saved = downloadAndSaveImage(context, imageUrl)

            withContext(Dispatchers.Main) {
                if (saved) {

                    lastImageUrl?.let { insertImage(it) }

                   // state = UploadUiState.Success(imageUrl)  // 👈 back to result
                    Toast.makeText(context,"Saved to Gallery ", Toast.LENGTH_SHORT).show()

                    Log.d("GALLERY", "Saved successfully")

                } else {

                    state = UploadUiState.Error("Save failed")
                    Log.d("GALLERY", "Save failed")
                }
            }
        }
    }

}

fun downloadAndSaveImage(context: Context, imageUrl: String): Boolean {
    return try {

        val client = OkHttpClient()
        val request = Request.Builder().url(imageUrl).build()
        val response = client.newCall(request).execute()

        if (!response.isSuccessful) return false

        val inputStream = response.body?.byteStream() ?: return false

        val fileName = "IMG_${System.currentTimeMillis()}.jpg"

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_DCIM + "/InterviewTask")
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val resolver = context.contentResolver

        val imageUri = resolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        ) ?: return false

        resolver.openOutputStream(imageUri).use { outputStream ->
            inputStream.copyTo(outputStream!!)
        }

        contentValues.clear()
        contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
        resolver.update(imageUri, contentValues, null, null)

        inputStream.close()

        true

    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}