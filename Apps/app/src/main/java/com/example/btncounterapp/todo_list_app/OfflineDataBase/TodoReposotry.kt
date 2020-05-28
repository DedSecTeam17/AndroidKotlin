package com.example.btncounterapp.todo_list_app.OfflineDataBase

import androidx.lifecycle.LiveData
import com.example.btncounterapp.todo_list_app.Models.ReminderModel
import com.example.btncounterapp.todo_list_app.Models.TaskModel
import com.example.btncounterapp.todo_list_app.Models.TodoModel
import com.example.btncounterapp.todo_list_app.OfflineDataBase.ReminderDataBase.ReminderDao
import com.example.btncounterapp.todo_list_app.OfflineDataBase.TaskDataBase.TaskDao

class TodoReposotry(
    private val todoDao: TodoDao,
    private val taskDao: TaskDao,
    private val reminderDao: ReminderDao
) {


    val allTodos: LiveData<List<TodoWithTasks>> = todoDao.getAllTodos()

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


//    tasks


    fun allTasks(todoId: Int): LiveData<List<TaskModel>> {
        return taskDao.getALLTasks(todoId = todoId)
    }

    suspend fun insertTask(taskModel: TaskModel) {
        taskDao.insert(taskModel)
    }


    suspend fun deleteTask(taskModel: TaskModel) {
        taskDao.delete(taskModel)
    }

    suspend fun updateTask(taskModel: TaskModel) {
        taskDao.update(taskModel)
    }

    //    reminders
    fun allReminders(todoId: Int): LiveData<List<ReminderModel>> {
        return reminderDao.getAllReminders(todoId = todoId)
    }

    suspend fun insertReminder(reminderModel: ReminderModel) {
        reminderDao.insert(reminderModel)
    }


    suspend fun deleteReminder(reminderModel: ReminderModel) {
        reminderDao.delete(reminderModel)
    }


}