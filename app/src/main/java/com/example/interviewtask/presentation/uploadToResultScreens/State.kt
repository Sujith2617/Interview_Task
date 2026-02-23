package com.example.interviewtask.presentation.uploadToResultScreens

sealed class UploadUiState{

    object Idle : UploadUiState()

    object Loading : UploadUiState()

    data class Success(val url : String): UploadUiState()

    data class Error(val message:String) : UploadUiState()

}