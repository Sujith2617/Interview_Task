package com.example.interviewtask.data.offline

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "genratedImageTable")
data class GenratedImageEntity(
    val imageUrl:String,
    val createdAt : Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true) val id : Int = 0

)