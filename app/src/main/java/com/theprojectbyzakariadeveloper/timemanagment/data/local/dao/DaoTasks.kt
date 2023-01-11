package com.theprojectbyzakariadeveloper.timemanagment.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.theprojectbyzakariadeveloper.timemanagment.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoTasks {

    @Delete
    suspend fun delete(id:Task)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task:Task)

    @Query("SELECT * FROM task ORDER BY id ASC")
    fun getAll():Flow<List<Task>>

    @Query("SELECT * FROM task WHERE data LIKE :content")
    fun findByContent(content:String):Flow<List<Task>>
}
