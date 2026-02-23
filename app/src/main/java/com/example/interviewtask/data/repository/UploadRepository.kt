package com.example.interviewtask.data.repository

import com.example.interviewtask.data.UploadResponse
import com.example.interviewtask.data.offline.GenratedImageDao
import com.example.interviewtask.data.offline.GenratedImageEntity
import com.example.interviewtask.data.remote.UploadAPi
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response

class UploadRepository(private val api: UploadAPi, private val dao: GenratedImageDao) {


    suspend fun uploadAndGetResult(
        folder: String,
        fileName: String,
        appName: String,
        imagePart: MultipartBody.Part): Response<UploadResponse>
    {

     return   api.uploadImage(
            folder = folder,
            fileName = fileName,
            appName = appName,
            sourceImage = imagePart
        )

    }

  suspend fun getResultImage(
        appName: String,
        requestId: String
    ): Response<ResponseBody>
  {
      return api.getResultImage(appName,requestId)
  }


    suspend fun insertImage(url :String){
       dao.insertImage(GenratedImageEntity(url))
    }

     fun getAllImages() = dao.getAllImages()


    suspend fun deleteImage(image: GenratedImageEntity){
        dao.deleteImage(image)
    }

}

