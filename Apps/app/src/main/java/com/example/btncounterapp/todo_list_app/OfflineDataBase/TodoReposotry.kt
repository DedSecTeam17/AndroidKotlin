package com.example.btncounterapp.todo_list_app.OfflineDataBase

import androidx.lifecycle.LiveData
import com.example.btncounterapp.todo_list_app.Models.TodoModel

class TodoReposotry(private val todoDao: TodoDao) {


    val allTodos: LiveData<List<TodoModel>> = todoDao.getAllTodos()

    suspend fun insert(todoModel: TodoModel) {
        todoDao.insert(todoModel)
    }


    suspend fun delete(todoModel: TodoModel) {
        todoDao.delete(todoModel)
    }

    suspend fun update(todoModel: TodoModel) {
        todoDao.update(todoModel)
    }


    suspend fun deleteAll() {
        todoDao.deleteAll()
    }


}