package com.onoprienko.movieland.repository.dao.jdbc.mapper;

import com.onoprienko.movieland.entity.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreRowMapper {
    public Genre mapRow(ResultSet resultSet) throws SQLException {
        return Genre.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .build();

    }
}
