package com.example.btncounterapp.todo_list_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.btncounterapp.R
import com.example.btncounterapp.todo_list_app.Models.TodoModel
import com.example.btncounterapp.todo_list_app.OfflineDataBase.TodoViewModel
import com.example.btncounterapp.todo_list_app.adapters.TodoListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TodoActivity : AppCompatActivity() {


    var toolbar: Toolbar? = null
    var addTodo: FloatingActionButton? = null

    var todos: RecyclerView? = null
    var mAdapter: TodoListAdapter? = null

    lateinit var emptyImage: ImageView


    private lateinit var todoViewModel: TodoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)

        todos = findViewById<RecyclerView>(R.id.todos)
        toolbar = findViewById<Toolbar>(R.id.appbar)
        addTodo = findViewById<FloatingActionButton>(R.id.addTodo)
        emptyImage = findViewById<ImageView>(R.id.empty)
        emptyImage.visibility = View.GONE

        toolbar?.title = "Woodife"
        todoViewModel = ViewModelProvider(this).get(TodoViewModel::class.java)

        mAdapter = TodoListAdapter(
            ctx = applicationContext,
            todos = mutableListOf<TodoModel>(),
            viewModel = todoViewModel
        )

        todos?.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = mAdapter

        }


//        toolbar?.title = "Todos"
        todoViewModel.allTodos.observe(this, Observer { todosData ->

            if (todosData.isEmpty()) {
                emptyImage.visibility = View.VISIBLE
                mAdapter?.setTodos(todosData.map { todoWithTasks -> todoWithTasks.todoModel })
            } else {

                emptyImage.visibility = View.GONE

                todosData.let {
                    mAdapter?.setTodos(todosData.map { todoWithTasks -> todoWithTasks.todoModel })

                }
            }


        })


        addTodo?.setOnClickListener {


            var i = Intent(this, TodoMutationActivity::class.java)
            i.action = TodoMutationActivity.IS_FOR_CREATE
            startActivity(i)
            //            open create activity

        }

    }
}


// adatapter go here
