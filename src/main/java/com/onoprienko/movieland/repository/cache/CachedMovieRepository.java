package com.onoprienko.movieland.repository.cache;

import com.onoprienko.movieland.dto.MovieWithDetailsDto;
import lombok.AllArgsConstructor;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
public class CachedMovieRepository {

    private final Map<Long, MovieWithDetailsDto> cache;
    private final ReferenceQueue<MovieWithDetailsDto> referenceQueue;
    private final int maxCacheSize;

    public Optional<MovieWithDetailsDto> getById(Long movieId) {
        return Optional.ofNullable(cache.get(movieId));
    }


    public void cacheMovie(MovieWithDetailsDto movie) {
        if (cache.size() >= maxCacheSize) {
            clearCache();
        }
        cache.put(movie.getId(), movie);
        WeakReference<MovieWithDetailsDto> movieRef = new WeakReference<>(movie, referenceQueue);
    }


    public void clearCache() {
        while (referenceQueue.poll() != null) {
            cache.values().removeIf(Objects::isNull);
        }
        if (cache.size() >= maxCacheSize) {
            cache.clear();
        }
    }
}