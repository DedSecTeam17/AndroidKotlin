package com.example.btncounterapp.todo_list_app.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.btncounterapp.R
import com.example.btncounterapp.todo_list_app.ShowTodoActivity
import com.example.btncounterapp.todo_list_app.Models.TodoModel
import com.example.btncounterapp.todo_list_app.OfflineDataBase.TodoViewModel
import com.example.btncounterapp.todo_list_app.TodoMutationActivity
import java.text.SimpleDateFormat
import android.text.format.DateUtils
import java.util.*


class TodoListViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.todo_row, parent, false)) {

    private var title: TextView? = null
    private var date: TextView? = null
    var delete: ImageView? = null
    var edit: ImageView? = null
    var done: CheckBox? = null
    var item: CardView? = null


    init {

        title = itemView.findViewById<TextView>(R.id.title)
        date = itemView.findViewById<TextView>(R.id.date)
        delete = itemView.findViewById<ImageView>(R.id.delete)
        edit = itemView.findViewById<ImageView>(R.id.edit)
        done = itemView.findViewById<CheckBox>(R.id.done)
        item = itemView.findViewById<CardView>(R.id.show)


    }

    fun bind(todo: TodoModel) {
        title?.text = todo.title
        var inputFormat = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val mDate = inputFormat.parse(todo.date)
        val niceDateStr = DateUtils.getRelativeTimeSpanString(
            mDate.getTime(),
            Calendar.getInstance().getTimeInMillis(),
            DateUtils.MINUTE_IN_MILLIS
        )


        date?.text = niceDateStr.toString()
        done?.isChecked = todo.isDone!!
    }


}


class TodoListAdapter(
    private var todos: List<TodoModel>,
    var ctx: Context,
    var viewModel: TodoViewModel
) :
    RecyclerView.Adapter<TodoListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val inflater = LayoutInflater.from(ctx)
        return TodoListViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = todos.size

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        holder.bind(todo = todos.get(position))




        holder.delete?.setOnClickListener {
            viewModel.delete(todos[position])
        }
        holder.edit?.setOnClickListener { view ->
            var i = Intent(ctx, TodoMutationActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            i.action = TodoMutationActivity.IS_FOR_EDIT
            i.putExtra("todo", todos[position])

            ctx.startActivity(i)
        }

        holder.done?.setOnCheckedChangeListener { _, isChecked ->

            todos[position].isDone = isChecked
            viewModel.update(todos[position])


        }

        holder.item?.setOnClickListener {
            var i = Intent(ctx, ShowTodoActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            i.putExtra("index", position)

            ctx.startActivity(i)
        }
    }

    internal fun setTodos(todos: List<TodoModel>) {
        this.todos = todos
        notifyDataSetChanged()
    }

}