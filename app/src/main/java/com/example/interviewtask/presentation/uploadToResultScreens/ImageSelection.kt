package com.example.interviewtask.presentation.uploadToResultScreens

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.interviewtask.UploadViewmodel
import com.example.interviewtask.bottomnavigation.Screens
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@Composable
fun ImageSelection (uri: String?, navController: NavController,viewmodel: UploadViewmodel){

    val context = LocalContext.current

    //for holding image from gallery
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }


    val imagePickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            selectedImageUri = uri
        }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally)
    {

        Text("Image Selection", fontSize = 18.sp, fontWeight = FontWeight.Bold)


        Card(modifier = Modifier.padding(top = 10.dp).wrapContentWidth().height(100.dp)) {

            AsyncImage(uri, contentDescription = "templates")

        }
        Button({imagePickerLauncher.launch("image/*")}) {
            Text("Select Image", fontSize = 12.sp)
        }


        //showing selected image from gallery
        selectedImageUri.let { uri ->
            Card(modifier = Modifier.padding(top = 10.dp).wrapContentWidth().height(430.dp)) {

                AsyncImage(uri, contentDescription = "templates")

            }
        }

        //button for upload selected image
        val isLoading = viewmodel.state is UploadUiState.Loading

            Button(enabled = selectedImageUri != null && !isLoading, onClick = {
                selectedImageUri?.let { uri->

                    val file = uriToFile(context,uri)

                    val filePart = prepareFilePart(file)

                    viewmodel.uploadImage(part = filePart)

                    navController.navigate(Screens.Processing.route)

                }

            }, modifier = Modifier.padding(top = 10.dp)) {
                Text("Upload Image", fontSize = 12.sp)
            }







    }
}


//uri to file
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


//multipart
fun prepareFilePart(file: File): MultipartBody.Part {
    val requestFile =
        file.asRequestBody("image/*".toMediaTypeOrNull())

    return MultipartBody.Part.createFormData(

        name = "sourceImage",          // API key name
        filename = file.name,
        body = requestFile
    )
}
