package com.example.interviewtask.bottomnavigation

import android.content.Context
import com.example.interviewtask.data.remote.TemplatesResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.Exception

fun loadJsonFromAssets(context: Context) : TemplatesResponse?{
    return try {

        //access/open json file from assets
        val jsonString = context.assets.open("templates.json")
            .bufferedReader()
            .use { it.readText() }

        // the exact type(map)
        val type = object : TypeToken<TemplatesResponse>() {}.type

        //convert json to kotlin map
        Gson().fromJson(jsonString,type)

    }
    catch (e: Exception){
        e.printStackTrace()
        null
    }
}