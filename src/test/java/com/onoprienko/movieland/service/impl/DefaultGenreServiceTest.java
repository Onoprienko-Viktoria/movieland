package com.onoprienko.movieland.service.impl;

import com.onoprienko.movieland.entity.Genre;
import com.onoprienko.movieland.repository.dao.GenreDao;
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
        GenreDao genreDao = Mockito.mock(GenreDao.class);
        GenreService genreService = new DefaultGenreService(genres, genreDao);

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
        GenreDao genreDao = Mockito.mock(GenreDao.class);
        GenreService genreService = new DefaultGenreService(new ArrayList<>(), genreDao);

        List<Genre> all = genreService.findAll();

        assertNotNull(all);
        assertTrue(all.isEmpty());
    }

    @Test
    void findAllReturnNullFromCache() {
        GenreDao genreDao = Mockito.mock(GenreDao.class);
        GenreService genreService = new DefaultGenreService(null, genreDao);

        List<Genre> all = genreService.findAll();

        assertNull(all);
    }


    @Test
    void updateVoidGenreCacheReturnNotVoidListOfGenres() {
        GenreDao genreDao = Mockito.mock(GenreDao.class);
        DefaultGenreService genreService = new DefaultGenreService(new ArrayList<>(), genreDao);
        Mockito.when(genreDao.findAll()).thenReturn(genres);

        genreService.updateGenreCache();

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
    void updateNullGenreCacheReturnNotVoidListOfGenres() {
        GenreDao genreDao = Mockito.mock(GenreDao.class);
        DefaultGenreService genreService = new DefaultGenreService(null, genreDao);
        Mockito.when(genreDao.findAll()).thenReturn(genres);

        genreService.updateGenreCache();

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
    void updateNotVoidGenreCacheReturnVoidList() {
        GenreDao genreDao = Mockito.mock(GenreDao.class);
        DefaultGenreService genreService = new DefaultGenreService(genres, genreDao);

        Mockito.when(genreDao.findAll()).thenReturn(new ArrayList<>());

        genreService.updateGenreCache();

        List<Genre> all = genreService.findAll();

        assertNotNull(all);
        assertTrue(all.isEmpty());
    }


    @Test
    void updateNotVoidGenreCacheReturnNull() {
        GenreDao genreDao = Mockito.mock(GenreDao.class);
        DefaultGenreService genreService = new DefaultGenreService(genres, genreDao);

        Mockito.when(genreDao.findAll()).thenReturn(null);

        genreService.updateGenreCache();

        List<Genre> all = genreService.findAll();

        assertNull(all);
    }

}