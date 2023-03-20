package com.example.quiz_api.model

data class MetadataQuiz(
    val byCategory: Map<String, Int>,
    val byState: Map<String, Int>,
    val byDifficulty: Map<String, Int>,
    val lastCreated: String,
    val lastReviewed: String
)
