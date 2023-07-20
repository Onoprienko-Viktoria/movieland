package com.onoprienko.movieland.service;

import com.onoprienko.movieland.dto.MovieWithDetailsDto;
import com.onoprienko.movieland.entity.Country;
import com.onoprienko.movieland.entity.Genre;
import com.onoprienko.movieland.entity.Movie;

import java.util.List;

public interface EnrichmentService {
    MovieWithDetailsDto enrichMovie(Movie movie);

    List<Genre> enrichGenres(List<Long> genresIds);

    List<Country> enrichCountries(List<Integer> countriesIds);
}
