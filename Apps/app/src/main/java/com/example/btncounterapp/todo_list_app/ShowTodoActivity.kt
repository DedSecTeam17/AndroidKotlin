package com.example.btncounterapp.todo_list_app

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.btncounterapp.R
import com.example.btncounterapp.todo_list_app.Models.ReminderModel
import com.example.btncounterapp.todo_list_app.Models.TodoModel
import com.example.btncounterapp.todo_list_app.OfflineDataBase.TodoViewModel
import com.example.btncounterapp.todo_list_app.adapters.TodosPageAdapter
import com.example.btncounterapp.todo_list_app.services.ReminderReciver
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import androidx.core.app.AlarmManagerCompat.setAlarmClock
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




class ShowTodoActivity : AppCompatActivity() {


    var toolbar: Toolbar? = null
    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: TodosPageAdapter
    private lateinit var currentTodoModel: TodoModel
    private lateinit var todoViewModel: TodoViewModel
    var  isFirstTime = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_todo)

        toolbar = findViewById<Toolbar>(R.id.appbar)
        toolbar?.title = ""
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        view pager stuff
        todoViewModel = ViewModelProvider(this).get(TodoViewModel::class.java)

        viewPager = findViewById(R.id.todosPages)


        todoViewModel.allTodos.observe(this, androidx.lifecycle.Observer { todosList ->
            todosList.let {

                var mapedList = todosList.map { todoWithTasks -> todoWithTasks.todoModel }

                pagerAdapter = TodosPageAdapter(supportFragmentManager, mapedList)
                viewPager.adapter = pagerAdapter
//                if (isFirstTime)
                viewPager.currentItem = intent.extras?.getInt("index") ?: 0
//                else
//                    isFirstTime=false
                viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrollStateChanged(state: Int) {
                    }

                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {
                        currentTodoModel = mapedList[position]
                        toolbar?.title = mapedList[position].title


                    }

                    override fun onPageSelected(position: Int) {
                        currentTodoModel = mapedList[position]


                    }

                })

            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete -> {
                todoViewModel.delete(currentTodoModel)
                onBackPressed()

                true
            }
            R.id.update -> {
                Toast.makeText(this, "update", Toast.LENGTH_LONG).show()
                var i = Intent(this, TodoMutationActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                i.action = TodoMutationActivity.IS_FOR_EDIT
                i.putExtra("todo", currentTodoModel)

                startActivity(i)
                true
            }
            R.id.done -> {
                currentTodoModel.isDone = true
                todoViewModel.update(currentTodoModel)

                onBackPressed()

                true
            }
            R.id.alarm -> {
//               open time picker
                val cal = Calendar.getInstance()
                val timeSetListener =
                    TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->

                        cal.set(Calendar.HOUR_OF_DAY, hour)
                        cal.set(Calendar.MINUTE, minute)

                        setAlarm(time = cal.timeInMillis)

                    }
                TimePickerDialog(
                    this,
                    timeSetListener,
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    true
                ).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    companion object {
        const val REMINDER_ACTION = "REMINDER"
    }

    private fun setAlarm(time: Long) {
        var alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, ReminderReciver::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)


        intent.action = REMINDER_ACTION
        intent.putExtra("id", currentTodoModel.id)
        intent.putExtra("title", currentTodoModel.title)
        intent.putExtra("body", currentTodoModel.description)

        var pendingIntent = PendingIntent.getBroadcast(
            this,
            currentTodoModel.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            alarmManager.setAlarmClock(
                AlarmManager.AlarmClockInfo(
                    time,
                    pendingIntent
                ), pendingIntent
            )

        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    time,
                    pendingIntent
                )
            } else {
                alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    time,
                    pendingIntent
                )
            }

        }
    }
}
