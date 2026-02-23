package com.example.practice

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult

import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@Composable
fun UploadScreenPractice(viewModelPractice: UploadViewModelPractice){

    val state = viewModelPractice.uploadState

    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize().systemBarsPadding(), horizontalAlignment = Alignment.CenterHorizontally) {

        var selectedImageFromGallery by remember { mutableStateOf<Uri?>(null) }

        val imagePickerLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.GetContent()){ uri: Uri?->
            selectedImageFromGallery = uri
        }

        Button({imagePickerLauncher.launch("image/*")}) {
            Text("select Image")
        }

        selectedImageFromGallery?.let { uri ->
            Card(modifier = Modifier.wrapContentWidth().wrapContentWidth()) {


                AsyncImage(uri, contentDescription = "uri")
            }
        }

        Button({
            selectedImageFromGallery?.let { uri ->
                viewModelPractice.uploadImage(context,uri)
            }
        }) {
            Text("upload ")
        }

        when(state){
            is UploadState.Error -> {
                Text(state.message)
            }
            is UploadState.Success -> {
                AsyncImage(
                    model = state.imageUrl,
                    contentDescription = null
                )
            }
            is UploadState.Loading -> {

                CircularProgressIndicator()
            }
            is UploadState.Idle -> {

            }
        }


    }



}

fun uriToFile(context: Context, uri: Uri): File{

    val file = File(context.cacheDir,"upload_image.jpg")
    val inputStream = context.contentResolver.openInputStream(uri)?:throw IllegalArgumentException("cannot open url")
    file.outputStream().use { outputStream ->
        inputStream.use { inputStream ->
            inputStream.copyTo(outputStream)
        }
    }
    return file
}

fun prepareFilePart(file: File): MultipartBody.Part {
    val requestFile =
        file.asRequestBody("image/*".toMediaTypeOrNull())

    return MultipartBody.Part.createFormData(
        name = "sourceImage",          // API key name
        filename = file.name,
        body = requestFile
    )
}





class  UploadViewModelPractice(): ViewModel(){


    var uploadState by mutableStateOf<UploadState>(UploadState.Idle)
        private set

    fun uploadImage(context: Context, uri: Uri,
    appName: String = "NaturePhotoFramesandEditor"){

    viewModelScope.launch {

        try {

            val file = uriToFile(context, uri)

            val filePart = prepareFilePart(file)

            //val folderPart = createPartFromString("modelingWomen")
            //val fileNamePart = createPartFromString("modelingWomen_4.jpg")
           // val appNamePart = createPartFromString("NaturePhotoFramesandEditor")

            val uploadResponse = RetrofitClient.instance.uploadImagePractice(
                folderPractice = "modelingWomen",
                fileNamePractice = "modelingWomen_4.jpg",
                appNamePractice = "NaturePhotoFramesandEditor",
                sourceImage = filePart
            )

            if (uploadResponse.isSuccessful) {
                Log.d("UPLOAD", "Success: ${uploadResponse.body()}")


                // getting image
                val requestId = uploadResponse.body()?.request_id.toString()

                delay(10_000)

                getResultImage(appName = appName, requestId = requestId)

                //RetrofitClient.instance.getResultImage("NaturePhotoFramesandEditor",requestId)


            } else {
                Log.d("UPLOAD", "Error: ${uploadResponse.errorBody()?.string()}")
            }

        } catch (e: Exception) {
            Log.d("UPLOAD", "Exception: ${e.message}")
        }

    }
    }

    private suspend fun getResultImage(
        requestId: String,
        appName: String
    ) {

        try {



            val response = RetrofitClient.instance.getResultImage(
                appName = appName,
                requestId = requestId
            )

            if (response.isSuccessful) {

                val imageUrl =
                    "http://157.20.190.28:1112/examples/results/$appName/$requestId"

                uploadState = UploadState.Success(imageUrl)

            } else {
                uploadState =
                    UploadState.Error("Result fetch failed: ${response.code()}")
            }

        } catch (e: Exception) {
            uploadState = UploadState.Error(e.message ?: "Result error")
        }
    }



}
sealed class UploadState {
    object Idle : UploadState()
    object Loading : UploadState()
    data class Success(val imageUrl: String) : UploadState()
    data class Error(val message: String) : UploadState()
}
