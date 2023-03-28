package com.onoprienko.movieland.repository;

import com.onoprienko.movieland.entity.Genre;

import java.util.List;

public interface GenreRepository {
    List<Genre> findAll();
}
