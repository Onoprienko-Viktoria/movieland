package com.onoprienko.movieland.repository.cache;

import com.onoprienko.movieland.entity.Movie;
import com.onoprienko.movieland.repository.dao.MovieDao;
import com.onoprienko.movieland.service.entity.PageRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieCacheTest {
    MovieDao movieDao = Mockito.mock(MovieDao.class);

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

    List<Movie> movies = List.of(testMovieOne, testMovieTwo);

    @Test
    void updateCacheThrowException() {
        MovieCache movieCache = new MovieCache(movieDao);
        Mockito.when(
                movieDao.findAll(
                        Mockito.any(PageRequest.class)
                )
        ).thenThrow(new RuntimeException());

        assertNull(movieCache.getMovieCache());

        assertThrows(RuntimeException.class, movieCache::updateMovieCache);

        Mockito.verify(movieDao, Mockito.times(1))
                .findAll(Mockito.any(PageRequest.class));

    }

    @Test
    void updateVoidMovieCacheWithVoidListWorkCorrect() {
        MovieCache movieCache = new MovieCache(movieDao);
        Mockito.when(
                movieDao.findAll(
                        Mockito.any(PageRequest.class)
                )
        ).thenReturn(new ArrayList<>()).thenReturn(new ArrayList<>());

        assertNull(movieCache.getMovieCache());
        movieCache.updateMovieCache();

        HashMap<Integer, Movie> updatedMovieCache = movieCache.getMovieCache();

        assertNotNull(updatedMovieCache);
        assertTrue(updatedMovieCache.isEmpty());

        movieCache.updateMovieCache();

        HashMap<Integer, Movie> voidMovieCache = movieCache.getMovieCache();

        assertNotNull(voidMovieCache);
        assertTrue(voidMovieCache.isEmpty());

        Mockito.verify(movieDao, Mockito.times(2))
                .findAll(Mockito.any(PageRequest.class));

    }

    @Test
    void updateNotVoidMovieCacheWithVoidListWorkCorrect() {
        MovieCache movieCache = new MovieCache(movieDao);
        Mockito.when(
                movieDao.findAll(
                        Mockito.any(PageRequest.class)
                )
        ).thenReturn(movies).thenReturn(new ArrayList<>());

        assertNull(movieCache.getMovieCache());
        movieCache.updateMovieCache();

        HashMap<Integer, Movie> updatedMovieCache = movieCache.getMovieCache();

        assertNotNull(updatedMovieCache);
        assertEquals(updatedMovieCache.size(), 2);

        movieCache.updateMovieCache();

        HashMap<Integer, Movie> voidMovieCache = movieCache.getMovieCache();

        assertNotNull(voidMovieCache);
        assertTrue(voidMovieCache.isEmpty());

        Mockito.verify(movieDao, Mockito.times(2))
                .findAll(Mockito.any(PageRequest.class));

    }

    @Test
    void updateMovieCacheWorkCorrect() {
        MovieCache movieCache = new MovieCache(movieDao);
        Mockito.when(
                movieDao.findAll(
                        Mockito.any(PageRequest.class)
                )
        ).thenReturn(movies);

        assertNull(movieCache.getMovieCache());

        movieCache.updateMovieCache();

        HashMap<Integer, Movie> updatedMovieCache = movieCache.getMovieCache();

        assertNotNull(updatedMovieCache);
        assertEquals(updatedMovieCache.size(), 2);

        Movie movieOne = updatedMovieCache.get(0);
        Movie movieTwo = updatedMovieCache.get(1);


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

        Mockito.verify(movieDao, Mockito.times(1))
                .findAll(Mockito.any(PageRequest.class));

    }

    @Test
    void fillCacheWithMovies() {
        MovieCache movieCache = new MovieCache(movieDao);
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
        MovieCache movieCache = new MovieCache(movieDao);
        movieCache.fillCacheWithMovies(new ArrayList<>());

        HashMap<Integer, Movie> cache = movieCache.getMovieCache();

        assertNotNull(cache);
        assertTrue(cache.isEmpty());
    }
}