package com.example.interviewtask.data.offline

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [GenratedImageEntity::class], version = 1)

abstract class GenratedImageDataBase : RoomDatabase(){

    abstract fun dao(): GenratedImageDao

}

object GenImageDataBaseProvider{

   private var Instance : GenratedImageDataBase? = null

    fun getDataBase(context: Context): GenratedImageDataBase{

        return Instance ?: synchronized(this){
            val instance  = Room.databaseBuilder(context, GenratedImageDataBase::class.java,"gidb").build()

             Instance = instance

            instance
        }

    }

}