package com.onoprienko.movieland.service.impl;

import com.onoprienko.movieland.entity.Genre;
import com.onoprienko.movieland.repository.cache.CachedGenreRepository;
import com.onoprienko.movieland.service.GenreService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class DefaultGenreServiceTest {

    List<Genre> genres = List.of(Genre.builder().name("Драма").id(1L).build(),
            Genre.builder().name("Триллер").id(2L).build(),
            Genre.builder().name("Мультфильм").id(3L).build());

    @Test
    void findAllReturnListOfGenresFromCache() {
        CachedGenreRepository repository = Mockito.mock(CachedGenreRepository.class);
        Mockito.when(repository.findAll()).thenReturn(genres);
        GenreService genreService = new DefaultGenreService(repository);

        List<Genre> all = genreService.findAll();

        assertNotNull(all);

        assertEquals(all.size(), 3);

        assertEquals(all.get(0).getId(), 1);
        assertEquals(all.get(1).getId(), 2);
        assertEquals(all.get(2).getId(), 3);
        assertEquals(all.get(0).getName(), "Драма");
        assertEquals(all.get(1).getName(), "Триллер");
        assertEquals(all.get(2).getName(), "Мультфильм");
    }

    @Test
    void findAllReturnVoidListFromCache() {
        CachedGenreRepository repository = Mockito.mock(CachedGenreRepository.class);
        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());
        GenreService genreService = new DefaultGenreService(repository);

        List<Genre> all = genreService.findAll();

        assertNotNull(all);
        assertTrue(all.isEmpty());
    }

    @Test
    void findAllReturnNullFromCache() {
        CachedGenreRepository repository = Mockito.mock(CachedGenreRepository.class);
        Mockito.when(repository.findAll()).thenReturn(null);
        GenreService genreService = new DefaultGenreService(repository);

        List<Genre> all = genreService.findAll();

        assertNull(all);
    }

}