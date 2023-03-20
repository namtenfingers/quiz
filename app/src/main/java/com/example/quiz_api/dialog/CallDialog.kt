package com.example.quiz_api.dialog

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.quiz_api.DialogListener
import com.example.quiz_api.OnItemClickListener
import com.example.quiz_api.R
import com.example.quiz_api.adapter.CallAdapter
import com.example.quiz_api.model.Caller
import de.hdodenhof.circleimageview.CircleImageView
import org.w3c.dom.Text

class CallDialog() : DialogFragment(){

    private var listCaller = arrayListOf<Caller>()
    private lateinit var indexCorrectAns: String
    private lateinit var handler: Handler
    private lateinit var tvCorrectAnswer: TextView
    private lateinit var tvNameCaller: TextView
    private lateinit var rcvCaller: RecyclerView
    private lateinit var ivCaller: CircleImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.full_screen_dialog)

        listCaller.add(Caller(R.drawable.kha_banh, "Khá Bảnh"))
        listCaller.add(Caller(R.drawable.sieu_nhan_xanh, "Siêu nhân xanh"))
        listCaller.add(Caller(R.drawable.author, "Author"))
        listCaller.add(Caller(R.drawable.doremon, "Doremon"))
        listCaller.add(Caller(R.drawable.paul, "Paul Octopus"))
        listCaller.add(Caller(R.drawable.super_idol, "Super Idol"))
        handler = Handler(Looper.myLooper()!!)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_call, container, false)
        val bundle = arguments?.getStringArrayList("call_clicked")
        val arr: ArrayList<String> = bundle as ArrayList<String>
        val ansA = arr[0]
        val ansB = arr[1]
        val ansC = arr[2]
        val ansD = arr[3]
        val correctAnswer = arr[4]
        val btnBack = view.findViewById<TextView>(R.id.tv_back)
        ivCaller = view.findViewById(R.id.iv_caller)
        tvNameCaller = view.findViewById(R.id.tv_name_caller)
        tvCorrectAnswer = view.findViewById(R.id.tv_true_answer)
        tvNameCaller.visibility = View.INVISIBLE
        tvCorrectAnswer.visibility = View.INVISIBLE
        when(correctAnswer){
            ansA -> indexCorrectAns = "A"
            ansB -> indexCorrectAns = "B"
            ansC -> indexCorrectAns = "C"
            ansD -> indexCorrectAns = "D"
        }

        btnBack.setOnClickListener {
            dismiss()
        }


        //Log.i("test", arr.toString())
        rcvCaller = view.findViewById(R.id.rcv_caller)
        rcvCaller.adapter =  CallAdapter(listCaller, requireContext(), object : OnItemClickListener{
            @SuppressLint("SetTextI18n")
            override fun onItemClick(data: Any?) {
                handler.postDelayed({
                    rcvCaller.visibility = View.GONE
                    tvNameCaller.visibility = View.VISIBLE
                    tvCorrectAnswer.visibility = View.VISIBLE
                    ivCaller.visibility = View.VISIBLE
                    val caller = data as Caller
                    tvNameCaller.text = "Đáp án của ${caller.name} là: "
                    tvCorrectAnswer.text = indexCorrectAns
                    ivCaller.setImageResource(caller.image)
                }, 1500)
            }
        })
        return view
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        handler.removeCallbacksAndMessages(null)
    }

}