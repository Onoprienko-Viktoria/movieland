package com.onoprienko.movieland.service.impl;

import com.onoprienko.movieland.dto.MovieWithDetailsDto;
import com.onoprienko.movieland.entity.Country;
import com.onoprienko.movieland.entity.Genre;
import com.onoprienko.movieland.entity.Movie;
import com.onoprienko.movieland.mapper.MovieMapper;
import com.onoprienko.movieland.service.CountryService;
import com.onoprienko.movieland.service.ReviewService;
import com.onoprienko.movieland.service.GenreService;
import com.onoprienko.movieland.service.EnrichmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DefaultEnrichmentService implements EnrichmentService {
    private final ReviewService reviewService;
    private final GenreService genreService;
    private final CountryService countryService;
    private final MovieMapper movieMapper;

    @Override
    public MovieWithDetailsDto enrichMovie(Movie movie) {
        MovieWithDetailsDto enrichedMovie = movieMapper.mapToMovieWithDetailsDto(movie);
        enrichedMovie.setReviews(reviewService.findByMovieId(movie.getId()));
        enrichedMovie.setGenres(genreService.findByMovieId(movie.getId()));
        enrichedMovie.setCountries(countryService.findByMovieId(movie.getId()));
        return enrichedMovie;
    }

    @Override
    public List<Genre> enrichGenres(List<Long> genresIds) {
        return genreService.findAllByIdIn(genresIds);
    }

    @Override
    public List<Country> enrichCountries(List<Integer> countriesIds) {
        return countryService.findAllByIdIn(countriesIds);
    }
}
