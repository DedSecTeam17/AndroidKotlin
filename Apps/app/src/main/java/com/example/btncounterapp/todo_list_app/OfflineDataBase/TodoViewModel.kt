package com.example.btncounterapp.todo_list_app.OfflineDataBase

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.btncounterapp.todo_list_app.Models.ReminderModel
import com.example.btncounterapp.todo_list_app.Models.TaskModel
import com.example.btncounterapp.todo_list_app.Models.TodoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TodoReposotry
    val allTodos: LiveData<List<TodoWithTasks>>


    init {
        val todoDao = TodoDataBase.getDatabase(application).todoDao()
        val taskDao = TodoDataBase.getDatabase(application).taskDao()
        val reminderDao = TodoDataBase.getDatabase(application).reminderDao()


        repository = TodoReposotry(todoDao, taskDao, reminderDao)
        allTodos = repository.allTodos


    }

    fun allTasks(todoId: Int): LiveData<List<TaskModel>> {
        return repository.allTasks(todoId = todoId)
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
//tasks

    fun insertTask(taskModel: TaskModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertTask(taskModel)
    }

    fun updateTask(taskModel: TaskModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateTask(taskModel)
    }

    fun deleteTask(taskModel: TaskModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteTask(taskModel)
    }

    //    reminders

    fun allReminders(todoId: Int): LiveData<List<ReminderModel>> {
        return repository.allReminders(todoId = todoId)
    }


    fun insertRemindier(reminderModel: ReminderModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertReminder(reminderModel)
    }


    fun deleteReminder(reminderModel: ReminderModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteReminder(reminderModel)
    }


}