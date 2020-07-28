package com.example.btncounterapp.data_binding_view_binding_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.btncounterapp.R
import com.example.btncounterapp.databinding.ActivityReBindingBinding

class ReBindingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val  binding  : ActivityReBindingBinding= DataBindingUtil.setContentView(this,R.layout.activity_re_binding)

    }
}
