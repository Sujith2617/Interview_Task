package com.example.practice



import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit




data class UploadResponse(
    val request_id: Double)
interface ApiInterfacePractice
{
    @Multipart
    @POST("upload/{folder}/{fileName}")
    suspend fun uploadImagePractice(
        @Header("accept") accept: String = "application/json",
        @Header("fcmtoken") fcmtoken: String = "",
        @Header("x-firebase-appcheck") appCheck: String = "",
        @Path("folder") folderPractice: String,
        @Path("fileName") fileNamePractice: String,
        @Query ("app_name") appNamePractice: String,
        @Part sourceImage: MultipartBody.Part,
    ): Response<UploadResponse>


    @GET("examples/results/{appName}/{requestId}")
    suspend fun getResultImage(
        @Path("appName") appName: String,
        @Path("requestId") requestId: String
    ): Response<ResponseBody>
}




object RetrofitClient {
    private const val BASE_URL = "http://157.20.190.28:1112/"

    private val okHttpClient: OkHttpClient by lazy {

        // Logging interceptor helps you see the Multipart request in Logcat
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    val instance: ApiInterfacePractice by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterfacePractice::class.java)
    }
}