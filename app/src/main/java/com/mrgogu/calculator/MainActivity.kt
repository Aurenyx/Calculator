package com.mrgogu.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope

class MainActivity : AppCompatActivity() {

    private var result_tv : TextView? = null
    var lastNumeric: Boolean = false
    var lastDot: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        result_tv = findViewById(R.id.result_tv)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun onDigit(view: View){
        result_tv?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }
    fun onClear(view: View){
        result_tv?.text = ""
    }

    fun onDecimalPoint(view: View){
        if (lastNumeric && !lastDot){
            result_tv?.append(".")
            lastNumeric = false
            lastDot = false
        }
    }

    fun onOperator(view: View) {
        val btn = view as Button
        val value = result_tv?.text.toString()

        if (value.isEmpty()) {
            if (btn.text == "-") {
                result_tv?.append(btn.text)
            }
        } else if (lastNumeric && !isOperatorAdded(value)) {
            result_tv?.append(btn.text)
            lastNumeric = false
            lastDot = false
        }
    }
    fun toEqual(view: View) {
        if (lastNumeric) {
            var tvValue = result_tv?.text.toString()

            try {
                var operator = ""
                var splitValue: List<String> = emptyList()

                when {
                    tvValue.contains("+") -> {
                        operator = "+"
                        splitValue = tvValue.split("+")
                    }

                    tvValue.contains("-") -> {
                        operator = "-"
                        splitValue = tvValue.split("-")
                    }

                    tvValue.contains("/") -> {
                        operator = "/"
                        splitValue = tvValue.split("/")
                    }

                    tvValue.contains("*") -> {
                        operator = "*"
                        splitValue = tvValue.split("*")
                    }
                }
                if (splitValue.size == 2) {
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (one.contains(".").not() && two.contains(".").not()) {
                        val result = when (operator) {
                            "+" -> one.toInt() + two.toInt()
                            "-" -> one.toInt() - two.toInt()
                            "*" -> one.toInt() * two.toInt()
                            "/" -> one.toFloat() / two.toFloat()
                            else -> 0
                        }
                        result_tv?.text = result.toString()
                    } else {
                        val result = when (operator) {
                            "+" -> one.toDouble() + two.toDouble()
                            "-" -> one.toDouble() - two.toDouble()
                            "*" -> one.toDouble() * two.toDouble()
                            "/" -> one.toDouble() / two.toDouble()
                            else -> 0.0
                        }
                        result_tv?.text = result.toString()
                    }
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
                }
             }
        }
        private fun isOperatorAdded(value: String): Boolean {

            val checkValue = if (value.startsWith("-")) value.substring(1) else value
            return checkValue.contains("/") ||
                    checkValue.contains("+") ||
                    checkValue.contains("-") ||
                    checkValue.contains("*")
        }
    }
