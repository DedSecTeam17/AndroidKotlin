package com.example.btncounterapp.todo_list_app.OfflineDataBase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.btncounterapp.todo_list_app.Models.TodoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TodoReposotry
    val allTodos: LiveData<List<TodoModel>>

    init {
        val wordsDao = TodoDataBase.getDatabase(application).todoDao()
        repository = TodoReposotry(wordsDao)
        allTodos = repository.allTodos
    }


    fun insert(todoModel: TodoModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(todoModel)
    }

    fun update(todoModel: TodoModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(todoModel)
    }

    fun delete(todoModel: TodoModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(todoModel)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }

}