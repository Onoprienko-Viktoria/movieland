package com.onoprienko.movieland.repository.dao;

import com.onoprienko.movieland.entity.Movie;
import com.onoprienko.movieland.service.entity.PageRequest;
import com.onoprienko.movieland.service.entity.SortEnum;

import java.util.List;

public interface MovieDao {
    List<Movie> findAll(PageRequest pageRequest);

    List<Movie> findAllByRating(PageRequest pageRequest);

    List<Movie> findAllByPrice(PageRequest pageRequest, SortEnum sort);

    List<Movie> findAllByGenre(Integer genreId, PageRequest pageRequest);

    Integer getCount();
}
