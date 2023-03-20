package com.example.quiz_api.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quiz_api.ApiService
import com.example.quiz_api.OnItemClickListener
import com.example.quiz_api.R
import com.example.quiz_api.adapter.CategoryAdapter
import com.example.quiz_api.databinding.ActivityDetailBinding
import com.example.quiz_api.model.Category
import com.example.quiz_api.model.MetadataQuiz
import com.example.quiz_api.model.Question
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Collections

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var receivedMetadata: MetadataQuiz
    private lateinit var byCategory: Map<String, Int>
    private var nameCategoryList = arrayListOf<String>()
    private var sizeCategoryList = arrayListOf<Int>()
    private var imageCategoryList = arrayListOf<Int>()
    private lateinit var categoryList: ArrayList<Category>
    private var bgColorList = arrayListOf<Int>()
    private var playFlag = 0
    private lateinit var listTenRandomQuestion: ArrayList<Question>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        receiveResponse()
        setImageCategoryList()
        setBackgroundColorList()

        callRandomQuestion()

        binding.btnPlayNow.setOnClickListener {
            playFlag = 1
            val bundle = Bundle()
            bundle.putInt("type_play", playFlag)
            val intent = Intent(this, PlayActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }

    }

    private fun callRandomQuestion() {
        ApiService.apiService.getRandomTypeQuestion().enqueue(object : Callback<List<Question>>{
            override fun onResponse(
                call: Call<List<Question>>,
                response: Response<List<Question>>
            ) {
                listTenRandomQuestion = response.body() as ArrayList<Question>
//                val intent = Intent(this@DetailActivity, PlayActivity::class.java)
//                val bundle = Bundle()
                Log.i("random_10_question", listTenRandomQuestion.toString())
            }

            override fun onFailure(call: Call<List<Question>>, t: Throwable) {
                Toast.makeText(this@DetailActivity, "ERROR", Toast.LENGTH_SHORT).show()
            }

        })
}

    private fun setBackgroundColorList() {
        bgColorList = arrayListOf(
            R.drawable.custom_bg_cardview_category_black,
            R.drawable.custom_bg_cardview_category_blue,
            R.drawable.custom_bg_cardview_category_green,
            R.drawable.custom_bg_cardview_category_orange,
            R.drawable.custom_bg_cardview_category_brown,
            R.drawable.custom_bg_cardview_category_red,
            R.drawable.custom_bg_cardview_category_ocean,
            R.drawable.custom_bg_cardview_category_purple,
            R.drawable.custom_bg_cardview_category_pink,
            R.drawable.custom_bg_cardview_category_yellow
        )
    }


    private fun setImageCategoryList() {
        imageCategoryList = arrayListOf(
            R.drawable.art_and_literature,
            R.drawable.films_and_tv,
            R.drawable.foods_and_drinks,
            R.drawable.general_knowledge,
            R.drawable.geography,
            R.drawable.history,
            R.drawable.music,
            R.drawable.science,
            R.drawable.social_and_culture,
            R.drawable.sport
        )
    }


    private fun receiveResponse() {
        ApiService.apiService.getCategory().enqueue(object : Callback<MetadataQuiz> {
            override fun onResponse(call: Call<MetadataQuiz>, response: Response<MetadataQuiz>) {
                receivedMetadata = response.body()!!
                byCategory = receivedMetadata.byCategory
                nameCategoryList = byCategory.keys.toList() as ArrayList<String>
                sizeCategoryList = byCategory.values.toList() as ArrayList<Int>
                nameCategoryList.sortBy { it }
                categoryList = ArrayList()
                for (i in 0 until nameCategoryList.size) {
                    categoryList.add(
                        Category(
                            nameCategoryList[i],
                            sizeCategoryList[i],
                            imageCategoryList[i],
                            bgColorList[i]
                        )
                    )
                }

                binding.rcvCategory.adapter = CategoryAdapter(this@DetailActivity, categoryList,
                    object : OnItemClickListener {
                        override fun onItemClick(data: Any?) {
                            playFlag = 2
                            //Toast.makeText(this@DetailActivity, data.toString(), Toast.LENGTH_SHORT).show()
                            val bundle = Bundle()
                            val intent = Intent(this@DetailActivity, PlayActivity::class.java)
                            bundle.putInt("type_play", playFlag)
                            bundle.putString("type_name", data as String?)
                            intent.putExtras(bundle)
                            startActivity(intent)
                        }
                    })

                //Log.i("size", categoryList.size.toString())
                //Toast.makeText(this@DetailActivity, "SUCCESS", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<MetadataQuiz>, t: Throwable) {
                Toast.makeText(this@DetailActivity, "ERROR", Toast.LENGTH_SHORT).show()
            }

        })

    }

}