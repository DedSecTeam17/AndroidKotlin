package com.example.btncounterapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.btncounterapp.camera_app.CameraActivity

class MainActivity : AppCompatActivity() {


    private var calculate: Button? = null
    private var result: TextView? = null
    private var input: EditText? = null


    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calculate = findViewById<Button>(R.id.calculate)
        input = findViewById<EditText>(R.id.input)
        input?.setText("1")

        result = findViewById<TextView>(R.id.result)

        calculate?.setOnClickListener {

            var value: Int? = input?.text.toString().toIntOrNull()
            if (value != null) {
                count += value
            }



            result?.setText(count.toString())


//            Toast.makeText(applicationContext, "$count", Toast.LENGTH_LONG).show()
        }


    }


    fun takeImage(view: View) {
        var i = Intent(applicationContext, CameraActivity::class.java)
        startActivity(i)
    }


}
