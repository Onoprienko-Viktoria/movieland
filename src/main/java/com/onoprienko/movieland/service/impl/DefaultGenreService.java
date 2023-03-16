package com.onoprienko.movieland.service.impl;

import com.onoprienko.movieland.entity.Genre;
import com.onoprienko.movieland.repository.cache.GenreCache;
import com.onoprienko.movieland.service.GenreService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DefaultGenreService implements GenreService {
    private final GenreCache genreCache;

    @Override
    public List<Genre> findAll() {
        log.info("Get all genres from cache {}", genreCache.getGenreCache());
        return genreCache.getGenreCache();
    }
}
