package com.example.interviewtask.bottomnavigation

import android.net.Uri


sealed class Screens(val route:String){

    object Home : Screens("Home")
    object Creations : Screens("Creations")
    object Settings : Screens("Settings")

    object ImageSelection : Screens("imageSelection/{uri}"){
        fun passData(uri: String): String{

            val encodedUrl = Uri.encode(uri)

            return "imageSelection/$encodedUrl"
        }
    }


    object Processing : Screens("processing")


    object Result : Screens("result/{imageUrl}"){
        fun passUrl(uri: String): String{

            val encodedUrl = Uri.encode(uri)

            return "result/$encodedUrl"
        }
    }
}