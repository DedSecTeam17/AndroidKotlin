package com.example.btncounterapp.calculator_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.btncounterapp.R
import java.lang.Exception

class CalculatorActivity : AppCompatActivity() {


    private var mResult: TextView? = null
    private var mEquation: TextView? = null

    private var equation = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)


        mResult = findViewById<TextView>(R.id.result)
        mResult?.setText("")
        mEquation = findViewById<TextView>(R.id.equation)

    }

    private fun updateEquation() {
        mEquation?.visibility = View.VISIBLE

        if (equation.size <= 0)
            mResult?.setText("")
        var equationAsString = ""
        for (op in equation) {
            equationAsString += op
        }
        mEquation?.setText(equationAsString)

        calculate()
    }


    private fun getEquationAsString(): String {

        var equationAsString = ""
        for (op in equation) {
            equationAsString += op
        }
        return equationAsString
    }


    //    function
    fun clear(view: View) {
        equation.clear()
        mResult?.setText("")
        updateEquation()


    }

    fun dividePlusSub(view: View) {}
    fun mod(view: View) {
        equation.add("%")
        updateEquation()
    }

    fun equal(view: View) {
        mEquation?.visibility = View.INVISIBLE
        calculate()
    }

    private fun calculate() {
        try {
            if (getEquationAsString().isNotEmpty()) {
                var result: String = eval(getEquationAsString()).toString()
                mResult?.setText(result)


            }


        } catch (e: Exception) {
//            mResult?.setText("")
        }
    }


    fun seven(view: View) {
        equation.add("7")
        updateEquation()
    }

    fun eigth(view: View) {
        equation.add("8")
        updateEquation()
    }

    fun nine(view: View) {
        equation.add("9")
        updateEquation()
    }

    fun divide(view: View) {
        equation.add("/")
        updateEquation()
    }

    fun four(view: View) {
        equation.add("4")
        updateEquation()
    }

    fun five(view: View) {
        equation.add("5")
        updateEquation()
    }

    fun six(view: View) {
        equation.add("6")
        updateEquation()
    }

    fun multiplay(view: View) {
        equation.add("*")
        updateEquation()
    }

    fun one(view: View) {
        equation.add("1")
        updateEquation()
    }

    fun tow(view: View) {
        equation.add("2")
        updateEquation()
    }

    fun three(view: View) {
        equation.add("3")
        updateEquation()
    }

    fun minus(view: View) {
        equation.add("-")
        updateEquation()
    }

    fun zero(view: View) {
        equation.add("0")
        updateEquation()
    }

    fun dot(view: View) {
        equation.add(".")
        updateEquation()
    }

    fun delete(view: View) {
        if (equation.size > 0)
            equation.removeAt(equation.size - 1)
        updateEquation()
    }

    fun plus(view: View) {
        equation.add("+")
        updateEquation()
    }


}
