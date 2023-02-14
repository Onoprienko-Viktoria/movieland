package com.onoprienko.movieland.repository.dao.jdbc.mapper;

import com.onoprienko.movieland.entity.Genre;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GenreRowMapperTest {

    @Test
    void mapRow() throws SQLException {
        ResultSet resultSetMock = mock(ResultSet.class);
        GenreRowMapper genreRowMapper = new GenreRowMapper();
        when(resultSetMock.getLong("id"))
                .thenReturn(1L);
        when(resultSetMock.getString("name"))
                .thenReturn("Драма");

        Genre genre = genreRowMapper.mapRow(resultSetMock);

        assertNotNull(genre);
        assertEquals(genre.getId(), 1L);
        assertEquals(genre.getName(), "Драма");
    }
}