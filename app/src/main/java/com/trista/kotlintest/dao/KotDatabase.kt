package com.trista.kotlintest.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.trista.kotlintest.base.Worker

/**
 *  @author : zhouff
 *  date : 2020/10/26 14:41
 *  description :
 */
@Database(entities = [Worker::class],version = 1)
abstract class KotDatabase : RoomDatabase() {
    abstract fun getWorkerDao():WorkerDao

    companion object {
        @Volatile
        private var INSTANCE: KotDatabase? = null
        fun getInstance(context: Context): KotDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, KotDatabase::class.java, "kot.db")
                .build()
    }
}