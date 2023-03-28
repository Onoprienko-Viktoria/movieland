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
        assertEquals(2, result.size());

        MovieDto movieOne = result.get(0);
        MovieDto movieTwo = result.get(1);
        assertEquals(1, movieOne.getId());
        assertEquals(55.8, movieOne.getPrice());
        assertEquals(7.3, movieOne.getRating());
        assertEquals("Test one", movieOne.getNameNative());
        assertEquals("Тест один", movieOne.getNameRussian());
        assertEquals(1900, movieOne.getYearOfRelease());
        assertEquals("path", movieOne.getPicturePath());

        assertEquals(2, movieTwo.getId());
        assertEquals(111.1, movieTwo.getPrice());
        assertEquals(9.8, movieTwo.getRating());
        assertEquals("Test two", movieTwo.getNameNative());
        assertEquals("Тест два", movieTwo.getNameRussian());
        assertEquals(2000, movieTwo.getYearOfRelease());
        assertEquals("path", movieTwo.getPicturePath());
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