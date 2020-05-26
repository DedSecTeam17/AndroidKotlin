package com.example.btncounterapp.todo_list_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.btncounterapp.R
import com.example.btncounterapp.todo_list_app.Models.TodoModel

private const val ARG_INDEX = "index"

class TodoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var todoModel: TodoModel? = null

    private var title: TextView? = null
    private var description: TextView? = null


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
        description?.text = todoModel?.description


        return view
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


