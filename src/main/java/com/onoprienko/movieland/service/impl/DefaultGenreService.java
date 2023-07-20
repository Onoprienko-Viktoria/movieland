package com.onoprienko.movieland.service.impl;

import com.onoprienko.movieland.dto.GenreDto;
import com.onoprienko.movieland.entity.Genre;
import com.onoprienko.movieland.repository.GenreRepository;
import com.onoprienko.movieland.service.GenreService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DefaultGenreService implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public List<GenreDto> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public List<Genre> findAllByIdIn(List<Long> genres) {
        return genreRepository.findByIdIn(genres);
    }

    @Override
    public List<GenreDto> findByMovieId(Long movieId) {
        return genreRepository.findByMovieId(movieId);
    }
}