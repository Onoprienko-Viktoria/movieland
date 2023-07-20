package com.onoprienko.movieland.service;

import com.onoprienko.movieland.dto.GenreDto;
import com.onoprienko.movieland.entity.Genre;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();

    List<Genre> findAllByIdIn(List<Long> genres);

    List<GenreDto> findByMovieId(Long movieId);
}
