package com.theprojectbyzakariadeveloper.timemanagment.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.theprojectbyzakariadeveloper.timemanagment.model.Category

@Dao
interface DaoCategory {

    @Delete
    suspend fun delete(id:Category)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: Category)


    @Query("SELECT * FROM categories")
    fun getAll():LiveData<List<Category>>

}
