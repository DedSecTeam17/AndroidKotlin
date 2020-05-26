package com.example.btncounterapp.todo_list_app.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.btncounterapp.todo_list_app.TodoFragment
import com.example.btncounterapp.todo_list_app.Models.TodoModel

class TodosPageAdapter(fragmentManager: FragmentManager, private val todos: List<TodoModel>) :
    FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return TodoFragment.newInstance(todoModel = todos.get(position))
    }

    override fun getCount(): Int {
        return todos.size
    }
}