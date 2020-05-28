package com.example.btncounterapp.todo_list_app.OfflineDataBase

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.btncounterapp.todo_list_app.Models.ReminderModel
import com.example.btncounterapp.todo_list_app.Models.TaskModel
import com.example.btncounterapp.todo_list_app.Models.TodoModel
import com.example.btncounterapp.todo_list_app.OfflineDataBase.ReminderDataBase.ReminderDao
import com.example.btncounterapp.todo_list_app.OfflineDataBase.TaskDataBase.TaskDao

@Database(
    entities = arrayOf(TodoModel::class, TaskModel::class, ReminderModel::class),
    version =10,
    exportSchema = false
)
public abstract class TodoDataBase : RoomDatabase() {

    abstract fun todoDao(): TodoDao
    abstract fun taskDao(): TaskDao
    abstract fun reminderDao(): ReminderDao


    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: TodoDataBase? = null

        fun getDatabase(context: Context): TodoDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDataBase::class.java,
                    "todo_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}