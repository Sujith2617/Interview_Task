package com.example.interviewtask.data.remote

import com.google.gson.annotations.SerializedName


data class CategoryTemplates(
   @SerializedName("Templates") val templates : List<TemplateItems>
)

data class TemplateItems(
    val url : String,
   @SerializedName("watch_ad")  val watchAd :String
)


typealias TemplatesResponse = Map<String, CategoryTemplates>