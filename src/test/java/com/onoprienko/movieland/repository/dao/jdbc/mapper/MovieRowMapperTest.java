package com.onoprienko.movieland.repository.dao.jdbc.mapper;

import com.onoprienko.movieland.entity.Movie;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MovieRowMapperTest {
    @Test
    void mapRow() throws SQLException {
        ResultSet resultSetMock = mock(ResultSet.class);
        MovieRowMapper movieRowMapper = new MovieRowMapper();
        when(resultSetMock.getLong("id"))
                .thenReturn(11L);
        when(resultSetMock.getString("name_native"))
                .thenReturn("Test");
        when(resultSetMock.getString("name_russian"))
                .thenReturn("Тест");
        when(resultSetMock.getInt("year_of_release"))
                .thenReturn(1999);
        when(resultSetMock.getDouble("rating"))
                .thenReturn(9.9);
        when(resultSetMock.getDouble("price"))
                .thenReturn(100.9);
        when(resultSetMock.getString("picture_path"))
                .thenReturn("path");

        Movie movie = movieRowMapper.mapRow(resultSetMock);

        assertNotNull(movie);
        assertEquals(movie.getId(), 11L);
        assertEquals(movie.getNameNative(), "Test");
        assertEquals(movie.getNameRussian(), "Тест");
        assertEquals(movie.getYearOfRelease(), 1999);
        assertEquals(movie.getRating(), 9.9);
        assertEquals(movie.getPrice(), 100.9);
        assertEquals(movie.getPicturePath(), "path");
    }
}