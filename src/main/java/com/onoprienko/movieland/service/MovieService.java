package com.onoprienko.movieland.service;

import com.onoprienko.movieland.entity.Movie;
import com.onoprienko.movieland.service.entity.SortEnum;

import java.util.List;

public interface MovieService {
    List<Movie> findAll(int page, SortEnum ratingSort, SortEnum priceSort);

    List<Movie> findAllByGenre(int genreId, int page);

    List<Movie> findRandom();
}
