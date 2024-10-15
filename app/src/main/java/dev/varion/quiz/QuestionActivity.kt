package dev.varion.quiz

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QuestionActivity : Activity(), View.OnClickListener {
    private val flagContainer = FlagContainer()
    private lateinit var username: String

    private var correctlyAnswered: Int = 0
    private var currentPosition: Int = 1
    private lateinit var questionList: ArrayList<Question>
    private val questionsChosen = ArrayList<Int>()
    private var selectedOptionPosition: Int = 0

    private lateinit var flagImage: ImageView
    private lateinit var firstOption: TextView
    private lateinit var secondOption: TextView
    private lateinit var thirdOption: TextView
    private lateinit var fourthOption: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        username = intent.getStringExtra("username")!!
        val chosenContinent = intent.getStringExtra("chosen_continent")!!

        setContentView(R.layout.activity_question)

        flagImage = findViewById(R.id.flag_image)
        firstOption = findViewById(R.id.first_option)
        secondOption = findViewById(R.id.second_option)
        thirdOption = findViewById(R.id.third_option)
        fourthOption = findViewById(R.id.fourth_option)
        generateQuestions(chosenContinent)
        if (questionList.isEmpty) {
            navigateToScoreboard()
            return
        }

        renderNextQuestion()
        clearOptions()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.first_option -> submitAnswer(firstOption, 1)
            R.id.second_option -> submitAnswer(secondOption, 2)
            R.id.third_option -> submitAnswer(thirdOption, 3)
            R.id.fourth_option -> submitAnswer(fourthOption, 4)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun renderNextQuestion() {

        clearOptions()
        val question: Question = questionList[currentPosition - 1]

        flagImage.setImageResource(question.image)
        val answers = question.availableAnswers
        firstOption.text = answers[0]
        secondOption.text = answers[1]
        thirdOption.text = answers[2]
        fourthOption.text = answers[3]

        firstOption.setOnClickListener(this)
        secondOption.setOnClickListener(this)
        thirdOption.setOnClickListener(this)
        fourthOption.setOnClickListener(this)

        firstOption.isEnabled = true
        secondOption.isEnabled = true
        thirdOption.isEnabled = true
        fourthOption.isEnabled = true
    }

    private fun clearOptions() {
        for (option in listOf(firstOption, secondOption, thirdOption, fourthOption)) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this, R.drawable.default_option_border_bg
            )
        }
    }

    private fun submitAnswer(tv: TextView, selectedOptionNum: Int) {
        clearOptions()
        selectedOptionPosition = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this, R.drawable.selected_option_border_bg
        )

        Log.d("submit", "onClick: Option selected: $selectedOptionPosition")

        firstOption.isEnabled = false
        secondOption.isEnabled = false
        thirdOption.isEnabled = false
        fourthOption.isEnabled = false

        val question = questionList[currentPosition - 1]
        Log.d("submit", question.toString())

        if (question.correctAnswerIndex + 1 != selectedOptionPosition) {
            highlightAnswerOption(selectedOptionPosition, R.drawable.wrong_option_border_bg)
            Log.d(
                "submit", "answer=${selectedOptionPosition}, correct=${question.correctAnswerIndex}"
            )
        } else {
            correctlyAnswered++
        }

        highlightAnswerOption(question.correctAnswerIndex + 1, R.drawable.correct_option_border_bg)
        questionsChosen.add(selectedOptionPosition)
        selectedOptionPosition = 0
        currentPosition++

        CoroutineScope(Dispatchers.Main).launch {
            delay(1000L)
            if (currentPosition <= questionList.size) {
                renderNextQuestion()
            } else {
                navigateToScoreboard()
            }
        }
    }

    private fun navigateToScoreboard() {
        val intent = Intent(this@QuestionActivity, ScoreboardActivity::class.java)
        intent.putExtra("username", username)
        intent.putExtra("correctly_answered", correctlyAnswered)
        intent.putExtra("total_questions", questionList.size)
        startActivity(intent)
        finish()
    }

    private fun highlightAnswerOption(answer: Int, drawableView: Int) {
        val optionTextViews = listOf(firstOption, secondOption, thirdOption, fourthOption)
        if (answer in 1..4) {
            optionTextViews[answer - 1].background = ContextCompat.getDrawable(this, drawableView)
        }
    }

    private fun generateQuestions(continent: String) {
        flagContainer.countriesByContinent[continent]?.let { countriesByContinent ->
            val countries =
                countriesByContinent.filter { flagContainer.flagIdByCountry.contains(it) }

            val chosenCountries = mutableSetOf<String>()

            val numberOfQuestions = minOf(100, countries.size) + 1
            Log.d("produceQuestions", "countries: ${countries.size}")
            Log.d("produceQuestions", "questions: $numberOfQuestions")

            for (i in 1 until numberOfQuestions) {
                var correctAnswer = countries.random()
                while (chosenCountries.contains(correctAnswer)) {
                    correctAnswer = countries.random()
                }
                chosenCountries.add(correctAnswer)
                flagContainer.flagIdByCountry[correctAnswer]?.let {

                    Log.d("produceQuestions", "correct [$i]: $correctAnswer")

                    val answers = mutableSetOf<String>()
                    while (answers.size < 3) {
                        val randomAnswer = countries.random()
                        if (randomAnswer != correctAnswer) {
                            answers.add(randomAnswer)
                        }
                    }

                    Log.d("produceQuestions", "wrong [$i]: ${answers.joinToString()}")

                    val answerList = answers.toMutableList()
                    answerList.add(correctAnswer)
                    answerList.shuffle()

                    questionList.add(
                        Question(
                            i, it, answerList.indexOf(correctAnswer), answerList
                        )
                    )
                }
            }
        }
    }
}