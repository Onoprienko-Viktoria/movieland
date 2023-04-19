package com.onoprienko.movieland.repository;

import com.onoprienko.movieland.entity.Genre;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository {
    List<Genre> findAll();

    List<Genre> findByIdIn(List<Long> ids);

    List<Genre> findByMovieId(Long id);
}
