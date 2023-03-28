package com.onoprienko.movieland.service;

import com.onoprienko.movieland.common.MoviesRequest;
import com.onoprienko.movieland.dto.MovieDto;

import java.util.List;

public interface MovieService {
    List<MovieDto> findAll(MoviesRequest request);

    List<MovieDto> findByGenre(MoviesRequest request);

    List<MovieDto> findRandom();
}
