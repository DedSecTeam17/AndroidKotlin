package com.example.btncounterapp.todo_list_app.OfflineDataBase.TaskDataBase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.btncounterapp.todo_list_app.Models.TaskModel
import com.example.btncounterapp.todo_list_app.Models.TodoModel
import com.example.btncounterapp.todo_list_app.OfflineDataBase.TodoWithTasks

@Dao
interface TaskDao {

    @Query("SELECT * from task_table WHERE todo_id=:todoId   ORDER BY id ASC")
    fun getALLTasks(todoId: Int): LiveData<List<TaskModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(taskModel: TaskModel)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(taskModel: TaskModel)

    @Delete
    suspend fun delete(taskModel: TaskModel)

    @Query("DELETE FROM task_table")
    suspend fun deleteAll()
}