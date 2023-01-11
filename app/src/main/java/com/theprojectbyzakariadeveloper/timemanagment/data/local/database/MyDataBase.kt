package com.theprojectbyzakariadeveloper.timemanagment.data.local.database

import android.content.Context
import androidx.compose.runtime.Immutable
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.theprojectbyzakariadeveloper.timemanagment.data.local.dao.DaoCategory
import com.theprojectbyzakariadeveloper.timemanagment.data.local.dao.DaoTasks
import com.theprojectbyzakariadeveloper.timemanagment.model.Category
import com.theprojectbyzakariadeveloper.timemanagment.model.Task

@Database(entities = [Task::class, Category::class], version = 1, exportSchema = false)
abstract class MyDataBase :RoomDatabase(){

    abstract fun getTaskDao() :DaoTasks
    abstract fun getCategoryDao() :DaoCategory
    companion object{
        @Volatile
        private var dataBaseTasks :MyDataBase? = null

        fun getDatabase(context: Context):MyDataBase{
            if (dataBaseTasks != null){
                return dataBaseTasks!!
            }
            synchronized(this){
                val database = Room.databaseBuilder(context,MyDataBase::class.java,"tasks_app.db").build()
                dataBaseTasks = database
                return database
            }
        }
    }
}