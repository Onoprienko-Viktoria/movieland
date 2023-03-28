package com.onoprienko.movieland.mapper;

import com.onoprienko.movieland.dto.MovieDto;
import com.onoprienko.movieland.entity.Movie;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieMapperTest {

    @Test
    void testMapToMovieDtoList() {
        Movie testMovie = Movie.builder()
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

        List<Movie> movies = List.of(testMovie, testMovieTwo);

        MovieMapper mapper = Mappers.getMapper(MovieMapper.class);

        List<MovieDto> result = mapper.mapToMovieDtoList(movies);

        assertNotNull(result);
        assertEquals(result.size(), 2);

        MovieDto movieOne = result.get(0);
        MovieDto movieTwo= result.get(1);
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
    void testMapToMovieDtoListOnVoidList() {
        List<Movie> movies = new ArrayList<>();

        MovieMapper mapper = Mappers.getMapper(MovieMapper.class);

        List<MovieDto> result = mapper.mapToMovieDtoList(movies);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testMapToMovieDtoListOnNullList() {
        List<Movie> movies = null;

        MovieMapper mapper = Mappers.getMapper(MovieMapper.class);

        List<MovieDto> result = mapper.mapToMovieDtoList(movies);

        assertNull(result);
    }
}