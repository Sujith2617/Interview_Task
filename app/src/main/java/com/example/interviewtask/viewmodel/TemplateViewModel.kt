package com.example.interviewtask

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.interviewtask.bottomnavigation.loadJsonFromAssets
import com.example.interviewtask.data.remote.TemplatesResponse
class TemplateViewModel(application: Application): AndroidViewModel(application
){

    //holds observes changes
    var templates by mutableStateOf<TemplatesResponse?>(null)

        private set

    //to hold selected cat
    var selectedCategory by mutableStateOf<String?>(null)
        private set

    //main priority inside init
    init {
        loadTemplates()
    }

    fun loadTemplates(){
        templates = loadJsonFromAssets(getApplication()) as TemplatesResponse?

        selectedCategory = templates?.keys?.firstOrNull()

    }


    fun onCategorySelected(category: String){
        selectedCategory = category
    }



}