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

    private val questionsChosen = ArrayList<Int>()

    private lateinit var mUserName: String
    private var mCorrectAnswers: Int = 0
    private var mCurrentPosition: Int = 1
    private lateinit var mQuestionsList: ArrayList<Question>
    private var mSelectedOptionPosition: Int = 0

    private var ivImage: ImageView? = null

    private lateinit var firstOption: TextView
    private lateinit var secondOption: TextView
    private lateinit var thirdOption: TextView
    private lateinit var fourthOption: TextView

    private val flagIdByCountry: MutableMap<String, Int> = mutableMapOf()

    init {
        flagIdByCountry["Austria"] = R.drawable.ic_flag_austria
        flagIdByCountry["Belize"] = R.drawable.ic_flag_belize
        flagIdByCountry["Benin"] = R.drawable.ic_flag_benin
        flagIdByCountry["Germany"] = R.drawable.ic_flag_germany
        flagIdByCountry["Afghanistan"] = R.drawable.ic_flag_of_afghanistan
        flagIdByCountry["Albania"] = R.drawable.ic_flag_of_albania
        flagIdByCountry["Algeria"] = R.drawable.ic_flag_of_algeria
        flagIdByCountry["American Samoa"] = R.drawable.ic_flag_of_american_samoa
        flagIdByCountry["Andorra"] = R.drawable.ic_flag_of_andorra
        flagIdByCountry["Angola"] = R.drawable.ic_flag_of_angola
        flagIdByCountry["Argentina"] = R.drawable.ic_flag_of_argentina
        flagIdByCountry["Armenia"] = R.drawable.ic_flag_of_armenia
        flagIdByCountry["Aruba"] = R.drawable.ic_flag_of_aruba
        flagIdByCountry["Australia"] = R.drawable.ic_flag_of_australia
        flagIdByCountry["Azerbaijan"] = R.drawable.ic_flag_of_azerbaijan
        flagIdByCountry["Bahamas"] = R.drawable.ic_flag_of_bahamas
        flagIdByCountry["Bangladesh"] = R.drawable.ic_flag_of_bangladesh
        flagIdByCountry["Barbados"] = R.drawable.ic_flag_of_barbados
        flagIdByCountry["Belgium"] = R.drawable.ic_flag_of_belgium
        flagIdByCountry["Bermuda"] = R.drawable.ic_flag_of_bermuda
        flagIdByCountry["Bhutan"] = R.drawable.ic_flag_of_bhutan
        flagIdByCountry["Bosnia"] = R.drawable.ic_flag_of_bosnia
        flagIdByCountry["Botswana"] = R.drawable.ic_flag_of_botswana
        flagIdByCountry["Brazil"] = R.drawable.ic_flag_of_brazil
        flagIdByCountry["Brunei"] = R.drawable.ic_flag_of_brunei
        flagIdByCountry["Bulgaria"] = R.drawable.ic_flag_of_bulgaria
        flagIdByCountry["Burkina Faso"] = R.drawable.ic_flag_of_burkina_flso
        flagIdByCountry["Burundi"] = R.drawable.ic_flag_of_burundi
        flagIdByCountry["Canada"] = R.drawable.ic_flag_of_canada
        flagIdByCountry["Cape Verde"] = R.drawable.ic_flag_of_cape_verde
        flagIdByCountry["Chile"] = R.drawable.ic_flag_of_chile
        flagIdByCountry["China"] = R.drawable.ic_flag_of_china
        flagIdByCountry["Christmas Island"] = R.drawable.ic_flag_of_christmas_island
        flagIdByCountry["Colombia"] = R.drawable.ic_flag_of_colombia
        flagIdByCountry["Cote d'Ivoire"] = R.drawable.ic_flag_of_cote_dlvoire
        flagIdByCountry["Denmark"] = R.drawable.ic_flag_of_denmark
        flagIdByCountry["Djibouti"] = R.drawable.ic_flag_of_djibouti
        flagIdByCountry["Dominica"] = R.drawable.ic_flag_of_dominica
        flagIdByCountry["Ecuador"] = R.drawable.ic_flag_of_ecuador
        flagIdByCountry["Egypt"] = R.drawable.ic_flag_of_egypt
        flagIdByCountry["Eritrea"] = R.drawable.ic_flag_of_eritrea
        flagIdByCountry["Faroe Islands"] = R.drawable.ic_flag_of_faroe_islands
        flagIdByCountry["Finland"] = R.drawable.ic_flag_of_finland
        flagIdByCountry["France"] = R.drawable.ic_flag_of_france
        flagIdByCountry["Gabon"] = R.drawable.ic_flag_of_gabon
        flagIdByCountry["Gibraltar"] = R.drawable.ic_flag_of_gibraltar
        flagIdByCountry["Greece"] = R.drawable.ic_flag_of_greece
        flagIdByCountry["Guam"] = R.drawable.ic_flag_of_gunam
        flagIdByCountry["Guatemala"] = R.drawable.ic_flag_of_gutemala
        flagIdByCountry["India"] = R.drawable.ic_flag_of_india
        flagIdByCountry["Japan"] = R.drawable.ic_flag_of_japan
        flagIdByCountry["Luxembourg"] = R.drawable.ic_flag_of_luxembourg
        flagIdByCountry["Maldives"] = R.drawable.ic_flag_of_maldives
        flagIdByCountry["Mali"] = R.drawable.ic_flag_of_mali
        flagIdByCountry["Netherlands"] = R.drawable.ic_flag_of_netherlands
        flagIdByCountry["Niger"] = R.drawable.ic_flag_of_niger
        flagIdByCountry["Pakistan"] = R.drawable.ic_flag_of_pakistan
        flagIdByCountry["Palau"] = R.drawable.ic_flag_of_palau
        flagIdByCountry["Peru"] = R.drawable.ic_flag_of_peru
        flagIdByCountry["Philippines"] = R.drawable.ic_flag_of_philippines
        flagIdByCountry["Portugal"] = R.drawable.ic_flag_of_portugal
        flagIdByCountry["Qatar"] = R.drawable.ic_flag_of_qatar
        flagIdByCountry["Romania"] = R.drawable.ic_flag_of_romania
        flagIdByCountry["Russia"] = R.drawable.ic_flag_of_russian_federation
        flagIdByCountry["Rwanda"] = R.drawable.ic_flag_of_rwanda
        flagIdByCountry["Saint Vincent and the Grenadines"] =
            R.drawable.ic_flag_of_saint_vicent_and_the_grenadines
        flagIdByCountry["Senegal"] = R.drawable.ic_flag_of_senegal
        flagIdByCountry["Seychelles"] = R.drawable.ic_flag_of_seychelles
        flagIdByCountry["Sierra Leone"] = R.drawable.ic_flag_of_sierra_leone
        flagIdByCountry["Singapore"] = R.drawable.ic_flag_of_singapore
        flagIdByCountry["Slovakia"] = R.drawable.ic_flag_of_slovakia
        flagIdByCountry["Solomon Islands"] = R.drawable.ic_flag_of_soloman_islands
        flagIdByCountry["Somalia"] = R.drawable.ic_flag_of_somalia
        flagIdByCountry["Sudan"] = R.drawable.ic_flag_of_sudan
        flagIdByCountry["Suriname"] = R.drawable.ic_flag_of_suriname
        flagIdByCountry["Sweden"] = R.drawable.ic_flag_of_sweden
        flagIdByCountry["Syria"] = R.drawable.ic_flag_of_syria
        flagIdByCountry["Taiwan"] = R.drawable.ic_flag_of_taiwan
        flagIdByCountry["Thailand"] = R.drawable.ic_flag_of_thailand
        flagIdByCountry["Timor-Leste"] = R.drawable.ic_flag_of_timor_leste
        flagIdByCountry["Togo"] = R.drawable.ic_flag_of_togo
        flagIdByCountry["Tonga"] = R.drawable.ic_flag_of_tonga
        flagIdByCountry["Trinidad and Tobago"] = R.drawable.ic_flag_of_trinidad_and_tobago
        flagIdByCountry["Tunisia"] = R.drawable.ic_flag_of_tunisia
        flagIdByCountry["Turkey"] = R.drawable.ic_flag_of_turkey
        flagIdByCountry["Turkmenistan"] = R.drawable.ic_flag_of_turkmenistan
        flagIdByCountry["Turks and Caicos Islands"] = R.drawable.ic_flag_of_turks_and_caicos_islands
        flagIdByCountry["Tuvalu"] = R.drawable.ic_flag_of_tuvalu
        flagIdByCountry["United Arab Emirates"] = R.drawable.ic_flag_of_uae
        flagIdByCountry["Uganda"] = R.drawable.ic_flag_of_uganda
        flagIdByCountry["Ukraine"] = R.drawable.ic_flag_of_ukraine
        flagIdByCountry["United States of America"] = R.drawable.ic_flag_of_united_states_of_america
        flagIdByCountry["Uruguay"] = R.drawable.ic_flag_of_uruguay
        flagIdByCountry["US Virgin Islands"] = R.drawable.ic_flag_of_us_virgin_islands
        flagIdByCountry["Uzbekistan"] = R.drawable.ic_flag_of_uzbekistan
        flagIdByCountry["Vanuatu"] = R.drawable.ic_flag_of_vanuatu
        flagIdByCountry["Vatican City"] = R.drawable.ic_flag_of_vatican_city
        flagIdByCountry["Venezuela"] = R.drawable.ic_flag_of_venezuela
        flagIdByCountry["Vietnam"] = R.drawable.ic_flag_of_vietnam
        flagIdByCountry["Wales"] = R.drawable.ic_flag_of_wales
        flagIdByCountry["Yemen"] = R.drawable.ic_flag_of_yemen
    }

    private val countriesByContinent: Map<String, List<String>> = mapOf(
        "africa" to listOf(
            "Algeria",
            "Angola",
            "Benin",
            "Botswana",
            "Burkina Faso",
            "Burundi",
            "Cote d'Ivoire",
            "Djibouti",
            "Egypt",
            "Eritrea",
            "Gabon",
            "Gambia",
            "Ghana",
            "Guinea",
            "Kenya",
            "Lesotho",
            "Liberia",
            "Libya",
            "Malawi",
            "Mali",
            "Morocco",
            "Namibia",
            "Niger",
            "Nigeria",
            "Rwanda",
            "Senegal",
            "Seychelles",
            "Sierra Leone",
            "Somalia",
            "South Africa",
            "Sudan",
            "Tanzania",
            "Togo",
            "Uganda",
            "Zambia",
            "Zimbabwe"
        ), "asia" to listOf(
            "Afghanistan",
            "Armenia",
            "Azerbaijan",
            "Bangladesh",
            "Bhutan",
            "Brunei",
            "Cambodia",
            "China",
            "Georgia",
            "India",
            "Indonesia",
            "Iran",
            "Iraq",
            "Israel",
            "Japan",
            "Jordan",
            "Kazakhstan",
            "Kuwait",
            "Kyrgyzstan",
            "Laos",
            "Lebanon",
            "Malaysia",
            "Maldives",
            "Mongolia",
            "Myanmar",
            "Nepal",
            "North Korea",
            "Oman",
            "Pakistan",
            "Palestine",
            "Philippines",
            "Qatar",
            "Saudi Arabia",
            "Singapore",
            "South Korea",
            "Sri Lanka",
            "Tajikistan",
            "Thailand",
            "Timor-Leste",
            "Turkmenistan",
            "United Arab Emirates",
            "Uzbekistan",
            "Vietnam",
            "Yemen"
        ), "europe" to listOf(
            "Albania",
            "Andorra",
            "Austria",
            "Belgium",
            "Bosnia",
            "Bulgaria",
            "Croatia",
            "Cyprus",
            "Czech Republic",
            "Denmark",
            "Estonia",
            "Finland",
            "France",
            "Germany",
            "Greece",
            "Hungary",
            "Iceland",
            "Ireland",
            "Italy",
            "Latvia",
            "Lithuania",
            "Luxembourg",
            "Malta",
            "Monaco",
            "Montenegro",
            "Netherlands",
            "North Macedonia",
            "Norway",
            "Poland",
            "Portugal",
            "Romania",
            "San Marino",
            "Slovakia",
            "Slovenia",
            "Spain",
            "Sweden",
            "Switzerland",
            "United Kingdom",
            "Ukraine",
            "Vatican City"
        ), "north_america" to listOf(
            "Antigua and Barbuda",
            "Bahamas",
            "Barbados",
            "Belize",
            "Canada",
            "Costa Rica",
            "Cuba",
            "Dominica",
            "Dominican Republic",
            "El Salvador",
            "Grenada",
            "Guatemala",
            "Haiti",
            "Honduras",
            "Jamaica",
            "Mexico",
            "Nicaragua",
            "Panama",
            "Saint Kitts and Nevis",
            "Saint Lucia",
            "Saint Vincent and the Grenadines",
            "United States of America",
            "US Virgin Islands"
        ), "oceania" to listOf(
            "American Samoa",
            "Australia",
            "Fiji",
            "Kiribati",
            "Marshall Islands",
            "Micronesia",
            "New Zealand",
            "Palau",
            "Papua New Guinea",
            "Samoa",
            "Solomon Islands",
            "Tonga",
            "Tuvalu",
            "Vanuatu"
        ), "south_america" to listOf(
            "Argentina",
            "Bolivia",
            "Brazil",
            "Chile",
            "Colombia",
            "Ecuador",
            "Guyana",
            "Paraguay",
            "Peru",
            "Suriname",
            "Uruguay",
            "Venezuela"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mUserName = intent.getStringExtra("user_name")!!
        val chosenContinent = intent.getStringExtra("chosen_continent")!!

        setContentView(R.layout.activity_question)

        ivImage = findViewById(R.id.iv_image)
        firstOption = findViewById(R.id.first_option)
        secondOption = findViewById(R.id.second_option)
        thirdOption = findViewById(R.id.third_option)
        fourthOption = findViewById(R.id.fourth_option)
        mQuestionsList = produceQuestions(chosenContinent)
        if (mQuestionsList.isEmpty()) {
            finishQuiz()
            return
        }

        nextQuestion()
        resetOptions()
    }

    @SuppressLint("SetTextI18n")
    private fun nextQuestion() {

        resetOptions()
        val question: Question = mQuestionsList[mCurrentPosition - 1]

        ivImage?.setImageResource(question.image)
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

    private fun resetOptions() {
        val optionTextViews = listOf(firstOption, secondOption, thirdOption, fourthOption)
        for (option in optionTextViews) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this, R.drawable.default_option_border_bg
            )
        }
    }

    private fun submit(tv: TextView, selectedOptionNum: Int) {
        resetOptions()
        mSelectedOptionPosition = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this, R.drawable.selected_option_border_bg
        )

        Log.d("submit", "onClick: Option selected: $mSelectedOptionPosition")

        firstOption.isEnabled = false
        secondOption.isEnabled = false
        thirdOption.isEnabled = false
        fourthOption.isEnabled = false

        val question = mQuestionsList[mCurrentPosition - 1]
        Log.d("submit", question.toString())

        if (question.correctAnswerIndex + 1 != mSelectedOptionPosition) {
            answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
            Log.d(
                "submit",
                "answer=${mSelectedOptionPosition}, correct=${question.correctAnswerIndex}"
            )
        } else {
            mCorrectAnswers++
        }

        answerView(question.correctAnswerIndex + 1, R.drawable.correct_option_border_bg)
        questionsChosen.add(mSelectedOptionPosition)
        mSelectedOptionPosition = 0
        mCurrentPosition++

        CoroutineScope(Dispatchers.Main).launch {
            delay(1000L)
            if (mCurrentPosition <= mQuestionsList.size) {
                nextQuestion()
            } else {
                finishQuiz()
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.first_option -> submit(firstOption, 1)
            R.id.second_option -> submit(secondOption, 2)
            R.id.third_option -> submit(thirdOption, 3)
            R.id.fourth_option -> submit(fourthOption, 4)
        }
    }

    private fun finishQuiz() {
        val intent = Intent(this@QuestionActivity, ScoreboardActivity::class.java)
        intent.putExtra("user_name", mUserName)
        intent.putExtra("correct_answer", mCorrectAnswers)
        intent.putExtra("total_questions", mQuestionsList.size)
        startActivity(intent)
        finish()
    }

    private fun answerView(answer: Int, drawableView: Int) {
        val optionTextViews = listOf(firstOption, secondOption, thirdOption, fourthOption)
        if (answer in 1..4) {
            optionTextViews[answer - 1].background = ContextCompat.getDrawable(this, drawableView)
        }
    }

    fun produceQuestions(continent: String): ArrayList<Question> {
        return countriesByContinent[continent]?.let { countriesByContinent ->
            val countries = countriesByContinent.filter { flagIdByCountry.contains(it) }

            val questionList = ArrayList<Question>()
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
                flagIdByCountry[correctAnswer]?.let {

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

            return questionList
        } ?: arrayListOf()
    }
}

