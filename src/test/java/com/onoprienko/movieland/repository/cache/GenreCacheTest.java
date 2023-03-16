package com.onoprienko.movieland.repository.cache;

import com.onoprienko.movieland.entity.Genre;
import com.onoprienko.movieland.repository.dao.GenreDao;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenreCacheTest {
    List<Genre> genres = List.of(Genre.builder().name("Драма").id(1L).build(),
            Genre.builder().name("Триллер").id(2L).build(),
            Genre.builder().name("Мультфильм").id(3L).build());

    @Test
    void updateVoidGenreCacheReturnNotVoidListOfGenres() {
        GenreDao genreDao = Mockito.mock(GenreDao.class);
        Mockito.when(genreDao.findAll()).thenReturn(genres);
        GenreCache genreCache = new GenreCache(genreDao);

        genreCache.updateGenreCache();

        List<Genre> all = genreCache.getGenreCache();

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
        Mockito.when(genreDao.findAll()).thenReturn(null).thenReturn(genres);
        GenreCache genreCache = new GenreCache(genreDao);

        genreCache.updateGenreCache();

        List<Genre> allNull = genreCache.getGenreCache();
        assertNull(allNull);

        genreCache.updateGenreCache();

        List<Genre> all = genreCache.getGenreCache();

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
        Mockito.when(genreDao.findAll()).thenReturn(new ArrayList<>());
        GenreCache genreCache = new GenreCache(genreDao);

        genreCache.updateGenreCache();

        List<Genre> all = genreCache.getGenreCache();

        assertNotNull(all);
        assertTrue(all.isEmpty());
    }


    @Test
    void updateNotVoidGenreCacheReturnNull() {
        GenreDao genreDao = Mockito.mock(GenreDao.class);
        Mockito.when(genreDao.findAll()).thenReturn(null);
        GenreCache genreCache = new GenreCache(genreDao);

        genreCache.updateGenreCache();

        List<Genre> all = genreCache.getGenreCache();

        assertNull(all);
    }

}