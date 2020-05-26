package com.example.btncounterapp.todo_list_app.OfflineDataBase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.btncounterapp.todo_list_app.Models.TodoModel

@Dao
interface TodoDao {

    @Query("SELECT * from todo_table ORDER BY id ASC")
    fun getAllTodos(): LiveData<List<TodoModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todoModel: TodoModel)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(todoModel: TodoModel)

    @Delete
    suspend fun delete(todoModel: TodoModel)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAll()
}