package com.ryanrvldo.movieslibrary.core.domain.model

data class Review(
    val id: String,
    val authorName: String,
    val authorAvatarPath: String?,
    val authorRating: String?,
    val review: String,
    val createdAt: String
)
