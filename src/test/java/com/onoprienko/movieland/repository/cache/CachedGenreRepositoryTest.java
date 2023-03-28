package com.onoprienko.movieland.repository.cache;

import com.onoprienko.movieland.entity.Genre;
import com.onoprienko.movieland.repository.jpa.JpaGenreRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CachedGenreRepositoryTest {

    List<Genre> genres = List.of(Genre.builder().name("Драма").id(1L).build(),
            Genre.builder().name("Триллер").id(2L).build(),
            Genre.builder().name("Мультфильм").id(3L).build());

    @Test
    void updateVoidGenreCacheReturnNotVoidListOfGenres() {
        JpaGenreRepository repository = Mockito.mock(JpaGenreRepository.class);
        Mockito.when(repository.findAll()).thenReturn(genres);
        CachedGenreRepository genreCache = new CachedGenreRepository(repository);

        genreCache.updateGenreCache();

        List<Genre> all = genreCache.findAll();

        assertNotNull(all);

        assertEquals(all.size(), 3);

        assertEquals(1, all.get(0).getId(), 1);
        assertEquals(2, all.get(1).getId(), 2);
        assertEquals(3, all.get(2).getId(), 3);
        assertEquals("Драма", all.get(0).getName());
        assertEquals("Триллер", all.get(1).getName());
        assertEquals("Мультфильм", all.get(2).getName());
    }


    @Test
    void updateNullGenreCacheReturnNotVoidListOfGenres() {
        JpaGenreRepository repository = Mockito.mock(JpaGenreRepository.class);
        Mockito.when(repository.findAll()).thenReturn(null).thenReturn(genres);
        CachedGenreRepository genreCache = new CachedGenreRepository(repository);

        genreCache.updateGenreCache();

        List<Genre> allNull = genreCache.findAll();
        assertNull(allNull);

        genreCache.updateGenreCache();

        List<Genre> all = genreCache.findAll();

        assertNotNull(all);

        assertEquals(all.size(), 3);

        assertEquals(1, all.get(0).getId(), 1);
        assertEquals(2, all.get(1).getId(), 2);
        assertEquals(3, all.get(2).getId(), 3);
        assertEquals("Драма", all.get(0).getName());
        assertEquals("Триллер", all.get(1).getName());
        assertEquals("Мультфильм", all.get(2).getName());
    }

    @Test
    void updateNotVoidGenreCacheReturnVoidList() {
        JpaGenreRepository repository = Mockito.mock(JpaGenreRepository.class);
        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());
        CachedGenreRepository genreCache = new CachedGenreRepository(repository);

        genreCache.updateGenreCache();

        List<Genre> all = genreCache.findAll();

        assertNotNull(all);
        assertTrue(all.isEmpty());
    }


    @Test
    void updateNotVoidGenreCacheReturnNull() {
        JpaGenreRepository repository = Mockito.mock(JpaGenreRepository.class);
        Mockito.when(repository.findAll()).thenReturn(null);
        CachedGenreRepository genreCache = new CachedGenreRepository(repository);

        genreCache.updateGenreCache();

        List<Genre> all = genreCache.findAll();

        assertNull(all);
    }
}