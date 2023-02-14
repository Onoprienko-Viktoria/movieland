package com.onoprienko.movieland.service.impl;

import com.onoprienko.movieland.entity.Genre;
import com.onoprienko.movieland.repository.dao.GenreDao;
import com.onoprienko.movieland.service.GenreService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DefaultGenreService implements GenreService {

    private List<Genre> genreCache;
    private final GenreDao genreDao;

    @Scheduled(fixedRateString = "${timing.updateData.genre}")
    public void updateGenreCache() {
        genreCache = genreDao.findAll();
        log.info("Update genre cache {}", genreCache);
    }


    @Override
    public List<Genre> findAll() {
        log.info("Get all genres from cache {}", genreCache);
        return genreCache;
    }
}
