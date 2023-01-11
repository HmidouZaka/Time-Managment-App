package com.theprojectbyzakariadeveloper.timemanagment.repository

import com.theprojectbyzakariadeveloper.timemanagment.data.local.database.MyDataBase
import com.theprojectbyzakariadeveloper.timemanagment.model.Category
import com.theprojectbyzakariadeveloper.timemanagment.model.Task
import kotlinx.coroutines.flow.Flow

class MainRepository (dataBase: MyDataBase) {
    private val taskDao = dataBase.getTaskDao()
    private val categoryDao = dataBase.getCategoryDao()



    val categories = categoryDao.getAll()
    val tasks = taskDao.getAll()




    suspend fun addCategory(category: Category){
        categoryDao.insertCategory(category)
    }

    suspend fun addTask(task: Task){
        taskDao.insertTask(task)
    }

    suspend fun removeTask(task: Task){
        taskDao.delete(task)
    }


    suspend fun upDateTask(task: Task){
        taskDao.updateTask(task)
    }
    suspend fun removeCtegory(category: Category){
        categoryDao.delete(category)
    }


    suspend fun findTask(task: String): Flow<List<Task>> {
        return taskDao.findByContent("%$task%")
    }


}