package com.example.btncounterapp.todo_list_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.afollestad.vvalidator.form
import com.example.btncounterapp.R
import com.example.btncounterapp.todo_list_app.Models.TodoModel
import com.example.btncounterapp.todo_list_app.OfflineDataBase.TodoViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.min

class TodoMutationActivity : AppCompatActivity() {


    companion object {

        const val IS_FOR_EDIT = "EDIT"
        const val IS_FOR_CREATE = "CREATE"


    }

    var currentFlag: String? = ""
    var toolbar: Toolbar? = null


    lateinit var mTitle: EditText
    lateinit var mDescription: EditText
    lateinit var doMutations: Button
    private lateinit var todoViewModel: TodoViewModel
    private lateinit var selectedTodoModel: TodoModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_mutation)
        toolbar = findViewById<Toolbar>(R.id.appbar)
        toolbar?.title = "Create new todo"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        todoViewModel = ViewModelProvider(this).get(TodoViewModel::class.java)
//
        mTitle = findViewById<EditText>(R.id.title)
        mDescription = findViewById<EditText>(R.id.description)
        doMutations = findViewById<Button>(R.id.doMutation)


        currentFlag = intent.action
        if (currentFlag.equals(IS_FOR_CREATE))
            doMutations.text = "CREATE"
        else {
            doMutations.text = "UPDATE"
            selectedTodoModel = intent.getParcelableExtra<TodoModel>("todo")
            mTitle.setText(selectedTodoModel.title)
            mDescription.setText(selectedTodoModel.description)


        }


        doValidation()

    }

    fun doValidation() {
        form {
            input(R.id.title) {
                isNotEmpty()
                length().atLeast(6)
            }
            input(R.id.description) {
                isNotEmpty()
                length().atLeast(6)
            }
            submitWith(R.id.doMutation) { result ->
                if (result.success()) {
                    if (currentFlag.equals(IS_FOR_CREATE)) {
                        todoViewModel.insert(
                            todoModel = TodoModel(
                                title = mTitle.text.toString(),
                                description = mDescription.text.toString(),
                                isDone = false,
                                date = SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(Date())
                            )
                        )
                    } else {
                        selectedTodoModel.title = mTitle.text.toString()
                        selectedTodoModel.description = mDescription.text.toString()

                        todoViewModel.update(
                            todoModel = selectedTodoModel
                        )
                    }




                    onBackPressed()
                }
                // this block is only called if form is valid.
                // do something with a valid form state.
            }
        }
    }

}
