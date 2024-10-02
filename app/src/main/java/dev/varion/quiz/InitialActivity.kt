package dev.varion.quiz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText

class InitialActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial)

        mapOf(
            Pair("europe", R.id.tv_europe),
            Pair("africa", R.id.tv_africa),
            Pair("asia", R.id.tv_asia),
            Pair("north_america", R.id.tv_north_america),
            Pair("oceania", R.id.tv_oceania),
            Pair("south_america", R.id.tv_south_america)
        ).forEach {
            findViewById<TextView>(it.value).setOnClickListener { _ ->
                val nameInput: EditText = findViewById(R.id.input_name)
                if (nameInput.text.isEmpty()) {
                    makeText(this, "Please enter your name!", LENGTH_LONG).show()
                } else {
                    val intent = Intent(this, QuestionActivity::class.java)
                    intent.putExtra("user_name", nameInput.text.toString())
                    intent.putExtra("chosen_continent", it.key.toString())
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}