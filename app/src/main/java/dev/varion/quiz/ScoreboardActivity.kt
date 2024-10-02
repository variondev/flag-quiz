package dev.varion.quiz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ScoreboardActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scoreboard)

        findViewById<TextView>(R.id.name).text = intent.getStringExtra("username")

        val correctlyAnswered = intent.getIntExtra("correctly_answered", 0)
        val totalQuestions = intent.getIntExtra("total_questions", 0)
        findViewById<TextView>(R.id.score).text =
            "You scored $correctAnswer out of ${totalQuestions}!"

        findViewById<Button>(R.id.finish_button).setOnClickListener {
            startActivity(Intent(this, QuestionActivity::class.java))
        }
    }
}
