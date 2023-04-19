package com.onoprienko.movieland.service.impl;

import com.onoprienko.movieland.common.CurrencyCode;
import com.onoprienko.movieland.common.SortDirection;
import com.onoprienko.movieland.common.SortType;
import com.onoprienko.movieland.common.request.AddMovieRequest;
import com.onoprienko.movieland.common.request.EditMovieRequest;
import com.onoprienko.movieland.common.request.MovieByIdRequest;
import com.onoprienko.movieland.common.request.MoviesRequest;
import com.onoprienko.movieland.dto.*;
import com.onoprienko.movieland.entity.Country;
import com.onoprienko.movieland.entity.Genre;
import com.onoprienko.movieland.entity.Movie;
import com.onoprienko.movieland.mapper.MovieMapper;
import com.onoprienko.movieland.repository.cache.CachedMovieRepository;
import com.onoprienko.movieland.repository.jpa.JpaMovieRepository;
import com.onoprienko.movieland.service.CountryService;
import com.onoprienko.movieland.service.GenreService;
import com.onoprienko.movieland.service.MovieService;
import com.onoprienko.movieland.service.ReviewService;
import com.onoprienko.movieland.service.utils.CurrencyConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
public class DefaultMovieService implements MovieService {
    private final JpaMovieRepository jpaMovieRepository;
    private final GenreService genreService;
    private final CountryService countryService;
    private final ReviewService reviewService;
    private final CurrencyConverter currencyConverter;
    private final CachedMovieRepository cachedMovieRepository;
    private final MovieMapper movieMapper;

    @Value("${movie.enrichment.timeout.seconds}")
    private int timeoutSeconds;
    private int defaultPageSize;

    @Override
    public List<MovieDto> findAll(MoviesRequest request) {
        Pageable pageable = getPageableForMovie(request);
        return movieMapper.mapToMovieDtoList(jpaMovieRepository.findAll(pageable).getContent());
    }

    @Override
    public List<MovieDto> findByGenre(MoviesRequest request) {
        Pageable pageable = getPageableForMovie(request);
        return movieMapper.mapToMovieDtoList(jpaMovieRepository.findByGenreId(request.getGenreId(), pageable));
    }

    @Override
    public List<MovieDto> findRandom() {
        return movieMapper.mapToMovieDtoList(jpaMovieRepository.findRandomMovies());
    }

    @Override
    public MovieWithDetailsDto findById(MovieByIdRequest request) {
        Optional<MovieWithDetailsDto> movieOptional = cachedMovieRepository.getById(request.getMovieId());
        MovieWithDetailsDto resultMovie;

        if (movieOptional.isEmpty()) {
            resultMovie = enrichMovie(request.getMovieId());
            cachedMovieRepository.cacheMovie(resultMovie);
        } else {
            resultMovie = movieOptional.get();
        }

        if (request.getCurrencyCode() != null) {
            Double price = currencyConverter.convertPrice(resultMovie.getPrice(), request.getCurrencyCode());
            resultMovie.setCurrencyCode(request.getCurrencyCode());
            resultMovie.setPrice(price);
        }
        resultMovie.setCurrencyCode(CurrencyCode.UAH);
        return resultMovie;
    }

    @Override
    public void add(AddMovieRequest request) {
        Movie movie = movieMapper.mapToMovie(request);
        List<Genre> genres = genreService.findAllByIdIn(request.getGenres());
        List<Country> countries = countryService.findAllByIdIn(request.getCountries());
        movie.setGenres(genres);
        movie.setCountries(countries);
        jpaMovieRepository.save(movie);
    }

    @Override
    public void edit(EditMovieRequest request) {
        Optional<Movie> optionalMovie = jpaMovieRepository.findById(request.getMovieId());
        if (optionalMovie.isEmpty()) {
            throw new IllegalArgumentException("Movie with id " + request.getMovieId() + " not found");
        }
        Movie movie = optionalMovie.get();
        setMovieFields(request, movie);
        jpaMovieRepository.save(movie);
    }

    private void setMovieFields(EditMovieRequest request, Movie movie) {
        if (request.getNameNative() != null) {
            movie.setNameNative(request.getNameNative());
        }
        if (request.getNameRussian() != null) {
            movie.setNameRussian(request.getNameRussian());
        }
        if (request.getPicturePath() != null) {
            movie.setPicturePath(request.getPicturePath());
        }
        if (request.getGenres() != null) {
            List<Genre> genres = genreService.findAllByIdIn(request.getGenres());
            movie.setGenres(genres);
        }
        if (request.getCountries() != null) {
            List<Country> countries = countryService.findAllByIdIn(request.getCountries());
            movie.setCountries(countries);
        }
    }


    private Pageable getPageableForMovie(MoviesRequest request) {
        if (request.getPriceDirection() != null || request.getRatingDirection() != null) {
            return PageRequest.of(request.getPage(),
                    defaultPageSize,
                    getSortForMovie(request));
        }
        return PageRequest.of(request.getPage(), defaultPageSize);
    }

    private Sort getSortForMovie(MoviesRequest request) {
        Sort sort = null;
        if (Objects.equals(request.getRatingDirection(), SortDirection.ASC)) {
            sort = Sort.by(SortType.RATING.name().toLowerCase()).ascending();
        } else if (Objects.equals(request.getPriceDirection(), SortDirection.ASC)) {
            sort = Sort.by(SortType.PRICE.name().toLowerCase()).ascending();
        } else if (Objects.equals(request.getPriceDirection(), SortDirection.DESC)) {
            sort = Sort.by(SortType.PRICE.name().toLowerCase()).descending();
        }
        return sort;
    }

    MovieWithDetailsDto enrichMovie(Long movieId) {
        CompletableFuture<Movie> movieFuture = CompletableFuture.supplyAsync(jpaMovieRepository.findById(movieId)::get);
        CompletableFuture<List<GenreDto>> genresFuture = CompletableFuture.supplyAsync(() -> genreService.findByMovieId(movieId));
        CompletableFuture<List<CountryDto>> countriesFuture = CompletableFuture.supplyAsync(() -> countryService.findByMovieId(movieId));
        CompletableFuture<List<ReviewDto>> reviewsFuture = CompletableFuture.supplyAsync(() -> reviewService.findByMovieId(movieId));

        try {
            CompletableFuture.allOf(movieFuture, genresFuture, countriesFuture, reviewsFuture)
                    .get(timeoutSeconds, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            cancelFuture(genresFuture);
            cancelFuture(countriesFuture);
            cancelFuture(reviewsFuture);
        }

        MovieWithDetailsDto enrichedMovie = movieMapper.mapToMovieWithDetailsDto(movieFuture.join());
        enrichedMovie.setGenres(genresFuture.getNow(new ArrayList<>()));
        enrichedMovie.setCountries(countriesFuture.getNow(new ArrayList<>()));
        enrichedMovie.setReviews(reviewsFuture.getNow(new ArrayList<>()));
        return enrichedMovie;
    }

    void cancelFuture(CompletableFuture<?> future) {
        if (!future.isDone()) {
            future.cancel(true);
        }
    }

    @Value("${movie.page.size}")
    public void setDefaultPageSize(int defaultPageSize) {
        this.defaultPageSize = defaultPageSize;
    }
}
