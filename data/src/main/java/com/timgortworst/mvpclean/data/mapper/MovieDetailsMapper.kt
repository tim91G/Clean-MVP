package com.timgortworst.mvpclean.data.mapper

import com.timgortworst.mvpclean.data.model.NetworkMovieDetails
import com.timgortworst.mvpclean.domain.model.movie.MovieDetails

fun NetworkMovieDetails.asDomainModel() = with(this) {
    MovieDetails(
        adult,
        backdropPath,
        budget,
        genres.map { it.asDomainModel() },
        homepage,
        id,
        imdbId,
        originalLanguage,
        originalTitle,
        overview,
        popularity,
        posterPath,
        productionCompanies.map { it.asDomainModel() },
        productionCountries.map { it.asDomainModel() },
        releaseDate,
        revenue,
        runtime,
        spokenLanguages.map { it.asDomainModel() },
        status,
        tagline,
        title,
        video,
        voteAverage,
        voteCount
    )
}

fun NetworkMovieDetails.Genre.asDomainModel() = with(this) {
    MovieDetails.Genre(
        id,
        name
    )
}

fun NetworkMovieDetails.ProductionCompany.asDomainModel() = with(this) {
    MovieDetails.ProductionCompany(
        id,
        logoPath.orEmpty(),
        name,
        originCountry
    )
}

fun NetworkMovieDetails.ProductionCountry.asDomainModel() = with(this) {
    MovieDetails.ProductionCountry(
        iso31661,
        name
    )
}

fun NetworkMovieDetails.SpokenLanguage.asDomainModel() = with(this) {
    MovieDetails.SpokenLanguage(
        iso6391,
        name
    )
}
