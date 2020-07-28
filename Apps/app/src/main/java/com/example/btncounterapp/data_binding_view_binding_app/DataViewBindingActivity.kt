package com.example.btncounterapp.data_binding_view_binding_app


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.example.btncounterapp.R
import com.example.btncounterapp.databinding.ActivityDataViewBindingBinding
import com.example.btncounterapp.todo_list_app.OfflineDataBase.TodoViewModel





class DataViewBindingActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_data_view_binding)

        val binding: ActivityDataViewBindingBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_data_view_binding)

        binding.lifecycleOwner = this

        binding.fname = "Mohammed "
        binding.lname = "Elamin "
        var viewModel = ViewModelProvider(this).get(MyAppViewModel::class.java)
        binding.viewmodel = viewModel


    }




//    binding adapter
}

