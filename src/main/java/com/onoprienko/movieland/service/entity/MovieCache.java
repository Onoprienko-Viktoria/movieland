package com.onoprienko.movieland.service.entity;

import com.onoprienko.movieland.entity.Movie;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Getter
@Component
public class MovieCache {
    private HashMap<Integer, Movie> movieCache;


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
