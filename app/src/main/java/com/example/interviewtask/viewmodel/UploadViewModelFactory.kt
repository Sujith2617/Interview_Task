package com.example.interviewtask

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.interviewtask.data.repository.UploadRepository

class UploadViewModelFactory(private val repository: UploadRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UploadViewmodel::class.java)){
            return UploadViewmodel(repository) as T
        }
        throw IllegalArgumentException("error")
    }
}