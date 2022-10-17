package ca.georgiancollege.androidcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlin.math.roundToInt

/*
* File name: MainActivity.kt
* Author's name: Alexander Shevchuk
* StudentID: 200454517
* Date: October 2, 2022
* App Description: Calculator built for android devices.
* Version: 1.0
* */

class MainActivity : AppCompatActivity() {

    // Instance members of the MainActivity class
    private var resultLabel: TextView? = null
    private var historyLabel: TextView? = null
    private var operationSymbols = arrayListOf("÷", "×", "-", "+", "%")


    /*
    * Default on create function which generates the view.
    */
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultLabel = findViewById(R.id.resultLabel)
        historyLabel = findViewById(R.id.historyLabel)

        resultLabel?.text = "0"
    }

    /*
    * This method is responsible for all of the operator and number buttons.
    */
    fun buttonPressed(view: View)
    {
        val pressedButtonInfo = view as Button
        val pressedButtonText = pressedButtonInfo.text

        if (resultLabel?.text == "0" && pressedButtonText != "."){
            resultLabel?.text = pressedButtonText
        }
        else{
            resultLabel?.text = resultLabel?.text.toString().plus(pressedButtonText)
        }
    }

    /*
    * The Delete method is called when the delete (backspace) button is pressed. Removes the last entered character.
    */
    fun delete(view: View){

        val resultLabelText = resultLabel?.text

        //If the length is higher then two, we can safely check if the last input was an operation symbol.
        if (resultLabelText?.length!! > 2){
            //Check if the last input is the operation symbol. If it is, remove the last 3 characters.
            if (operationSymbols.contains(resultLabelText[resultLabelText.count() - 2].toString()) && resultLabelText[resultLabelText.count() - 1].toString() == " ") {
                resultLabel?.text = resultLabel?.text?.substring(0, resultLabel!!.text.count() - 2)
            }
        }

        resultLabel?.text = resultLabel?.text?.substring(0, resultLabel!!.text.count() - 1)

        if (resultLabel?.text!!.isEmpty()){
            resultLabel?.text = "0"
        }
    }

    /*
    * The Clear method is called when the clear button is pressed. It resets the history as well as the current entered numbers.
    */
    fun clear(view: View){
        resultLabel?.text = "0"
        historyLabel?.text = ""
    }

    /*
    * The ChangePolarity method is called when the plus/minus button is pressed. It reverses the polarity of the last entered number.
    */
    fun changePolarity(view: View){
        val equation = resultLabel?.text?.split(" ")?.toMutableList()

        if (!operationSymbols.contains(equation?.get(equation.size - 1)))
            equation?.set(equation.size - 1, (equation[equation.size - 1].toInt() * -1).toString())

        resultLabel?.text = equation?.joinToString(" ")
    }

    /*
    * This is the function which handles all the evaluating of the equation. It is executed when the equal sign is pressed.
    */
    fun evaluate(view: View)
    {

        //split the equation into an array of strings
        var equation = resultLabel?.text?.split(" ")?.toMutableList()
        var result = ""
        var head: List<String>
        var tail: List<String>

        //if the equation isnt a single number, we do the process. Otherwise we return the single number

        if (equation?.size!! > 2) {

            //if user clicked evaluate while the last input was an operator, we remove the last operator from the equation.
            if (operationSymbols.contains(equation[equation.size - 2]) && equation[equation.size - 1].isEmpty()){
                equation.removeAt(equation.size - 1)
                equation.removeAt(equation.size - 1)
            }

            //set the history label
            historyLabel?.text = equation.joinToString(" ").plus(" =")


            //while we have multiple operators/numbers, we keep looping to solve them one at a time.
            while (equation!!.size > 1){

                //we go through each number/operator in the equation.
                for ((index, value) in equation!!.withIndex()) {

                    //check for div, multi, modulus
                    if (listOf("÷", "×", "%").contains(equation[index])){
                        when(equation[index]){
                            "÷" -> result = (equation[index - 1].toDouble() / equation[index + 1].toDouble()).toString()
                            "×" -> result = (equation[index - 1].toDouble() * equation[index + 1].toDouble()).toString()
                            "%" -> result = (equation[index - 1].toDouble() % equation[index + 1].toDouble()).toString()
                        }
                    }

                    //check for addition, sub if there are no more of the above in the equation
                    else if (listOf("+", "-").contains(equation[index]) && !equation.any { it == "÷" || it == "×" || it == "%" }){
                        when(equation[index]){
                            "+" -> result = (equation[index - 1].toDouble() + equation[index + 1].toDouble()).toString()
                            "-" -> result = (equation[index - 1].toDouble() - equation[index + 1].toDouble()).toString()
                        }
                    }

                    //if the index fell on an operator, there would have been a result. we take the result, take apart the equation, insert it where needed, and reset the loop to start over.
                    if (result.isNotEmpty()){
                        head = equation.slice(0 until index - 1)
                        tail = equation.slice(index + 2 until equation.size)
                        equation = (head + result + tail).toMutableList()


                        result = ""
                        break
                    }
                }
            }
        }

        //after all is set and done, set the result label. Round it as well if needed.
        resultLabel?.text = if (equation.first().toDouble() % 1 == 0.0) equation.first().toDouble().roundToInt().toString() else "%.8f".format(equation.first().toDouble())
    }
}
