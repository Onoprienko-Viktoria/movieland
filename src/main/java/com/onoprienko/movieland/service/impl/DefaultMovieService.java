package com.onoprienko.movieland.service.impl;

import com.onoprienko.movieland.entity.Movie;
import com.onoprienko.movieland.repository.dao.MovieDao;
import com.onoprienko.movieland.service.MovieService;
import com.onoprienko.movieland.service.entity.MovieCache;
import com.onoprienko.movieland.service.entity.PageRequest;
import com.onoprienko.movieland.service.entity.SortEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.onoprienko.movieland.service.utils.RandomGenerationUtils.getRandomIndexes;
import static com.onoprienko.movieland.service.utils.RandomGenerationUtils.getRandomPage;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultMovieService implements MovieService {
    @Value("${page.capacity.default}")
    private int defaultPageCapacity;
    @Value("${page.capacity.random}")
    private int randomPageCapacity;
    @Value("${movie.cache.capacity}")
    private int movieCacheCapacity;
    private final MovieDao movieDao;
    private final MovieCache movieCache;


    @Scheduled(fixedRateString = "${timing.updateData.movie}")
    public void updateMovieCache() {
        int randomPage = getRandomPage(movieDao.getCount(), movieCacheCapacity);
        List<Movie> movies = movieDao.findAll(PageRequest.builder()
                .page(randomPage)
                .capacity(movieCacheCapacity)
                .build());
        movieCache.fillCacheWithMovies(movies);
        log.info("Update movie cache {}", movieCache.getMovieCache());
    }

    @Override
    public List<Movie> findAll(int page, SortEnum ratingSort, SortEnum priceSort) {
        PageRequest pageRequest = PageRequest.builder().page(page).capacity(defaultPageCapacity).build();

        if (ratingSort.equals(SortEnum.DESC)) {
            return movieDao.findAllByRating(pageRequest);
        }

        if (!priceSort.equals(SortEnum.NONE)) {
            return movieDao.findAllByPrice(pageRequest, priceSort);
        }
        return movieDao.findAll(pageRequest);
    }

    @Override
    public List<Movie> findAllByGenre(int genreId, int page) {
        PageRequest request = PageRequest.builder().page(page).capacity(defaultPageCapacity).build();
        return movieDao.findAllByGenre(genreId, request);
    }

    @Override
    public List<Movie> findRandom() {
        List<Movie> movies = new ArrayList<>();
        HashMap<Integer, Movie> cache = movieCache.getMovieCache();
        List<Integer> randomIndexes = getRandomIndexes(cache.size(), randomPageCapacity);
        for (Integer randomIndex : randomIndexes) {
            movies.add(cache.get(randomIndex));
        }
        return movies;
    }

    void setRandomPageCapacity(int randomPageCapacity) {
        this.randomPageCapacity = randomPageCapacity;
    }
}
