package com.onoprienko.movieland.repository.cache;

import com.onoprienko.movieland.entity.Movie;
import com.onoprienko.movieland.repository.dao.MovieDao;
import com.onoprienko.movieland.service.entity.PageRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

import static com.onoprienko.movieland.service.utils.RandomGenerationUtils.getRandomPage;

@Getter
@Component
@RequiredArgsConstructor
@Slf4j
public class MovieCache {
    private HashMap<Integer, Movie> movieCache;
    private final MovieDao movieDao;
    @Value("${movie.cache.capacity}")
    private int movieCacheCapacity;


    @Scheduled(fixedRateString = "${timing.updateData.movie}")
    public void updateMovieCache() {
        int randomPage = getRandomPage(movieDao.getCount(), movieCacheCapacity);
        List<Movie> movies = movieDao.findAll(PageRequest.builder()
                .page(randomPage)
                .capacity(movieCacheCapacity)
                .build());
        fillCacheWithMovies(movies);
        log.info("Update movie cache {}", movieCache);
    }


    public void fillCacheWithMovies(List<Movie> movies) {
        HashMap<Integer, Movie> cache = new HashMap<>();
        int movieIndex = 0;
        for (Movie movie : movies) {
            cache.put(movieIndex, movie);
            movieIndex++;
        }
        movieCache = cache;
    }
}
