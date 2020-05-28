package com.example.btncounterapp.todo_list_app.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.example.btncounterapp.todo_list_app.Models.TodoModel
import com.example.btncounterapp.todo_list_app.ShowTodoActivity

class ReminderReciver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {


        if (intent != null) {
            if (intent.action.equals(ShowTodoActivity.REMINDER_ACTION)) {
                var todoModel: TodoModel? = intent.extras?.getParcelable<TodoModel>("todo")
//                Toast.makeText(context, "ok ${todoModel?.id}", Toast.LENGTH_LONG).show()

                var reminderIntent: Intent = Intent(context, ReminderService::class.java)
                reminderIntent.action = ShowTodoActivity.REMINDER_ACTION
                reminderIntent.putExtra("id", intent.extras?.getInt("id"))
                reminderIntent.putExtra("title", intent.extras?.getString("title"))
                reminderIntent.putExtra("body", intent.extras?.getString("body"))
                context?.startService(reminderIntent)


            }
        }
    }

}