package com.onoprienko.movieland.web.controller;

import com.onoprienko.movieland.entity.Movie;
import com.onoprienko.movieland.service.MovieService;
import com.onoprienko.movieland.service.entity.SortEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
class MovieControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private MovieService movieService;

    private final String baseUrl = "/api/v1/movie/";

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
    Movie tesMovieThree = Movie.builder().id(3L)
            .price(355.0)
            .rating(5.5)
            .nameRussian("Тест три")
            .nameNative("Test three")
            .yearOfRelease(2018)
            .picturePath("path")
            .build();

    List<Movie> movies = List.of(testMovieOne, testMovieTwo, tesMovieThree);

    @Test
    void findAllMoviesByPage() throws Exception {
        given(movieService.findAll(1, SortEnum.NONE, SortEnum.NONE)).willReturn(movies);
        mvc.perform(get(baseUrl + "?page=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[0].price", is(55.8)))
                .andExpect(jsonPath("$[1].price", is(111.1)))
                .andExpect(jsonPath("$[2].price", is(355.0)))
                .andExpect(jsonPath("$[0].rating", is(7.3)))
                .andExpect(jsonPath("$[1].rating", is(9.8)))
                .andExpect(jsonPath("$[2].rating", is(5.5)))
                .andExpect(jsonPath("$[0].nameRussian", is("Тест один")))
                .andExpect(jsonPath("$[1].nameRussian", is("Тест два")))
                .andExpect(jsonPath("$[2].nameRussian", is("Тест три")))
                .andExpect(jsonPath("$[0].nameNative", is("Test one")))
                .andExpect(jsonPath("$[1].nameNative", is("Test two")))
                .andExpect(jsonPath("$[2].nameNative", is("Test three")))
                .andExpect(jsonPath("$[0].yearOfRelease", is(1900)))
                .andExpect(jsonPath("$[1].yearOfRelease", is(2000)))
                .andExpect(jsonPath("$[2].yearOfRelease", is(2018)))
                .andExpect(jsonPath("$[0].picturePath", is("path")))
                .andExpect(jsonPath("$[1].picturePath", is("path")))
                .andExpect(jsonPath("$[2].picturePath", is("path")));

        verify(movieService, times(1)).findAll(1, SortEnum.NONE, SortEnum.NONE);
    }

    @Test
    void findAllMoviesByPageAndRatingDesc() throws Exception {
        List<Movie> descRatingMovies = List.of(testMovieTwo, testMovieOne, tesMovieThree);
        given(movieService.findAll(1, SortEnum.DESC, SortEnum.NONE)).willReturn(descRatingMovies);
        mvc.perform(get(baseUrl + "?page=1&rating=desc").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[1].id", is(1)))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[0].price", is(111.1)))
                .andExpect(jsonPath("$[1].price", is(55.8)))
                .andExpect(jsonPath("$[2].price", is(355.0)))
                .andExpect(jsonPath("$[0].rating", is(9.8)))
                .andExpect(jsonPath("$[1].rating", is(7.3)))
                .andExpect(jsonPath("$[2].rating", is(5.5)))
                .andExpect(jsonPath("$[0].nameRussian", is("Тест два")))
                .andExpect(jsonPath("$[1].nameRussian", is("Тест один")))
                .andExpect(jsonPath("$[2].nameRussian", is("Тест три")))
                .andExpect(jsonPath("$[0].nameNative", is("Test two")))
                .andExpect(jsonPath("$[1].nameNative", is("Test one")))
                .andExpect(jsonPath("$[2].nameNative", is("Test three")))
                .andExpect(jsonPath("$[0].yearOfRelease", is(2000)))
                .andExpect(jsonPath("$[1].yearOfRelease", is(1900)))
                .andExpect(jsonPath("$[2].yearOfRelease", is(2018)))
                .andExpect(jsonPath("$[0].picturePath", is("path")))
                .andExpect(jsonPath("$[1].picturePath", is("path")))
                .andExpect(jsonPath("$[2].picturePath", is("path")));

        verify(movieService, times(1)).findAll(1, SortEnum.DESC, SortEnum.NONE);
    }

    @Test
    void findAllMoviesByPageAndPriceAsc() throws Exception {
        given(movieService.findAll(1, SortEnum.NONE, SortEnum.ASC)).willReturn(movies);
        mvc.perform(get(baseUrl + "?page=1&price=asc").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[0].price", is(55.8)))
                .andExpect(jsonPath("$[1].price", is(111.1)))
                .andExpect(jsonPath("$[2].price", is(355.0)))
                .andExpect(jsonPath("$[0].rating", is(7.3)))
                .andExpect(jsonPath("$[1].rating", is(9.8)))
                .andExpect(jsonPath("$[2].rating", is(5.5)))
                .andExpect(jsonPath("$[0].nameRussian", is("Тест один")))
                .andExpect(jsonPath("$[1].nameRussian", is("Тест два")))
                .andExpect(jsonPath("$[2].nameRussian", is("Тест три")))
                .andExpect(jsonPath("$[0].nameNative", is("Test one")))
                .andExpect(jsonPath("$[1].nameNative", is("Test two")))
                .andExpect(jsonPath("$[2].nameNative", is("Test three")))
                .andExpect(jsonPath("$[0].yearOfRelease", is(1900)))
                .andExpect(jsonPath("$[1].yearOfRelease", is(2000)))
                .andExpect(jsonPath("$[2].yearOfRelease", is(2018)))
                .andExpect(jsonPath("$[0].picturePath", is("path")))
                .andExpect(jsonPath("$[1].picturePath", is("path")))
                .andExpect(jsonPath("$[2].picturePath", is("path")));

        verify(movieService, times(1)).findAll(1, SortEnum.NONE, SortEnum.ASC);
    }

    @Test
    void findAllMoviesByPageAndPriceDesc() throws Exception {
        List<Movie> descPriceMovies = List.of(tesMovieThree, testMovieTwo, testMovieOne);
        given(movieService.findAll(1, SortEnum.NONE, SortEnum.DESC)).willReturn(descPriceMovies);
        mvc.perform(get(baseUrl + "?page=1&price=desc").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[0].id", is(3)))
                .andExpect(jsonPath("$[2].price", is(55.8)))
                .andExpect(jsonPath("$[1].price", is(111.1)))
                .andExpect(jsonPath("$[0].price", is(355.0)))
                .andExpect(jsonPath("$[2].rating", is(7.3)))
                .andExpect(jsonPath("$[1].rating", is(9.8)))
                .andExpect(jsonPath("$[0].rating", is(5.5)))
                .andExpect(jsonPath("$[2].nameRussian", is("Тест один")))
                .andExpect(jsonPath("$[1].nameRussian", is("Тест два")))
                .andExpect(jsonPath("$[0].nameRussian", is("Тест три")))
                .andExpect(jsonPath("$[2].nameNative", is("Test one")))
                .andExpect(jsonPath("$[1].nameNative", is("Test two")))
                .andExpect(jsonPath("$[0].nameNative", is("Test three")))
                .andExpect(jsonPath("$[2].yearOfRelease", is(1900)))
                .andExpect(jsonPath("$[1].yearOfRelease", is(2000)))
                .andExpect(jsonPath("$[0].yearOfRelease", is(2018)))
                .andExpect(jsonPath("$[2].picturePath", is("path")))
                .andExpect(jsonPath("$[1].picturePath", is("path")))
                .andExpect(jsonPath("$[0].picturePath", is("path")));

        verify(movieService, times(1)).findAll(1, SortEnum.NONE, SortEnum.DESC);
    }

    @Test
    void findAllMoviesReturnBadRequestOnException() throws Exception {
        given(movieService.findAll(2, SortEnum.NONE, SortEnum.NONE)).willThrow(new RuntimeException("Exception"));
        mvc.perform(get(baseUrl + "?page=2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(movieService, times(1)).findAll(2, SortEnum.NONE, SortEnum.NONE);
    }


    @Test
    void findByGenreReturnListOfMovies() throws Exception {
        List<Movie> moviesByGenre = List.of(testMovieOne, testMovieTwo);
        given(movieService.findAllByGenre(1, 1)).willReturn(moviesByGenre);
        mvc.perform(get(baseUrl + "/genre/1?page=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[0].price", is(55.8)))
                .andExpect(jsonPath("$[1].price", is(111.1)))
                .andExpect(jsonPath("$[0].rating", is(7.3)))
                .andExpect(jsonPath("$[1].rating", is(9.8)))
                .andExpect(jsonPath("$[0].nameRussian", is("Тест один")))
                .andExpect(jsonPath("$[1].nameRussian", is("Тест два")))
                .andExpect(jsonPath("$[0].nameNative", is("Test one")))
                .andExpect(jsonPath("$[1].nameNative", is("Test two")))
                .andExpect(jsonPath("$[0].yearOfRelease", is(1900)))
                .andExpect(jsonPath("$[1].yearOfRelease", is(2000)))
                .andExpect(jsonPath("$[0].picturePath", is("path")))
                .andExpect(jsonPath("$[1].picturePath", is("path")));

        verify(movieService, times(1)).findAllByGenre(1, 1);
    }


    @Test
    void findByGenreReturnVoidList() throws Exception {
        List<Movie> moviesByGenre = new ArrayList<>();
        given(movieService.findAllByGenre(1, 1)).willReturn(moviesByGenre);
        mvc.perform(get(baseUrl + "/genre/15?page=15").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(movieService, times(1)).findAllByGenre(15, 15);
    }

    @Test
    void findByGenreReturnBadRequest() throws Exception {
        given(movieService.findAllByGenre(166, 900)).willThrow(new RuntimeException("Exception"));
        mvc.perform(get(baseUrl + "/genre/166?page=900").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(movieService, times(1)).findAllByGenre(166, 900);
    }

    @Test
    void findRandomRetutnListOfMovies() throws Exception {
        List<Movie> randomMovies = List.of(testMovieOne, testMovieTwo);
        given(movieService.findRandom()).willReturn(randomMovies);
        mvc.perform(get(baseUrl + "/random").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[0].price", is(55.8)))
                .andExpect(jsonPath("$[1].price", is(111.1)))
                .andExpect(jsonPath("$[0].rating", is(7.3)))
                .andExpect(jsonPath("$[1].rating", is(9.8)))
                .andExpect(jsonPath("$[0].nameRussian", is("Тест один")))
                .andExpect(jsonPath("$[1].nameRussian", is("Тест два")))
                .andExpect(jsonPath("$[0].nameNative", is("Test one")))
                .andExpect(jsonPath("$[1].nameNative", is("Test two")))
                .andExpect(jsonPath("$[0].yearOfRelease", is(1900)))
                .andExpect(jsonPath("$[1].yearOfRelease", is(2000)))
                .andExpect(jsonPath("$[0].picturePath", is("path")))
                .andExpect(jsonPath("$[1].picturePath", is("path")));

        verify(movieService, times(1)).findRandom();
    }

    @Test
    void findRandomMoviesReturnBadRequest() throws Exception {
        given(movieService.findRandom()).willThrow(new RuntimeException("Exception"));
        mvc.perform(get(baseUrl + "/random").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(movieService, times(1)).findRandom();
    }
}