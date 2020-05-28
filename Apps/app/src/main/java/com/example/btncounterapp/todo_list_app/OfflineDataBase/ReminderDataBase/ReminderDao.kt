package com.example.btncounterapp.todo_list_app.OfflineDataBase.ReminderDataBase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.btncounterapp.todo_list_app.Models.ReminderModel
import com.example.btncounterapp.todo_list_app.Models.TaskModel

@Dao
interface ReminderDao {

    @Query("SELECT * from reminder_table WHERE todo_id=:todoId   ORDER BY id ASC")
    fun getAllReminders(todoId: Int): LiveData<List<ReminderModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(reminderModel: ReminderModel)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(reminderModel: ReminderModel)

    @Delete
    suspend fun delete(reminderModel: ReminderModel)

    @Query("DELETE FROM reminder_table")
    suspend fun deleteAll()
}