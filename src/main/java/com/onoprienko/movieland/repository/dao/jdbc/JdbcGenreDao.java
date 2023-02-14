package com.onoprienko.movieland.repository.dao.jdbc;


import com.onoprienko.movieland.entity.Genre;
import com.onoprienko.movieland.repository.dao.GenreDao;
import com.onoprienko.movieland.repository.dao.jdbc.mapper.GenreRowMapper;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
@Setter
@Slf4j
public class JdbcGenreDao implements GenreDao {
    private static final String FIND_ALL_GENRES_SQL = "SELECT id, name FROM genre";
    private final DataSource dataSource;
    private final static GenreRowMapper ROW_MAPPER = new GenreRowMapper();

    @Override
    @SneakyThrows
    public List<Genre> findAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_GENRES_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<Genre> genres = new ArrayList<>();
            while (resultSet.next()) {
                genres.add(ROW_MAPPER.mapRow((resultSet)));
            }
            return genres;
        }
    }
}
