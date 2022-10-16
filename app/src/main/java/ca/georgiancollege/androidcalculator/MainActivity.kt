package ca.georgiancollege.androidcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        if (resultLabel?.text == "0"){
            resultLabel?.text = pressedButtonText
        }
        else{
            resultLabel?.text = resultLabel?.text.toString().plus(pressedButtonText);
        }
    }

    fun Evaluate(view: View)
    {

    }

    fun Delete(view: View){

        val resultLabelText = resultLabel?.text

        //If the length is higher then two, we can safely check if the last input was an operation symbol.
        if (resultLabelText?.length!! > 2){

            //Check if the last input is the operation symbol. If it is, remove the last 3 characters.
            if (operationSymbols.contains(resultLabelText?.get(resultLabelText.count() - 2).toString())) {
                resultLabel?.text = resultLabel?.text?.substring(0, resultLabel!!.text.count() - 2)
            }
        }

        resultLabel?.text = resultLabel?.text?.substring(0, resultLabel!!.text.count() - 1)

        if (resultLabel?.text!!.isEmpty()){
            resultLabel?.text = "0"
        }
    }

    fun Clear(view: View){
        resultLabel?.text = "0"
    }



}