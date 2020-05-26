package com.example.btncounterapp.todo_list_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.btncounterapp.R
import com.example.btncounterapp.todo_list_app.Models.TodoModel
import com.example.btncounterapp.todo_list_app.OfflineDataBase.TodoViewModel
import com.example.btncounterapp.todo_list_app.adapters.TodosPageAdapter
import java.text.SimpleDateFormat
import java.util.*

class ShowTodoActivity : AppCompatActivity() {


    var toolbar: Toolbar? = null
    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: TodosPageAdapter
    private lateinit var currentTodoModel: TodoModel
    private lateinit var todoViewModel: TodoViewModel

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

        todoViewModel.allTodos.observe(this, androidx.lifecycle.Observer { todosList ->
            todosList.let {
                viewPager = findViewById(R.id.todosPages)
                pagerAdapter = TodosPageAdapter(supportFragmentManager, todosList)
                viewPager.adapter = pagerAdapter
                viewPager.currentItem = intent.extras?.getInt("index") ?: 0
                viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrollStateChanged(state: Int) {
                    }

                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {
                        currentTodoModel = todosList[position]
                        toolbar?.title = todosList[position].title
                    }

                    override fun onPageSelected(position: Int) {
                        currentTodoModel = todosList[position]


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
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}
