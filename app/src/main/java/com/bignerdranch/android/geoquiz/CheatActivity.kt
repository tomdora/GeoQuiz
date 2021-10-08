package com.bignerdranch.android.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import java.security.AccessControlContext

const val EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown"
private const val EXTRA_VALUE_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true"
private const val KEY_CHEAT = "index"
private var answerIsTrue = false

class CheatActivity : AppCompatActivity() {
    private lateinit var answerTextView: TextView
    private lateinit var numCheatsTextView: TextView
    private lateinit var showAnswerButton: Button

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        val cheatsLeft = savedInstanceState?.getInt(KEY_CHEAT, 3) ?: 3
        quizViewModel.cheatsLeft = cheatsLeft

        answerIsTrue = intent.getBooleanExtra(EXTRA_VALUE_IS_TRUE, false)
        answerTextView = findViewById(R.id.answer_text_view)
        numCheatsTextView = findViewById(R.id.num_cheats_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)
        showAnswerButton.setOnClickListener {
            val answerText = when{
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            quizViewModel.decreaseCheats()
            answerTextView.setText(answerText)
            setAnswerShownResult(true)
            checkCheats()
            disableCheats()
        }

        checkCheats()
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean){
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    private fun disableCheats() {
        showAnswerButton.isEnabled = false
        showAnswerButton.isClickable = false
    }

    private fun checkCheats() {
        numCheatsTextView.setText(quizViewModel.cheatsLeft.toString())

        if (quizViewModel.cheatsLeft == 0) {
            disableCheats()
        }
    }

    companion object{
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent{
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_VALUE_IS_TRUE, answerIsTrue)
            }
        }
    }
};