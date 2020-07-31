package com.example.btncounterapp.navigation_component.adapters


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import  androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.btncounterapp.R
import com.example.btncounterapp.todo_list_app.ShowTodoActivity
import com.example.btncounterapp.todo_list_app.Models.TodoModel
import com.example.btncounterapp.todo_list_app.OfflineDataBase.TodoViewModel
import com.example.btncounterapp.todo_list_app.TodoMutationActivity
import java.text.SimpleDateFormat
import android.text.format.DateUtils
import android.widget.TextView
import java.util.*

///TodoListener

interface TodoListener {
    fun onItemClicked(text: String)
}


class TodoListNavHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.nav_todo_row, parent, false)) {

    private var title: TextView? = null
    var item: CardView? = null


    //    on initialization
    init {
        title = itemView.findViewById<TextView>(R.id.nav_todo_item)
        item = itemView.findViewById<CardView>(R.id.show_nav)
    }

    fun bind(text: String) {
        title?.text = text

    }


}


class TodoListNavAdapter(
        private var todos: List<String>,
        var ctx: Context,
        var todoListener: TodoListener

) :
        RecyclerView.Adapter<TodoListNavHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListNavHolder {
        val inflater = LayoutInflater.from(ctx)
        return TodoListNavHolder(inflater, parent)
    }

    override fun getItemCount(): Int = todos.size

    override fun onBindViewHolder(holder: TodoListNavHolder, position: Int) {
        holder.bind(text = todos.get(position))


        holder.item?.setOnClickListener {
            println("clicked")
//          we need to call the interface
            todoListener.onItemClicked(todos.get(position))
        }

    }

    internal fun setTodos(todos: List<String>) {
        this.todos = todos
        notifyDataSetChanged()
    }

}