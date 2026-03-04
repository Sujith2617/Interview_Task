package com.example.interviewtask.bottomnavigation

import android.content.Context
import com.example.interviewtask.data.remote.TemplatesResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.Exception



// reading json
fun loadJsonFromAssets(context: Context) : TemplatesResponse?{
    return try {

        // access to assests then read it as text (converting to string )
        val jsonString = context.assets.open("templates.json")
            .bufferedReader()
            .use { it.readText() }

        // the exact type(map) telling gson type we want
        val type = object : TypeToken<TemplatesResponse>() {}.type

        //convert json to kotlin map bcoz out key are dynamic so we we need to use maping
        Gson().fromJson(jsonString,type)

    }
    catch (e: Exception){
        e.printStackTrace()
        null
    }
}