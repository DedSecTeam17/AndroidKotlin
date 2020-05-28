package com.example.btncounterapp.todo_list_app.Models

import androidx.room.*


@Entity(
    indices = arrayOf(Index(value =  ["todo_id"],name = "todo_id_unique_index_in_reminder" ,  unique = true)),

    tableName = "reminder_table", foreignKeys = arrayOf(
        ForeignKey(
            entity = TodoModel::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("todo_id"),
            onDelete = ForeignKey.CASCADE
        )
    )


)
class ReminderModel(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "todo_id", index = true) var toDoId: Int,
    @ColumnInfo(name = "future_time") var futureTime: Long
)