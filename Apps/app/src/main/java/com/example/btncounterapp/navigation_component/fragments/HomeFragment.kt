package com.example.btncounterapp.navigation_component.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.btncounterapp.R
import com.example.btncounterapp.navigation_component.adapters.TodoListNavAdapter
import com.example.btncounterapp.navigation_component.adapters.TodoListener
import com.example.btncounterapp.todo_list_app.Models.TodoModel
import com.example.btncounterapp.todo_list_app.adapters.TodoListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(), TodoListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    var mAdapter: TodoListNavAdapter? = null
    var data = listOf<String>("workout ", "gaming", "programming", "mediate")


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_home, container, false)


        var addNavTodo = view.findViewById<FloatingActionButton>(R.id.nav_add_todo)
        addNavTodo.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_createFragment)

        }

        var todos = view.findViewById<RecyclerView>(R.id.nav_todos)

        mAdapter = context?.applicationContext?.let {
            TodoListNavAdapter(
                    ctx = it,
                    todos = data,
                    todoListener = this
            )
        }


        todos?.apply {
            layoutManager = LinearLayoutManager(context.applicationContext)
            adapter = mAdapter

        }

        return view
    }


    //    here we will inflate rv and setup it ,
    fun setupListView(rootView: View) {


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                HomeFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    override fun onItemClicked(text: String) {
        println("data" + text);

        var action = HomeFragmentDirections.actionHomeFragmentToViewFragment(title = text)
        findNavController().navigate(action)

//        findNavController().navigate(R.id.action_homeFragment_to_createFragment)

    }
}