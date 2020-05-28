package com.example.btncounterapp.todo_list_app.adapters

import android.content.Context
import android.os.Build
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.btncounterapp.R
import com.example.btncounterapp.todo_list_app.Models.TaskModel
import com.example.btncounterapp.todo_list_app.Models.TodoModel
import com.example.btncounterapp.todo_list_app.OfflineDataBase.TodoViewModel
import java.text.SimpleDateFormat
import java.util.*


class TaskViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(
    inflater.inflate(
        R.layout.task_row, parent, false
    )
) {


    private var title: TextView? = null
    var done: CheckBox? = null


    lateinit var delete: ImageView
    lateinit var update: ImageView


    init {
        title = itemView.findViewById<TextView>(R.id.task_title)
        done = itemView.findViewById<CheckBox>(R.id.task_done)

        delete = itemView.findViewById<ImageView>(R.id.delete_task)
        update = itemView.findViewById<ImageView>(R.id.update_task)

    }

    fun bind(task: TaskModel) {
        title?.text = task.taskName
        done?.isChecked = task.isDone!!
    }
}

class TaskListAdapter(
    var tasks: List<TaskModel>, var ctx: Context,
    var viewModel: TodoViewModel,
    var taskOperations: TaskOperations
) : RecyclerView.Adapter<TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TaskViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        holder.bind(task = tasks[position])

        if (position % 2 == 0)
            holder.itemView.setBackgroundColor(ctx.resources.getColor(R.color.opactiy_a))
        else
            holder.itemView.setBackgroundColor(ctx.resources.getColor(R.color.opactiy_b))


        holder.done?.setOnCheckedChangeListener { _, isChecked ->
            tasks[position].isDone = isChecked
            viewModel.updateTask(tasks[position])
        }

        holder.delete.setOnClickListener {
            taskOperations.onDeleteClicked(selectedItem = tasks[position])


        }

        holder.update.setOnClickListener {

            taskOperations.onUpdateClicked(selectedItem = tasks[position])

        }

    }

    internal fun setTasks(tasks: List<TaskModel>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }
}

interface TaskOperations {
    fun onUpdateClicked(selectedItem: TaskModel)
    fun onDeleteClicked(selectedItem: TaskModel)

}