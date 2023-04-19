package com.onoprienko.movieland.service;

import com.onoprienko.movieland.common.request.AddMovieRequest;
import com.onoprienko.movieland.common.request.EditMovieRequest;
import com.onoprienko.movieland.common.request.MovieByIdRequest;
import com.onoprienko.movieland.common.request.MoviesRequest;
import com.onoprienko.movieland.dto.MovieDto;
import com.onoprienko.movieland.dto.MovieWithDetailsDto;

import java.util.List;

public interface MovieService {
    List<MovieDto> findAll(MoviesRequest request);

    List<MovieDto> findByGenre(MoviesRequest request);

    List<MovieDto> findRandom();

    MovieWithDetailsDto findById(MovieByIdRequest request);

    void add(AddMovieRequest request);

    void edit(EditMovieRequest request);
}
