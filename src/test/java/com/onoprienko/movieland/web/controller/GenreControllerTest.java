package com.onoprienko.movieland.web.controller;

import com.onoprienko.movieland.entity.Genre;
import com.onoprienko.movieland.service.GenreService;
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

@WebMvcTest(GenreController.class)
class GenreControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private GenreService genreService;
    private final String baseUrl = "/api/v1/genre/";

    @Test
    public void findAllReturnListOfGenresAndStatusOk() throws Exception {
        List<Genre> genres = List.of(Genre.builder().id(1L).name("Драма").build(),
                Genre.builder().id(2L).name("Триллер").build(),
                Genre.builder().id(3L).name("Фентези").build(),
                Genre.builder().id(4L).name("Криминал").build());
        given(genreService.findAll()).willReturn(genres);
        mvc.perform(get(baseUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[3].id", is(4)))
                .andExpect(jsonPath("$[0].name", is("Драма")))
                .andExpect(jsonPath("$[1].name", is("Триллер")))
                .andExpect(jsonPath("$[2].name", is("Фентези")))
                .andExpect(jsonPath("$[3].name", is("Криминал")));
        verify(genreService, times(1)).findAll();
    }


    @Test
    public void findAllVoidListAndStatusOk() throws Exception {
        List<Genre> genres = new ArrayList<>();
        given(genreService.findAll()).willReturn(genres);
        mvc.perform(get(baseUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
        verify(genreService, times(1)).findAll();
    }

}