package ca.georgiancollege.androidcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

/*
* File name: MainActivity.kt
* Author's name: Alexander Shevchuk
* StudentID: 200454517
* Date: October 2, 2022
* App Description: Calculator built for android devices.
* Version: 0.1
* */

class MainActivity : AppCompatActivity() {

    // Instance members of the MainActivity class
    var resultLabel: TextView? = null
    var operationSymbols = arrayListOf<String>("÷", "×", "-", "+")

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultLabel = findViewById<TextView>(R.id.resultLabel);

        resultLabel?.text = "0";
    }

    fun ButtonPressed(view: View)
    {
        val pressedButtonInfo = view as Button
        val pressedButtonText = pressedButtonInfo.text

        when(pressedButtonText){
            "0" -> resultLabel?.text = pressedButtonText;
            else -> {
//                resultLabel?.text += pressedButtonText;
            }
        }

    }

    fun Evaluate()
    {

    }

}