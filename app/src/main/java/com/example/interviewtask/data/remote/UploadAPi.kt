package com.example.interviewtask.data.remote





import com.example.interviewtask.data.UploadResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
interface UploadAPi {
    @Multipart
    @POST("upload/{folder}/{fileName}")
    suspend fun uploadImage(
        @Header("accept") accept: String = "application/json",
        @Header("fcmtoken") fcmtoken: String = "",
        @Header("x-firebase-appcheck") appCheck: String = "",
        @Path("folder") folder: String,
        @Path("fileName") fileName: String,
        @Query("app_name") appName: String,
        @Part sourceImage: MultipartBody.Part

    ): Response<UploadResponse>


    @GET("examples/results/{appName}/{requestId}")
    suspend fun getResultImage(
        @Path("appName") appName: String,
        @Path("requestId") requestId : String
    ) : Response<ResponseBody>
}