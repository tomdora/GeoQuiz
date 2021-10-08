package com.bignerdranch.android.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel: ViewModel() {
    var currentIndex = 0
    var isCheater = false
    var cheatsLeft = 3

    private val questionBank = listOf(
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true),
        Question(R.string.question_australia, true),
        Question(R.string.question_africa, false),
        Question(R.string.question_mideast, false),
        Question(R.string.question_oceans, true)
    )

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveToPrev(){
        currentIndex -= 1
        if(currentIndex < 0){
            currentIndex = questionBank.size - 1
        }
    }

    fun moveToNext(){
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun decreaseCheats(){
        cheatsLeft -= 1
    }
}