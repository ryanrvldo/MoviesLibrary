package com.ryanrvldo.movieslibrary.core.data.network.mapper

import com.ryanrvldo.movieslibrary.core.data.network.response.GenreResponse
import com.ryanrvldo.movieslibrary.core.domain.model.Genre

fun GenreResponse.mapToDomain() = Genre(id, name)
