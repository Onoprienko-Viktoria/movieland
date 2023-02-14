package com.onoprienko.movieland.service.entity;

import com.onoprienko.movieland.entity.Movie;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieCacheTest {

    Movie testMovieOne = Movie.builder()
            .id(1L)
            .price(55.8)
            .rating(7.3)
            .nameRussian("Тест один")
            .nameNative("Test one")
            .yearOfRelease(1900)
            .picturePath("path")
            .build();
    Movie testMovieTwo = Movie.builder().id(2L)
            .price(111.1)
            .rating(9.8)
            .nameRussian("Тест два")
            .nameNative("Test two")
            .yearOfRelease(2000)
            .picturePath("path")
            .build();

    @Test
    void fillCacheWithMovies() {
        MovieCache movieCache = new MovieCache();
        movieCache.fillCacheWithMovies(List.of(testMovieOne, testMovieTwo));

        HashMap<Integer, Movie> cache = movieCache.getMovieCache();

        assertNotNull(cache);

        Movie movieOne = cache.get(0);
        Movie movieTwo = cache.get(1);


        assertEquals(movieOne.getId(), 1);
        assertEquals(movieOne.getPrice(), 55.8);
        assertEquals(movieOne.getRating(), 7.3);
        assertEquals(movieOne.getNameNative(), "Test one");
        assertEquals(movieOne.getNameRussian(), "Тест один");
        assertEquals(movieOne.getYearOfRelease(), 1900);
        assertEquals(movieOne.getPicturePath(), "path");

        assertEquals(movieTwo.getId(), 2);
        assertEquals(movieTwo.getPrice(), 111.1);
        assertEquals(movieTwo.getRating(), 9.8);
        assertEquals(movieTwo.getNameNative(), "Test two");
        assertEquals(movieTwo.getNameRussian(), "Тест два");
        assertEquals(movieTwo.getYearOfRelease(), 2000);
        assertEquals(movieTwo.getPicturePath(), "path");
    }


    @Test
    void fillCacheWithMoviesVoidList() {
        MovieCache movieCache = new MovieCache();
        movieCache.fillCacheWithMovies(new ArrayList<>());

        HashMap<Integer, Movie> cache = movieCache.getMovieCache();

        assertNotNull(cache);
        assertTrue(cache.isEmpty());
    }
}