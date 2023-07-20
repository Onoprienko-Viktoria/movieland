package com.onoprienko.movieland.repository.cache;

import com.onoprienko.movieland.common.component.Cache;
import com.onoprienko.movieland.dto.CacheGenreDto;
import com.onoprienko.movieland.dto.GenreDto;
import com.onoprienko.movieland.entity.Genre;
import com.onoprienko.movieland.mapper.GenreMapper;
import com.onoprienko.movieland.repository.GenreRepository;
import com.onoprienko.movieland.repository.jpa.JpaGenreRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Cache
@RequiredArgsConstructor
@Slf4j
public class CachedGenreRepository implements GenreRepository {
    private volatile List<CacheGenreDto> genreCache;
    private final JpaGenreRepository jpaGenreRepository;
    private final GenreMapper genreMapper;

    @PostConstruct
    @Scheduled(initialDelayString = "${delay.update.cache.genres}",
            fixedRateString = "${time.update.cache.genres}")
    void updateGenreCache() {
        List<Genre> all = jpaGenreRepository.findAll();
        genreCache = genreMapper.mapToCacheGenreDtoList(all);
        log.info("Update genre cache {}", genreCache);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GenreDto> findAll() {
        return genreMapper.mapToGenreDtoListFromCacheGenreList(genreCache);
    }

    @Override
    public List<Genre> findByIdIn(List<Long> ids) {
        return genreMapper.mapToGenreList(genreCache.stream()
                .filter(genre -> ids.contains(genre.getId()))
                .collect(Collectors.toList()));
    }

    @Override
    public List<GenreDto> findByMovieId(Long id) {
        return genreMapper.mapToGenreDtoList(jpaGenreRepository.findAllByMovieId(id));
    }
}
