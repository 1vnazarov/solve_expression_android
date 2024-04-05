package com.example.solve_expressions

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.solve_expressions.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var correctAnswers = 0
    private var incorrectAnswers = 0
    private var totalAnswers = 0
    private var color = Color.TRANSPARENT

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.checkBtn.isEnabled = false
        binding.startBtn.setOnClickListener {
            it.isEnabled = false
            binding.checkBtn.isEnabled = true
            binding.answer.text.clear()
            setColor(Color.TRANSPARENT)
            generateExample()
        }

        binding.checkBtn.setOnClickListener {
            it.isEnabled = false
            binding.startBtn.isEnabled = false
            checkAnswer()
        }
    }

    private fun setColor(c: Int) {
        color = c
        binding.answer.setBackgroundColor(color)
    }

    private fun updateUI() {
        binding.countRightText.text = "$correctAnswers"
        binding.countErrorText.text = "$incorrectAnswers"
        binding.countSolveText.text = "$totalAnswers"
        binding.percentText.text = "%.2f%%".format(correctAnswers.toDouble() / totalAnswers * 100)
    }

    private fun generateExample() {
        val num1 = (10..99).random()
        var num2 = (10..99).random()

        // Убеждаемся, что частное будет целым
        while (num1 % num2 != 0) {
            num2 = (10..99).random()
        }
        binding.firstOperand.text = "$num1"
        binding.secondOperand.text = "$num2"
        when ((0..3).random()) {
            0 -> binding.operator.text = "*"
            1 -> binding.operator.text = "/"
            2 -> binding.operator.text = "-"
            3 -> binding.operator.text = "+"
        }
    }

    private fun checkAnswer() {
        val answer = binding.answer.text.toString().toIntOrNull() ?: 0

        val num1 = binding.firstOperand.text.toString().toInt()
        val num2 = binding.secondOperand.text.toString().toInt()
        val operator = binding.operator.text.toString()
        var result = 0
        when (operator) {
            "*" -> result = num1 * num2
            "/" -> result = num1 / num2
            "-" -> result = num1 - num2
            "+" -> result = num1 + num2
        }

        if (answer == result) {
            setColor(Color.GREEN)
            correctAnswers++
        } else {
            setColor(Color.RED)
            incorrectAnswers++
        }
        totalAnswers++
        updateUI()
        binding.startBtn.isEnabled = true
    }

    override fun onSaveInstanceState(instanceState: Bundle) {
        super.onSaveInstanceState(instanceState)
        instanceState.putInt("correctAnswers", correctAnswers)
        instanceState.putInt("incorrectAnswers", incorrectAnswers)
        instanceState.putInt("totalAnswers", totalAnswers)
        instanceState.putInt("color", color)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        correctAnswers = savedInstanceState.getInt("correctAnswers")
        incorrectAnswers = savedInstanceState.getInt("incorrectAnswers")
        totalAnswers = savedInstanceState.getInt("totalAnswers")
        color = savedInstanceState.getInt("color")
        updateUI()
        setColor(color)
    }
}