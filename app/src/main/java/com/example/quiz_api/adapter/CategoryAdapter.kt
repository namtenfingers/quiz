package com.example.quiz_api.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.quiz_api.OnItemClickListener
import com.example.quiz_api.R
import com.example.quiz_api.model.Category

class CategoryAdapter(
    private val context: Context,
    private val listCategory: ArrayList<Category>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {


    inner class CategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNameCategory: TextView = itemView.findViewById(R.id.category_name)
        val tvSizeCategory: TextView = itemView.findViewById(R.id.category_size)
        val ivCategory: ImageView = itemView.findViewById(R.id.iv_category)
        val colorCategoryBackground: ConstraintLayout = itemView.findViewById(R.id.bg_color_category)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(listCategory[adapterPosition].name)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        return CategoryHolder(LayoutInflater.from(context).inflate(R.layout.item_category, parent, false))
    }

    override fun getItemCount(): Int = listCategory.size


    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        val currentCategory = listCategory[position]
        holder.tvNameCategory.text = currentCategory.name
        holder.tvSizeCategory.text = currentCategory.size.toString()
        holder.ivCategory.setImageResource(currentCategory.image)
        holder.colorCategoryBackground.setBackgroundResource(currentCategory.imageBG)
    }

}