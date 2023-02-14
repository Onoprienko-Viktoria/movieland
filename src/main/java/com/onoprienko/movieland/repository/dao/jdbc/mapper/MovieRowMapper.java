package com.onoprienko.movieland.repository.dao.jdbc.mapper;


import com.onoprienko.movieland.entity.Movie;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieRowMapper {
    public Movie mapRow(ResultSet resultSet) throws SQLException {
        return Movie.builder()
                .id(resultSet.getLong("id"))
                .nameNative(resultSet.getString("name_native"))
                .nameRussian(resultSet.getString("name_russian"))
                .yearOfRelease(resultSet.getInt("year_of_release"))
                .rating(resultSet.getDouble("rating"))
                .picturePath(resultSet.getString("picture_path"))
                .price(resultSet.getDouble("price"))
                .build();
    }
}
