package com.example.btncounterapp.todo_list_app.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.btncounterapp.todo_list_app.Models.TodoModel
import com.example.btncounterapp.todo_list_app.ShowTodoActivity
import android.R
import android.app.NotificationManager
import android.app.NotificationChannel
import android.content.Context
import androidx.core.app.NotificationCompat


class ReminderService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        if (intent != null) {
            if (intent.action.equals(ShowTodoActivity.REMINDER_ACTION)) {
//                var todoModel: TodoModel? = intent.extras?.getParcelable<TodoModel>("todo")
                showNotification(title =  intent.extras?.getString("title") , body =  intent.extras?.getString("body") , id = intent.extras?.getInt("id"))

            }
        }

        return START_STICKY
    }

    fun showNotification(title: String?, body: String?, id: Int?) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
        val notificationBuilder = NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.ic_menu_close_clear_cancel)
            .setContentTitle("Reminder")
            .setContentText("Reminder of your task $title")
            .setPriority(NotificationCompat.PRIORITY_MAX)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                "REMINDER-$title-$id",
               "$title+$id",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.description = "reminder"
            notificationManager.createNotificationChannel(notificationChannel)
            notificationBuilder.setChannelId(notificationChannel.id)
        }
        val incomingCallNotification = notificationBuilder.build()

        if (id != null) {
            notificationManager.notify(id , incomingCallNotification)
        }
    }

}