package com.onoprienko.movieland.repository.dao;

import com.onoprienko.movieland.entity.Genre;

import java.util.List;

public interface GenreDao {
    List<Genre> findAll();

}
