package com.example.interviewtask

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.interviewtask.bottomnavigation.MainScreen
import com.example.interviewtask.data.remote.RetrofitInstance
import com.example.interviewtask.data.offline.GenImageDataBaseProvider
import com.example.interviewtask.data.offline.GenratedImageDataBase
import com.example.interviewtask.data.repository.UploadRepository
import kotlin.getValue

class MainActivity : ComponentActivity() {

   private val viewModel by viewModels<TemplateViewModel>()

    private lateinit var uploadViewmodel: UploadViewmodel


  //  private val viewModelPractice by viewModels<UploadViewModelPractice>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = GenImageDataBaseProvider.getDataBase(this)
        val api = RetrofitInstance.api
        val repository = UploadRepository(api, dao.dao())

        val factory = UploadViewModelFactory(repository)

       uploadViewmodel = ViewModelProvider(this,factory).get(UploadViewmodel::class.java)
        enableEdgeToEdge()
        setContent {

           // UploadScreenPractice(viewModelPractice)


            MainScreen(viewModel,uploadViewmodel)


        }
    }
}

