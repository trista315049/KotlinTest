package com.trista.kotlintest.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.trista.kotlintest.base.Worker

/**
 *  @author : zhouff
 *  date : 2020/10/26 14:38
 *  description :
 */
@Dao
interface WorkerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWorker(worker: Worker)

    @Query("delete from worker where name=:name")
    fun deleteWorker(name: String)

    @Query("select * from worker")
    fun queryAllWorkers():List<Worker>
}