package com.ryanrvldo.movieslibrary.core.data.network.mapper

import com.ryanrvldo.movieslibrary.core.data.network.response.ReviewResponse
import com.ryanrvldo.movieslibrary.core.domain.model.Review

fun ReviewResponse.mapToDomain() = Review(
    id = id,
    authorName = author,
    authorAvatarPath = authorDetails.avatarPath,
    authorRating = authorDetails.rating,
    review = content,
    createdAt = createdAt
)
