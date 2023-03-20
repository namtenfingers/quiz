package com.example.quiz_api.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.quiz_api.ApiService
import com.example.quiz_api.R
import com.example.quiz_api.databinding.ActivityPlayBinding
import com.example.quiz_api.dialog.BotDialog
import com.example.quiz_api.dialog.CallDialog
import com.example.quiz_api.dialog.LoseDialog
import com.example.quiz_api.dialog.WinDialog
import com.example.quiz_api.model.Question
import com.google.android.material.card.MaterialCardView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class PlayActivity : AppCompatActivity() {

    private var listQuestion = arrayListOf<Question>()
    private lateinit var typeName: String
    private lateinit var binding: ActivityPlayBinding
    private var pos = 0
    private var mapImageOfType = mapOf<String, Int>()
    private lateinit var handlerAnswered: Handler
    private lateinit var handlerNextQuiz: Handler
    private lateinit var handlerEndGame: Handler
    private lateinit var handlerWinGame: Handler
    private lateinit var countDownTimer: CountDownTimer
    private var isDown: Boolean = true
    private var isCall:Boolean = false // chua click
    private var isFiftyFifty: Boolean = false
    private var isBot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        val playFlag = bundle!!.getInt("type_play")
        typeName = bundle.getString("type_name").toString()

        handlerNextQuiz = Handler(Looper.myLooper()!!)
        handlerAnswered = Handler(Looper.myLooper()!!)
        handlerEndGame = Handler(Looper.myLooper()!!)
        handlerWinGame = Handler(Looper.myLooper()!!)

        when (playFlag) {
            1 -> callRandomQuestion()
            2 -> callTypeQuestion()
        }

        setDataMap()
//        binding.answerA.setOnClickListener {
//            setAnswerClicked(binding.lnAnsA, binding.cvAnsA)
//        }

    }

    private fun setDataMap() {
        mapImageOfType = mapOf(
            "Arts & Literature" to R.drawable.art_and_literature,
            "Film & TV" to R.drawable.films_and_tv,
            "Food & Drink" to R.drawable.foods_and_drinks,
            "General Knowledge" to R.drawable.general_knowledge,
            "Geography" to R.drawable.geography,
            "History" to R.drawable.history,
            "Music" to R.drawable.music,
            "Science" to R.drawable.science,
            "Society & Culture" to R.drawable.social_and_culture,
            "Sport & Leisure" to R.drawable.sport
        )
    }

    private fun callTypeQuestion() {
        ApiService.apiService.getTypeQuestion(typeName).enqueue(object : Callback<List<Question>> {
            override fun onResponse(
                call: Call<List<Question>>,
                response: Response<List<Question>>
            ) {
                listQuestion = response.body() as ArrayList<Question>
                playGame(listQuestion)
                Log.i("list_question", listQuestion.toString())
            }

            override fun onFailure(call: Call<List<Question>>, t: Throwable) {
                Toast.makeText(this@PlayActivity, "ERROR", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun callRandomQuestion() {
        ApiService.apiService.getRandomTypeQuestion().enqueue(object : Callback<List<Question>> {
            override fun onResponse(
                call: Call<List<Question>>,
                response: Response<List<Question>>
            ) {
                listQuestion = response.body() as ArrayList<Question>
                //playGame(listQuestion)
                Log.i("list_question", listQuestion.toString())
            }

            override fun onFailure(call: Call<List<Question>>, t: Throwable) {
                Toast.makeText(this@PlayActivity, "ERROR", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setColorDifficulty(diff: String) {
        when (diff) {
            "hard" -> binding.tvDifficulty.setTextColor(ContextCompat.getColor(this, R.color.red))
            "medium" -> binding.tvDifficulty.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.orange
                )
            )
            "easy" -> binding.tvDifficulty.setTextColor(ContextCompat.getColor(this, R.color.ocean))
        }
    }

    private fun setAnswer(ans1: String, ans2: String, ans3: String, ans4: String) {
        val listAns = arrayListOf(ans1, ans2, ans3, ans4)
        listAns.shuffle()
        binding.answerA.text = listAns[0]
        binding.answerB.text = listAns[1]
        binding.answerC.text = listAns[2]
        binding.answerD.text = listAns[3]
    }

    @SuppressLint("SetTextI18n")
    private fun metaData(question: Question) {
        var listIncorrectAns = question.incorrectAnswers
        binding.tvCurrentType.text = question.category
        binding.tvDifficulty.text = question.difficulty
        if (question.difficulty == "null") {
            binding.tvDifficulty.text = ""
        }
        setColorDifficulty(binding.tvDifficulty.text.toString())
        Log.i("current_type", question.type)
        binding.tvCurrentScore.text = "${pos+1}/10"
        val ivPath = mapImageOfType[binding.tvCurrentType.text]
        binding.tvQuestionContent.text = question.question
        binding.ivCurrentType.setImageResource(ivPath!!)
        setAnswer(
            question.correctAnswer,
            listIncorrectAns[0],
            listIncorrectAns[1],
            listIncorrectAns[2]
        )
    }

    private fun notify(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun supportSelection(){
        binding.spBot.setOnClickListener {
            supportBotClicked()
        }
        binding.spCall.setOnClickListener {
            supportCallClicked()
        }
        binding.spFiftyFifty.setOnClickListener {
            supportFiftyFiftyClicked()
        }
    }

    private fun fiftyFiftySupport(
        tvTrueAnswer: TextView,
        tv1: TextView,
        tv2: TextView,
        tv3: TextView
    ) {
        val rd = Random.nextInt(1, 4)
        if (tvTrueAnswer.text.toString() == listQuestion[pos].correctAnswer) {
            when (rd) {
                1 -> {
                    tv1.isEnabled = false
                    tv2.isEnabled = false
                    tv1.text = ""
                    tv2.text = ""
                }
                2 -> {
                    tv2.isEnabled = false
                    tv3.isEnabled = false
                    tv2.text = ""
                    tv3.text = ""
                }
                3 -> {
                    tv1.isEnabled = false
                    tv3.isEnabled = false
                    tv1.text = ""
                    tv3.text = ""
                }
            }
        }
    }

    private fun supportFiftyFiftyClicked() {
        isFiftyFifty = true
        binding.spFiftyFifty.isEnabled = false
        fiftyFiftySupport(binding.answerA, binding.answerB, binding.answerC, binding.answerD)
        fiftyFiftySupport(binding.answerB, binding.answerA, binding.answerC, binding.answerD)
        fiftyFiftySupport(binding.answerC, binding.answerA, binding.answerB, binding.answerD)
        fiftyFiftySupport(binding.answerD, binding.answerA, binding.answerB, binding.answerC)
    }

    private fun supportCallClicked() {
        isCall = true
        binding.spCall.isEnabled = false
        val dialog = CallDialog()
        val bundle = Bundle()
        bundle.putStringArrayList("call_clicked", arrayListOf(binding.answerA.text.toString(), binding.answerB.text.toString(),
        binding.answerC.text.toString(), binding.answerD.text.toString(), listQuestion[pos].correctAnswer))
        dialog.arguments = bundle
        dialog.show(supportFragmentManager, "call_dialog")
    }

    private fun supportBotClicked() {
        isBot = true // clicked
        binding.spBot.isEnabled = false
        val dialog = BotDialog()
        val bundle = Bundle()
        bundle.putStringArrayList("bot_clicked", arrayListOf(binding.answerA.text.toString(), binding.answerB.text.toString(),
            binding.answerC.text.toString(), binding.answerD.text.toString(), listQuestion[pos].correctAnswer))
        dialog.arguments = bundle
        dialog.show(supportFragmentManager, "bot_dialog")

    }

    @SuppressLint("SetTextI18n")
    private fun playGame(listQuestionFunc: ArrayList<Question>) {
        metaData(listQuestionFunc[pos])
        countdown(60000)
        supportSelection()
        chooseAnswer(binding.answerD, binding.answerB, binding.answerC, binding.answerA)
        chooseAnswer(binding.answerA, binding.answerC, binding.answerD, binding.answerB)
        chooseAnswer(binding.answerA, binding.answerB, binding.answerD, binding.answerC)
        chooseAnswer(binding.answerA, binding.answerB, binding.answerC, binding.answerD)
    }

    private fun chooseAnswer(tv1: TextView, tv2: TextView, tv3: TextView, tvClicked: TextView) {
        tvClicked.setOnClickListener {
            when (tvClicked) {
                binding.answerA -> {
                    setColorAnswerClicked(binding.lnAnsA, binding.cvAnsA)
                    stopTimer()
                }
                binding.answerB -> {
                    stopTimer()
                    setColorAnswerClicked(binding.lnAnsB, binding.cvAnsB)
                }
                binding.answerC -> {
                    stopTimer()
                    setColorAnswerClicked(binding.lnAnsC, binding.cvAnsC)
                }
                binding.answerD -> {
                    stopTimer()
                    setColorAnswerClicked(binding.lnAnsD, binding.cvAnsD)
                }
            }

            tv1.isEnabled = false
            tv2.isEnabled = false
            tv3.isEnabled = false
            tvClicked.isEnabled = false

            //Log.i("hallo", listQuestion[pos].correctAnswer)

            handlerAnswered.postDelayed({
                if (tvClicked.text == listQuestion[pos].correctAnswer) {
                    when (tvClicked) {
                        binding.answerA -> {
                            setColorTrueAnswer(binding.lnAnsA, binding.cvAnsA)
                        }
                        binding.answerB -> {
                            setColorTrueAnswer(binding.lnAnsB, binding.cvAnsB)
                        }
                        binding.answerC -> {
                            setColorTrueAnswer(binding.lnAnsC, binding.cvAnsC)
                        }
                        binding.answerD -> {
                            setColorTrueAnswer(binding.lnAnsD, binding.cvAnsD)
                        }
                    }
                    if(pos < 2){
                        moveToNext()
                    } else {
                        winGame()
                    }
                } else {
                    when (tvClicked) {
                        binding.answerA -> {
                            setColorFalseAnswer(binding.lnAnsA, binding.cvAnsA)
                        }
                        binding.answerB -> {
                            setColorFalseAnswer(binding.lnAnsB, binding.cvAnsB)
                        }
                        binding.answerC -> {
                            setColorFalseAnswer(binding.lnAnsC, binding.cvAnsC)
                        }
                        binding.answerD -> {
                            setColorFalseAnswer(binding.lnAnsD, binding.cvAnsD)
                        }
                    }
                    endGame()
                }
            } ,2500)
        }
    }

    private fun moveToNext() {
        handlerNextQuiz.postDelayed({
            resetView()
            pos++
            countdown(60000)
            metaData(listQuestion[pos])
        },1500)
    }

    private fun winGame() {
        handlerWinGame.postDelayed({
            val dialog = WinDialog()
            dialog.show(supportFragmentManager, "win_dialog")
        },1500)
    }

    private fun endGame() {
        handlerEndGame.postDelayed({
            val dialog = LoseDialog()
            dialog.show(supportFragmentManager, "lose_dialog")
        },1500)
    }

    private fun resetView(){
        if(!binding.answerA.isEnabled){
            resetColorAnswerButton(binding.lnAnsA, binding.cvAnsA)
            binding.answerA.isEnabled = true
        }
        if(!binding.answerB.isEnabled){
            resetColorAnswerButton(binding.lnAnsB, binding.cvAnsB)
            binding.answerB.isEnabled = true
        }
        if(!binding.answerC.isEnabled){
            resetColorAnswerButton(binding.lnAnsC, binding.cvAnsC)
            binding.answerC.isEnabled = true
        }
        if(!binding.answerD.isEnabled){
            resetColorAnswerButton(binding.lnAnsD, binding.cvAnsD)
            binding.answerD.isEnabled = true
        }
    }


    private fun resetColorAnswerButton(colorBg: LinearLayout, colorStroke: MaterialCardView){
        colorBg.background = ContextCompat.getDrawable(this, R.color.ocean_70)
        colorStroke.strokeColor = ContextCompat.getColor(this, R.color.ocean)
    }

    private fun setColorAnswerClicked(colorBg: LinearLayout, colorStroke: MaterialCardView) {
        colorBg.background = ContextCompat.getDrawable(this, R.color.yellow_70)
        colorStroke.strokeColor = ContextCompat.getColor(this, R.color.yellow)
    }

    private fun setColorTrueAnswer(colorBg: LinearLayout, colorStroke: MaterialCardView) {
        colorBg.background = ContextCompat.getDrawable(this, R.color.green_70)
        colorStroke.strokeColor = ContextCompat.getColor(this, R.color.green)
    }

    private fun setColorFalseAnswer(colorBg: LinearLayout, colorStroke: MaterialCardView) {
        colorBg.background = ContextCompat.getDrawable(this, R.color.red_30)
        colorStroke.strokeColor = ContextCompat.getColor(this, R.color.red)
    }

    private fun countdown(time: Long){
        if(isDown) {
            countDownTimer = object : CountDownTimer(time, 1000) {
                override fun onTick(p0: Long) {
                    isDown = true
                    binding.tvTime.text = (p0 / 1000).toString()
                }

                override fun onFinish() {
                    endGame()
                }
            }.start()
        } else {
            isDown = false
        }
    }

    private fun stopTimer(){
        countDownTimer.cancel()
    }

}