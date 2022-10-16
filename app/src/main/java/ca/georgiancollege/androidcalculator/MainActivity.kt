package ca.georgiancollege.androidcalculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.flow.combine

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
    var historyLabel: TextView? = null
    var operationSymbols = arrayListOf<String>("÷", "×", "-", "+", "%")

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultLabel = findViewById<TextView>(R.id.resultLabel);
        historyLabel = findViewById<TextView>(R.id.historyLabel);

        resultLabel?.text = "0";
    }

    fun ButtonPressed(view: View)
    {
        val pressedButtonInfo = view as Button
        val pressedButtonText = pressedButtonInfo.text

        if (resultLabel?.text == "0" && pressedButtonText != "."){
            resultLabel?.text = pressedButtonText
        }
        else{
            resultLabel?.text = resultLabel?.text.toString().plus(pressedButtonText);
        }
    }

    fun Delete(view: View){

        val resultLabelText = resultLabel?.text

        //If the length is higher then two, we can safely check if the last input was an operation symbol.
        if (resultLabelText?.length!! > 2){

            //Check if the last input is the operation symbol. If it is, remove the last 3 characters.
            if (operationSymbols.contains(resultLabelText[resultLabelText.count() - 2].toString())) {
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
        historyLabel?.text = "";
    }

    fun ChangePolarity(view: View){
        val equation = resultLabel?.text?.split(" ")?.toMutableList()

        equation?.set(equation.size - 1, (equation[equation.size - 1].toDouble() * -1).toString());

        resultLabel?.text = equation?.joinToString(" ")
    }

    fun Evaluate(view: View)
    {
        historyLabel?.text = resultLabel?.text.toString().plus(" =");
        var equation = resultLabel?.text?.split(" ")?.toMutableList()
        var result = "";
        var head: List<String>;
        var tail: List<String>;


        if (equation != null && equation.size != 1) {
            while (equation!!.size > 1){
                for ((index, value) in equation!!.withIndex()) {
                    println("the element at $index is $value")

                    if (listOf("÷", "×", "%").contains(equation[index])){
                        when(equation[index]){
                            "÷" -> result = (equation[index - 1].toDouble() / equation[index + 1].toDouble()).toString()
                            "×" -> result = (equation[index - 1].toDouble() * equation[index + 1].toDouble()).toString()
                            "%" -> result = (equation[index - 1].toDouble() % equation[index + 1].toDouble()).toString()
                        }
                    }

                    else if (listOf("+", "-").contains(equation[index]) && !equation.any { it == "÷" || it == "×" || it == "%" }){
                        when(equation[index]){
                            "+" -> result = (equation[index - 1].toDouble() + equation[index + 1].toDouble()).toString()
                            "-" -> result = (equation[index - 1].toDouble() - equation[index + 1].toDouble()).toString()
                        }
                    }

                    if (result.isNotEmpty()){
                        head = equation.slice(0 until index - 1)
                        tail = equation.slice(index + 2 until equation.size)
                        equation = (head + result + tail).toMutableList()


                        result = ""
                        break;
                    }
                }
            }
        }

        else{

        }

        resultLabel?.text = equation?.first();
    }
}
