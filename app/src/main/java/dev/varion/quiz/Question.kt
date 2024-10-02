package dev.varion.quiz

data class Question(
    val id: Int,
    val image: Int,
    val correctAnswerIndex: Int,
    val availableAnswers: List<String>,
)