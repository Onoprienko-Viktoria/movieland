package com.onoprienko.movieland.service.impl;

import com.onoprienko.movieland.dto.CountryDto;
import com.onoprienko.movieland.dto.GenreDto;
import com.onoprienko.movieland.dto.MovieWithDetailsDto;
import com.onoprienko.movieland.dto.ReviewDto;
import com.onoprienko.movieland.entity.Country;
import com.onoprienko.movieland.entity.Genre;
import com.onoprienko.movieland.entity.Movie;
import com.onoprienko.movieland.mapper.MovieMapper;
import com.onoprienko.movieland.service.CountryService;
import com.onoprienko.movieland.service.ReviewService;
import com.onoprienko.movieland.service.GenreService;
import com.onoprienko.movieland.service.EnrichmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Primary
@Service
@RequiredArgsConstructor
public class ParallelEnrichmentService implements EnrichmentService {
    private final ReviewService reviewService;
    private final GenreService genreService;
    private final CountryService countryService;
    private final MovieMapper movieMapper;
    @Value("${movie.enrichment.timeout.seconds}")
    private int timeoutSeconds;

    @Override
    public MovieWithDetailsDto enrichMovie(Movie movie) {
        CompletableFuture<List<GenreDto>> genresFuture = CompletableFuture.supplyAsync(() -> genreService.findByMovieId(movie.getId()));
        CompletableFuture<List<CountryDto>> countriesFuture = CompletableFuture.supplyAsync(() -> countryService.findByMovieId(movie.getId()));
        CompletableFuture<List<ReviewDto>> reviewsFuture = CompletableFuture.supplyAsync(() -> reviewService.findByMovieId(movie.getId()));
        try {
            CompletableFuture.allOf(genresFuture, countriesFuture, reviewsFuture)
                    .get(timeoutSeconds, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            cancelFuture(List.of(genresFuture, countriesFuture, reviewsFuture));
        }

        MovieWithDetailsDto enrichedMovie = movieMapper.mapToMovieWithDetailsDto(movie);
        enrichedMovie.setGenres(genresFuture.getNow(new ArrayList<>()));
        enrichedMovie.setCountries(countriesFuture.getNow(new ArrayList<>()));
        enrichedMovie.setReviews(reviewsFuture.getNow(new ArrayList<>()));

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

    void cancelFuture(List<CompletableFuture<?>> futures) {
        for (CompletableFuture<?> future : futures) {
            if (!future.isDone()) {
                future.cancel(true);
            }
        }
    }
}
