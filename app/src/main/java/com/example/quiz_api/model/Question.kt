package com.example.quiz_api.model

import java.io.Serializable

data class Question(
    val category: String,
    val id: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>,
    val question: String,
    val tags: ArrayList<String>,
    val type: String,
    val difficulty: String,
    val regions: ArrayList<String>,
    val isNiche: Boolean
) : Serializable