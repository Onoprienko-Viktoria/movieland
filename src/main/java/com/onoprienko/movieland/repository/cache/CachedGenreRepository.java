package com.onoprienko.movieland.repository.cache;

import com.onoprienko.movieland.entity.Genre;
import com.onoprienko.movieland.repository.GenreRepository;
import com.onoprienko.movieland.repository.jpa.JpaGenreRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CachedGenreRepository implements GenreRepository {
    private List<Genre> genreCache;
    private final JpaGenreRepository jpaGenreRepository;

    @Scheduled(fixedRateString = "${timing.updateData.genre}")
    @PostConstruct
    public void updateGenreCache() {
        genreCache = jpaGenreRepository.findAll();
        log.info("Update genre cache {}", genreCache);
    }

    @Override
    public List<Genre> findAll() {
        return genreCache;
    }

    @Override
    public List<Genre> findByIdIn(List<Long> ids) {
        return jpaGenreRepository.findByIdIn(ids);
    }

    @Override
    public List<Genre> findByMovieId(Long id) {
        return jpaGenreRepository.findByMovieId(id);
    }
}
