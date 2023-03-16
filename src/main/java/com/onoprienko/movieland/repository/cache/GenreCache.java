package com.onoprienko.movieland.repository.cache;

import com.onoprienko.movieland.entity.Genre;
import com.onoprienko.movieland.repository.dao.GenreDao;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Component
@RequiredArgsConstructor
@Slf4j
public class GenreCache {
    private List<Genre> genreCache;
    private final GenreDao genreDao;

    @Scheduled(fixedRateString = "${timing.updateData.genre}")
    public void updateGenreCache() {
        genreCache = genreDao.findAll();
        log.info("Update genre cache {}", genreCache);
    }
}
