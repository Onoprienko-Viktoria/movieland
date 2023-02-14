package com.onoprienko.movieland.service.impl;

import com.onoprienko.movieland.entity.Movie;
import com.onoprienko.movieland.repository.dao.MovieDao;
import com.onoprienko.movieland.service.MovieService;
import com.onoprienko.movieland.service.entity.MovieCache;
import com.onoprienko.movieland.service.entity.PageRequest;
import com.onoprienko.movieland.service.entity.SortEnum;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DefaultMovieServiceTest {
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


    MovieDao movieDao = Mockito.mock(MovieDao.class);
    MovieService movieService = new DefaultMovieService(movieDao, new MovieCache());


    @Test
    void findAllReturnList() {
        Mockito.when(
                movieDao.findAll(
                        Mockito.any(PageRequest.class)
                )
        ).thenReturn(movies);

        List<Movie> all = movieService.findAll(1, SortEnum.NONE, SortEnum.NONE);

        assertNotNull(all);
        assertEquals(all.size(), 2);

        Movie movieOne = all.get(0);
        Movie movieTwo = all.get(1);


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
    void findAllWithRatingDescReturnList() {
        Mockito.when(
                movieDao.findAllByRating(
                        Mockito.any(PageRequest.class)
                )
        ).thenReturn(List.of(testMovieTwo, testMovieOne));

        List<Movie> all = movieService.findAll(1, SortEnum.DESC, SortEnum.NONE);

        assertNotNull(all);
        assertEquals(all.size(), 2);

        Movie movieOne = all.get(1);
        Movie movieTwo = all.get(0);


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
                .findAllByRating(Mockito.any(PageRequest.class));
    }


    @Test
    void findAllWithRatingAscNotSortList() {
        Mockito.when(
                movieDao.findAll(
                        Mockito.any(PageRequest.class)
                )
        ).thenReturn(movies);

        List<Movie> all = movieService.findAll(1, SortEnum.ASC, SortEnum.NONE);

        assertNotNull(all);
        assertEquals(all.size(), 2);

        Movie movieOne = all.get(0);
        Movie movieTwo = all.get(1);


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
    void findAllWithPriceAscReturnList() {
        Mockito.when(
                movieDao.findAllByPrice(
                        Mockito.any(PageRequest.class), Mockito.any(SortEnum.class)
                )
        ).thenReturn(movies);

        List<Movie> all = movieService.findAll(1, SortEnum.NONE, SortEnum.ASC);

        assertNotNull(all);
        assertEquals(all.size(), 2);

        Movie movieOne = all.get(0);
        Movie movieTwo = all.get(1);


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
                .findAllByPrice(Mockito.any(PageRequest.class), Mockito.any(SortEnum.class));
    }

    @Test
    void findAllWithPriceDescReturnList() {
        Mockito.when(
                movieDao.findAllByPrice(
                        Mockito.any(PageRequest.class), Mockito.any(SortEnum.class)
                )
        ).thenReturn(List.of(testMovieTwo, testMovieOne));

        List<Movie> all = movieService.findAll(1, SortEnum.NONE, SortEnum.DESC);

        assertNotNull(all);
        assertEquals(all.size(), 2);

        Movie movieOne = all.get(1);
        Movie movieTwo = all.get(0);


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
                .findAllByPrice(Mockito.any(PageRequest.class), Mockito.any(SortEnum.class));
    }


    @Test
    void findAllWithPriceAndRatingSortWillReturnRatingSortList() {
        Mockito.when(
                movieDao.findAllByRating(
                        Mockito.any(PageRequest.class)
                )
        ).thenReturn(movies);

        List<Movie> all = movieService.findAll(1, SortEnum.DESC, SortEnum.DESC);

        assertNotNull(all);
        assertEquals(all.size(), 2);

        Movie movieOne = all.get(0);
        Movie movieTwo = all.get(1);


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
                .findAllByRating(Mockito.any(PageRequest.class));
    }

    @Test
    void findAllReturnEmptyList() {
        Mockito.when(
                movieDao.findAll(
                        Mockito.any(PageRequest.class)
                )
        ).thenReturn(new ArrayList<>());

        List<Movie> all = movieService.findAll(1, SortEnum.NONE, SortEnum.NONE);

        assertNotNull(all);
        assertEquals(all.size(), 0);

        Mockito.verify(movieDao, Mockito.times(1))
                .findAll(Mockito.any(PageRequest.class));
    }

    @Test
    void findAllReturnException() {
        Mockito.when(
                movieDao.findAll(
                        Mockito.any(PageRequest.class)
                )
        ).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> movieService.findAll(1, SortEnum.NONE, SortEnum.NONE));

        Mockito.verify(movieDao, Mockito.times(1))
                .findAll(Mockito.any(PageRequest.class));
    }

    @Test
    void findAllByGenreReturnListOfMovies() {
        Mockito.when(
                movieDao.findAllByGenre(Mockito.anyInt(),
                        Mockito.any(PageRequest.class)
                )
        ).thenReturn(movies);

        List<Movie> all = movieService.findAllByGenre(1, 1);

        assertNotNull(all);
        assertEquals(all.size(), 2);

        Movie movieOne = all.get(0);
        Movie movieTwo = all.get(1);


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
                .findAllByGenre(Mockito.anyInt(), Mockito.any(PageRequest.class));
    }


    @Test
    void findAllByGenreReturnVoidList() {
        Mockito.when(
                movieDao.findAllByGenre(Mockito.anyInt(),
                        Mockito.any(PageRequest.class)
                )
        ).thenReturn(new ArrayList<>());

        List<Movie> all = movieService.findAllByGenre(133, 1);

        assertNotNull(all);
        assertEquals(all.size(), 0);

        Mockito.verify(movieDao, Mockito.times(1))
                .findAllByGenre(Mockito.anyInt(), Mockito.any(PageRequest.class));
    }

    @Test
    void findAllByGenreReturnException() {
        Mockito.when(
                movieDao.findAllByGenre(Mockito.anyInt(),
                        Mockito.any(PageRequest.class)
                )
        ).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> movieService.findAllByGenre(133, 1));

        Mockito.verify(movieDao, Mockito.times(1))
                .findAllByGenre(Mockito.anyInt(), Mockito.any(PageRequest.class));
    }

    @Test
    void updateMovieCacheWorkCorrect() {
        MovieCache movieCache = new MovieCache();
        DefaultMovieService defaultMovieService = new DefaultMovieService(movieDao, movieCache);
        Mockito.when(
                movieDao.findAll(
                        Mockito.any(PageRequest.class)
                )
        ).thenReturn(movies);

        assertNull(movieCache.getMovieCache());

        defaultMovieService.updateMovieCache();

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
                .findAll( Mockito.any(PageRequest.class));

    }


    @Test
    void updateNotVoidMovieCacheWithVoidListWorkCorrect() {
        MovieCache movieCache = new MovieCache();
        DefaultMovieService defaultMovieService = new DefaultMovieService(movieDao, movieCache);
        Mockito.when(
                movieDao.findAll(
                        Mockito.any(PageRequest.class)
                )
        ).thenReturn(movies).thenReturn(new ArrayList<>());

        assertNull(movieCache.getMovieCache());
        defaultMovieService.updateMovieCache();

        HashMap<Integer, Movie> updatedMovieCache = movieCache.getMovieCache();

        assertNotNull(updatedMovieCache);
        assertEquals(updatedMovieCache.size(), 2);

        defaultMovieService.updateMovieCache();

        HashMap<Integer, Movie> voidMovieCache = movieCache.getMovieCache();

        assertNotNull(voidMovieCache);
        assertTrue(voidMovieCache.isEmpty());

        Mockito.verify(movieDao, Mockito.times(2))
                .findAll( Mockito.any(PageRequest.class));

    }

    @Test
    void updateVoidMovieCacheWithVoidListWorkCorrect() {
        MovieCache movieCache = new MovieCache();
        DefaultMovieService defaultMovieService = new DefaultMovieService(movieDao, movieCache);
        Mockito.when(
                movieDao.findAll(
                        Mockito.any(PageRequest.class)
                )
        ).thenReturn(new ArrayList<>()).thenReturn(new ArrayList<>());

        assertNull(movieCache.getMovieCache());
        defaultMovieService.updateMovieCache();

        HashMap<Integer, Movie> updatedMovieCache = movieCache.getMovieCache();

        assertNotNull(updatedMovieCache);
        assertTrue(updatedMovieCache.isEmpty());

        defaultMovieService.updateMovieCache();

        HashMap<Integer, Movie> voidMovieCache = movieCache.getMovieCache();

        assertNotNull(voidMovieCache);
        assertTrue(voidMovieCache.isEmpty());

        Mockito.verify(movieDao, Mockito.times(2))
                .findAll( Mockito.any(PageRequest.class));

    }

    @Test
    void updateCacheThrowException() {
        MovieCache movieCache = new MovieCache();
        DefaultMovieService defaultMovieService = new DefaultMovieService(movieDao, movieCache);
        Mockito.when(
                movieDao.findAll(
                        Mockito.any(PageRequest.class)
                )
        ).thenThrow(new RuntimeException());

        assertNull(movieCache.getMovieCache());

        assertThrows(RuntimeException.class, defaultMovieService::updateMovieCache);

        Mockito.verify(movieDao, Mockito.times(1))
                .findAll( Mockito.any(PageRequest.class));

    }

    @Test
    void findRandomFromCacheWithSameValuesReturnCorrectResult() {
        MovieCache movieCache = new MovieCache();
        DefaultMovieService defaultMovieService = new DefaultMovieService(movieDao, movieCache);
        defaultMovieService.setRandomPageCapacity(3);
        Mockito.when(
                movieDao.findAll(
                        Mockito.any(PageRequest.class)
                )
        ).thenReturn(List.of(testMovieOne, testMovieOne, testMovieOne));
        defaultMovieService.updateMovieCache();

        List<Movie> random = defaultMovieService.findRandom();
        assertNotNull(random);
        assertEquals(random.size(), 3);
        Movie movieOne = random.get(0);

        assertEquals(movieOne.getId(), 1);
        assertEquals(movieOne.getPrice(), 55.8);
        assertEquals(movieOne.getRating(), 7.3);
        assertEquals(movieOne.getNameNative(), "Test one");
        assertEquals(movieOne.getNameRussian(), "Тест один");
        assertEquals(movieOne.getYearOfRelease(), 1900);
        assertEquals(movieOne.getPicturePath(), "path");

        Movie movieTwo = random.get(1);

        assertEquals(movieTwo.getId(), 1);
        assertEquals(movieTwo.getPrice(), 55.8);
        assertEquals(movieTwo.getRating(), 7.3);
        assertEquals(movieTwo.getNameNative(), "Test one");
        assertEquals(movieTwo.getNameRussian(), "Тест один");
        assertEquals(movieTwo.getYearOfRelease(), 1900);
        assertEquals(movieTwo.getPicturePath(), "path");
    }

    @Test
    void findRandomFromCacheReturnCorrectResult() {
        MovieCache movieCache = new MovieCache();
        DefaultMovieService defaultMovieService = new DefaultMovieService(movieDao, movieCache);
        defaultMovieService.setRandomPageCapacity(2);
        Mockito.when(
                movieDao.findAll(
                        Mockito.any(PageRequest.class)
                )
        ).thenReturn(movies);
        defaultMovieService.updateMovieCache();

        List<Movie> random = defaultMovieService.findRandom();
        assertNotNull(random);
        assertEquals(random.size(), 2);

    }

    @Test
    void findRandomFromCacheReturnVoidListIfPageCapacityZero() {
        MovieCache movieCache = new MovieCache();
        DefaultMovieService defaultMovieService = new DefaultMovieService(movieDao, movieCache);
        defaultMovieService.setRandomPageCapacity(0);
        Mockito.when(
                movieDao.findAll(
                        Mockito.any(PageRequest.class)
                )
        ).thenReturn(movies);
        defaultMovieService.updateMovieCache();

        List<Movie> random = defaultMovieService.findRandom();
        assertNotNull(random);
        assertTrue(random.isEmpty());

    }

    @Test
    void findRandomFromOneItemCacheReturnException() {
        MovieCache movieCache = new MovieCache();
        DefaultMovieService defaultMovieService = new DefaultMovieService(movieDao, movieCache);
        Mockito.when(
                movieDao.findAll(
                        Mockito.any(PageRequest.class)
                )
        ).thenReturn(List.of(testMovieOne));
        defaultMovieService.updateMovieCache();

        assertThrows(IllegalArgumentException.class, defaultMovieService::findRandom);
    }
}