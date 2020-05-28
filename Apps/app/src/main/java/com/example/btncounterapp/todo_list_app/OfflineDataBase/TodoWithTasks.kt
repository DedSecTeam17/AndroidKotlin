package com.example.btncounterapp.todo_list_app.OfflineDataBase

import androidx.room.Embedded
import androidx.room.Relation
import com.example.btncounterapp.todo_list_app.Models.TaskModel
import com.example.btncounterapp.todo_list_app.Models.TodoModel

data class TodoWithTasks(
    @Embedded val todoModel: TodoModel,
    @Relation(
        parentColumn = "id",
        entityColumn = "todo_id"
    )
    val tasks: List<TaskModel>
)