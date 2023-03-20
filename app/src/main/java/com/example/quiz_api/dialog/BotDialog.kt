package com.example.quiz_api.dialog

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.quiz_api.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class BotDialog : DialogFragment(){

    private var rdA: Int = 0
    private var rdB: Int = 0
    private var rdC: Int = 0
    private var rdD: Int = 0
    private lateinit var tvA: TextView
    private lateinit var tvB: TextView
    private lateinit var tvC: TextView
    private lateinit var tvD: TextView
    private lateinit var tvPercentA: TextView
    private lateinit var tvPercentB: TextView
    private lateinit var tvPercentC: TextView
    private lateinit var tvPercentD: TextView
    private lateinit var answerA: String
    private lateinit var answerB: String
    private lateinit var answerC: String
    private lateinit var answerD: String
    private lateinit var trueAnswer: String
    private var rd1: Int = 0
    private var rd2: Int = 0
    private lateinit var handler: Handler
    private lateinit var arrData: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.full_screen_dialog)
        handler = Handler(Looper.myLooper()!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_bot, container, false)
        arrData = arguments?.getStringArrayList("bot_clicked") as ArrayList<String>
        answerA = arrData[0]
        answerB = arrData[1]
        answerC = arrData[2]
        answerD = arrData[3]
        trueAnswer = arrData[4]

        tvPercentA = view.findViewById(R.id.tvPercentA)
        tvPercentB = view.findViewById(R.id.tvPercentB)
        tvPercentC = view.findViewById(R.id.tvPercentC)
        tvPercentD = view.findViewById(R.id.tvPercentD)
        tvA = view.findViewById(R.id.tvA)
        tvB = view.findViewById(R.id.tvB)
        tvC = view.findViewById(R.id.tvC)
        tvD = view.findViewById(R.id.tvD)
        tvA.height = 1
        tvB.height = 1
        tvC.height = 1
        tvD.height = 1
        val btnOK: TextView = view.findViewById(R.id.tv_back)
        btnOK.setOnClickListener {
            dismiss()
        }


        rdA = Random.nextInt(0, 101)
        if (rdA == 100) {
            rdB = 0
            rdC = 0
            rdD = 0
        } else {
            rdB = Random.nextInt(0, 101 - rdA)
            if (rdB == 100) {
                rdC = 0
                rdD = 0
            } else {
                rdC = Random.nextInt(0, 101 - rdA - rdB)
                rdD = if (rdC == 0) {
                    0
                } else {
                    100 - rdA - rdB - rdC
                }
            }
        }
        createTwoRandom()

        val rdMax = maxOf(rdA, rdB, rdC, rdD)
        val rdMax2 = maxOf(rd1, rd2)

        if(answerA != "" && answerB != "" && answerC != "" && answerD != ""){
            handler.postDelayed({
                if (trueAnswer == answerA) {
                    if (rdA == rdMax) {
                        setCoroutine(tvA, tvPercentA, rdA)
                        setCoroutine(tvB, tvPercentB, rdB)
                        setCoroutine(tvC, tvPercentC, rdC)
                        setCoroutine(tvD, tvPercentD, rdD)
                    } else if(rdB == rdMax){
                        setCoroutine(tvA, tvPercentA, rdB)
                        setCoroutine(tvB, tvPercentB, rdA)
                        setCoroutine(tvC, tvPercentC, rdC)
                        setCoroutine(tvD, tvPercentD, rdD)
                    } else if(rdC == rdMax) {
                        setCoroutine(tvA, tvPercentA, rdC)
                        setCoroutine(tvB, tvPercentB, rdA)
                        setCoroutine(tvC, tvPercentC, rdB)
                        setCoroutine(tvD, tvPercentD, rdD)
                    } else if(rdD == rdMax) {
                        setCoroutine(tvA, tvPercentA, rdD)
                        setCoroutine(tvB, tvPercentB, rdA)
                        setCoroutine(tvC, tvPercentC, rdB)
                        setCoroutine(tvD, tvPercentD, rdC)
                    }

                } else if(trueAnswer == answerB){
                    if (rdA == rdMax) {
                        setCoroutine(tvA, tvPercentA, rdB)
                        setCoroutine(tvB, tvPercentB, rdA)
                        setCoroutine(tvC, tvPercentC, rdC)
                        setCoroutine(tvD, tvPercentD, rdD)
                    } else if(rdB == rdMax){
                        setCoroutine(tvA, tvPercentA, rdA)
                        setCoroutine(tvB, tvPercentB, rdB)
                        setCoroutine(tvC, tvPercentC, rdC)
                        setCoroutine(tvD, tvPercentD, rdD)
                    } else if(rdC == rdMax) {
                        setCoroutine(tvA, tvPercentA, rdA)
                        setCoroutine(tvB, tvPercentB, rdC)
                        setCoroutine(tvC, tvPercentC, rdB)
                        setCoroutine(tvD, tvPercentD, rdD)
                    } else if(rdD == rdMax) {
                        setCoroutine(tvA, tvPercentA, rdA)
                        setCoroutine(tvB, tvPercentB, rdD)
                        setCoroutine(tvC, tvPercentC, rdB)
                        setCoroutine(tvD, tvPercentD, rdC)
                    }
                } else if(trueAnswer == answerC) {
                    if (rdA == rdMax) {
                        setCoroutine(tvA, tvPercentA, rdB)
                        setCoroutine(tvB, tvPercentB, rdC)
                        setCoroutine(tvC, tvPercentC, rdA)
                        setCoroutine(tvD, tvPercentD, rdD)
                    } else if (rdB == rdMax) {
                        setCoroutine(tvA, tvPercentA, rdA)
                        setCoroutine(tvB, tvPercentB, rdC)
                        setCoroutine(tvC, tvPercentC, rdB)
                        setCoroutine(tvD, tvPercentD, rdD)
                    } else if (rdC == rdMax) {
                        setCoroutine(tvA, tvPercentA, rdA)
                        setCoroutine(tvB, tvPercentB, rdB)
                        setCoroutine(tvC, tvPercentC, rdC)
                        setCoroutine(tvD, tvPercentD, rdD)
                    } else if (rdD == rdMax) {
                        setCoroutine(tvA, tvPercentA, rdA)
                        setCoroutine(tvB, tvPercentB, rdB)
                        setCoroutine(tvC, tvPercentC, rdD)
                        setCoroutine(tvD, tvPercentD, rdC)
                    }
                } else if(trueAnswer == answerD) {
                    if (rdA == rdMax) {
                        setCoroutine(tvA, tvPercentA, rdB)
                        setCoroutine(tvB, tvPercentB, rdC)
                        setCoroutine(tvC, tvPercentC, rdD)
                        setCoroutine(tvD, tvPercentD, rdA)
                    } else if (rdB == rdMax) {
                        setCoroutine(tvA, tvPercentA, rdA)
                        setCoroutine(tvB, tvPercentB, rdC)
                        setCoroutine(tvC, tvPercentC, rdD)
                        setCoroutine(tvD, tvPercentD, rdB)
                    } else if (rdC == rdMax) {
                        setCoroutine(tvA, tvPercentA, rdA)
                        setCoroutine(tvB, tvPercentB, rdB)
                        setCoroutine(tvC, tvPercentC, rdD)
                        setCoroutine(tvD, tvPercentD, rdC)
                    } else if (rdD == rdMax) {
                        setCoroutine(tvA, tvPercentA, rdA)
                        setCoroutine(tvB, tvPercentB, rdB)
                        setCoroutine(tvC, tvPercentC, rdC)
                        setCoroutine(tvD, tvPercentD, rdD)
                    }
                }
            },1500)
        } else {
            if(answerA == "" && answerB == ""){
                if(trueAnswer == answerC){
                    if(rdMax2 == rd1){
                        setCoroutine(tvC, tvPercentC, rd1)
                        setCoroutine(tvD, tvPercentD, rd2)
                    } else if(rdMax2 == rd2){
                        setCoroutine(tvC, tvPercentC, rd2)
                        setCoroutine(tvD, tvPercentD, rd1)
                    }
                } else if(trueAnswer == answerD) {
                    if (rdMax2 == rd1) {
                        setCoroutine(tvC, tvPercentC, rd2)
                        setCoroutine(tvD, tvPercentD, rd1)
                    } else if (rdMax2 == rd2) {
                        setCoroutine(tvC, tvPercentC, rd1)
                        setCoroutine(tvD, tvPercentD, rd2)
                    }
                }
            } else if (answerA == "" && answerC == ""){
                if(trueAnswer == answerB){
                    if(rdMax2 == rd1){
                        setCoroutine(tvB, tvPercentB, rd1)
                        setCoroutine(tvD, tvPercentD, rd2)
                    } else if(rdMax2 == rd2){
                        setCoroutine(tvB, tvPercentB, rd2)
                        setCoroutine(tvD, tvPercentD, rd1)
                    }
                } else if(trueAnswer == answerD){
                    if(rdMax2 == rd1){
                        setCoroutine(tvB, tvPercentB, rd2)
                        setCoroutine(tvD, tvPercentD, rd1)
                    } else if(rdMax2 == rd2){
                        setCoroutine(tvB, tvPercentB, rd1)
                        setCoroutine(tvD, tvPercentD, rd2)
                    }
                }
            } else if (answerA == "" && answerD == "") {
                if (trueAnswer == answerB) {
                    if (rdMax2 == rd1) {
                        setCoroutine(tvB, tvPercentB, rd1)
                        setCoroutine(tvC, tvPercentC, rd2)
                    } else if (rdMax2 == rd2) {
                        setCoroutine(tvB, tvPercentB, rd2)
                        setCoroutine(tvC, tvPercentC, rd1)
                    }
                } else if (trueAnswer == answerC) {
                    if (rdMax2 == rd1) {
                        setCoroutine(tvC, tvPercentC, rd1)
                        setCoroutine(tvB, tvPercentB, rd2)
                    } else if (rdMax2 == rd2) {
                        setCoroutine(tvC, tvPercentC, rd2)
                        setCoroutine(tvB, tvPercentB, rd1)
                    }
                }
            } else if (answerB == "" && answerC == "") {
                if (trueAnswer == answerA) {
                    if (rdMax2 == rd1) {
                        setCoroutine(tvA, tvPercentA, rd1)
                        setCoroutine(tvD, tvPercentD, rd2)
                    } else if (rdMax2 == rd2) {
                        setCoroutine(tvA, tvPercentA, rd2)
                        setCoroutine(tvD, tvPercentD, rd1)
                    }
                } else if (trueAnswer == answerD) {
                    if (rdMax2 == rd1) {
                        setCoroutine(tvD, tvPercentD, rd1)
                        setCoroutine(tvA, tvPercentA, rd2)
                    } else if (rdMax2 == rd2) {
                        setCoroutine(tvD, tvPercentD, rd2)
                        setCoroutine(tvA, tvPercentA, rd1)
                    }
                }
            } else if (answerB == "" && answerD == "") {
                if (trueAnswer == answerA) {
                    if (rdMax2 == rd1) {
                        setCoroutine(tvA, tvPercentA, rd1)
                        setCoroutine(tvC, tvPercentC, rd2)
                    } else if (rdMax2 == rd2) {
                        setCoroutine(tvA, tvPercentA, rd2)
                        setCoroutine(tvC, tvPercentC, rd1)
                    }
                } else if (trueAnswer == answerC) {
                    if (rdMax2 == rd1) {
                        setCoroutine(tvC, tvPercentC, rd1)
                        setCoroutine(tvA, tvPercentA, rd2)
                    } else if (rdMax2 == rd2) {
                        setCoroutine(tvC, tvPercentC, rd2)
                        setCoroutine(tvA, tvPercentA, rd1)
                    }
                }
            } else if (answerC == "" && answerD == "") {
                if (trueAnswer == answerA) {
                    if (rdMax2 == rd1) {
                        setCoroutine(tvA, tvPercentA, rd1)
                        setCoroutine(tvB, tvPercentB, rd2)
                    } else if (rdMax2 == rd2) {
                        setCoroutine(tvA, tvPercentA, rd2)
                        setCoroutine(tvB, tvPercentB, rd1)
                    }
                } else if (trueAnswer == answerB) {
                    if (rdMax2 == rd1) {
                        setCoroutine(tvB, tvPercentB, rd1)
                        setCoroutine(tvA, tvPercentA, rd2)
                    } else if (rdMax2 == rd2) {
                        setCoroutine(tvB, tvPercentB, rd2)
                        setCoroutine(tvA, tvPercentA, rd1)
                    }
                }
            }
        }
        return view
    }


    private fun setCoroutine(tv1: TextView, tv2: TextView, rd: Int){
        GlobalScope.launch(Dispatchers.Main) {
            for(i in 0..rd){
                tv1.height = i * 8
                tv2.text = "$i%"
                delay(1)
            }
        }
    }

    private fun createTwoRandom(){
        rd1 = Random.nextInt(1,101)
        rd2 = 100 - rd1
    }

}