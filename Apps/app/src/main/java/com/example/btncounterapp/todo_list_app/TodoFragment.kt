package com.example.btncounterapp.todo_list_app

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.vvalidator.form

import com.example.btncounterapp.R
import com.example.btncounterapp.todo_list_app.Models.TaskModel
import com.example.btncounterapp.todo_list_app.Models.TodoModel
import com.example.btncounterapp.todo_list_app.OfflineDataBase.TodoViewModel
import com.example.btncounterapp.todo_list_app.adapters.TaskListAdapter
import com.example.btncounterapp.todo_list_app.adapters.TaskOperations
import com.example.btncounterapp.todo_list_app.adapters.TodoListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mikhaellopez.circularprogressbar.CircularProgressBar

private const val ARG_INDEX = "index"

class TodoFragment : Fragment(), TaskOperations {

    // TODO: Rename and change types of parameters
    private var todoModel: TodoModel? = null

    private var title: TextView? = null
    private var description: TextView? = null
    private lateinit var todoViewModel: TodoViewModel
    //
    var tasks: RecyclerView? = null
    var mAdapter: TaskListAdapter? = null

    var isUpdateTask = false

    lateinit var selectedTask: TaskModel
    lateinit var addTask: FloatingActionButton

    lateinit var emptyTasks: ImageView
    lateinit var circularProgressBar: CircularProgressBar

    lateinit var taskHint: TextView
    lateinit var taskPercentage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            todoModel = it.getParcelable<TodoModel>(ARG_INDEX)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view: View = inflater.inflate(R.layout.fragment_todo, container, false)

        title = view.findViewById<TextView>(R.id.title)
        title?.text = todoModel?.title
        description = view.findViewById<TextView>(R.id.description)
        taskHint = view.findViewById<TextView>(R.id.tasks_hint)
        taskPercentage = view.findViewById<TextView>(R.id.tasks_per)

        description?.text = todoModel?.description
        tasks = view.findViewById<RecyclerView>(R.id.tasks)
        addTask = view.findViewById<FloatingActionButton>(R.id.addTask)
        emptyTasks = view.findViewById<ImageView>(R.id.empty_tasks)
        circularProgressBar = view.findViewById<CircularProgressBar>(R.id.circularProgressBar)

        addTask.setOnClickListener {
            mutaionsDialog()
        }


//
        todoViewModel = ViewModelProvider(this).get(TodoViewModel::class.java)
        mAdapter = this.context?.let {
            TaskListAdapter(
                ctx = it,
                tasks = mutableListOf<TaskModel>(),
                viewModel = todoViewModel,
                taskOperations = this
            )
        }

        tasks?.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = mAdapter


        }
        tasks?.setNestedScrollingEnabled(false)



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        todoModel?.id?.let {
            todoViewModel.allTasks(it).observe(this, Observer { listOfTasks ->
                if (listOfTasks.isEmpty())
                    emptyTasks.visibility = View.VISIBLE
                else
                    emptyTasks.visibility = View.GONE

                var doneSize = listOfTasks.filter { taskModel -> taskModel.isDone }.size
                var needToDone = listOfTasks.filter { taskModel -> !taskModel.isDone }.size
                taskHint.setText("$needToDone to done")


                if (listOfTasks.isNotEmpty()) {
                    var percantage: Float = (doneSize.toFloat() / listOfTasks.size.toFloat()) * 100
                    taskPercentage.setText("Done %${percantage.toInt()}")
                    updateProgress(percantage)

                }



                mAdapter?.setTasks(listOfTasks)
            })
        }
    }


    fun updateProgress(newProgress: Float) {
        circularProgressBar.apply {
            // Set Progress
            progress = newProgress
            // or with animation
            setProgressWithAnimation(newProgress, 1000) // =1s

            // Set Progress Max
            progressMax = 100f

        }
    }

    //    for create and update tasks
    fun mutaionsDialog() {

        activity?.let {
            val mDialogView = LayoutInflater.from(it).inflate(R.layout.new_task_dialog, null)

            var doTaskMutation: Button = mDialogView.findViewById(R.id.taskDoMutation)
            var taskName: EditText = mDialogView.findViewById(R.id.task_title)

            if (isUpdateTask) {
                taskName.setText(selectedTask.taskName)
                doTaskMutation.setText("UPDATE")
            } else {
                taskName.setText("")
                doTaskMutation.setText("CERATE")
            }


            val mBuilder = AlertDialog.Builder(it)
                .setView(mDialogView)
                .setTitle("")
            val mAlertDialog = mBuilder.show()
            doTaskMutation.setOnClickListener {
                if (!taskName.text.isEmpty()) {
                    if (!isUpdateTask) {
                        todoViewModel.insertTask(
                            taskModel = TaskModel(
                                taskName = taskName.text.toString(),
                                isDone = false,
                                todoId = todoModel?.id
                            )
                        )
                    } else {
                        selectedTask.taskName = taskName.text.toString()
                        todoViewModel.updateTask(
                            taskModel = selectedTask
                        )

                    }
                    isUpdateTask = false
                    mAlertDialog.dismiss()
                }
            }



            mAlertDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        }

    }

    override fun onUpdateClicked(selectedItem: TaskModel) {
        isUpdateTask = true
        selectedTask = selectedItem
        mutaionsDialog()
    }

    override fun onDeleteClicked(selectedItem: TaskModel) {
        todoViewModel.deleteTask(taskModel = selectedItem)
    }


    companion object {

        fun newInstance(todoModel: TodoModel) =
            TodoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_INDEX, todoModel)
                }
            }
    }
}


