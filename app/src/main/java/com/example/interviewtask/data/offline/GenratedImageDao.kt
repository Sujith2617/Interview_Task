package com.example.interviewtask.data.offline

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface GenratedImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: GenratedImageEntity)

    @Query("SELECT * FROM genratedImageTable ORDER BY createdAt DESC")
    fun getAllImages(): Flow<List<GenratedImageEntity>>

    @Delete
    suspend fun deleteImage(image: GenratedImageEntity)
}