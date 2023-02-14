package com.onoprienko.movieland.repository.dao.jdbc;

import com.onoprienko.movieland.entity.Movie;
import com.onoprienko.movieland.repository.dao.MovieDao;
import com.onoprienko.movieland.repository.dao.jdbc.mapper.MovieRowMapper;
import com.onoprienko.movieland.service.entity.PageRequest;
import com.onoprienko.movieland.service.entity.SortEnum;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
@AllArgsConstructor
@Setter
public class JdbcMovieDao implements MovieDao {
    private static final String FIND_ALL_MOVIES_SQL = "SELECT id, name_russian, name_native, year_of_release, rating, price, picture_path FROM movie limit ? offset ?";
    private static final String FIND_ALL_MOVIES_ORDER_BY_RATING_DESC_SQL = "SELECT id, name_russian, name_native, year_of_release, rating, price, picture_path FROM movie order by rating desc limit ? offset ?";
    private static final String FIND_ALL_MOVIES_ORDER_BY_PRICE_SQL = "SELECT id, name_russian, name_native, year_of_release, rating, price, picture_path FROM movie order by price SORTING limit ? offset ?";
    private static final String FIND_ALL_MOVIES_BY_ID_IN = "SELECT id, name_russian, name_native, year_of_release, rating, price, picture_path FROM movie WHERE id IN (SELECT movie_id FROM movie_genre WHERE genre_id = ?) limit ? offset ?";
    private static final String COUNT_ROWS_SQL = "SELECT COUNT(*)  FROM movie";
    private static final String SORTING_REGEX = "SORTING";
    private final DataSource dataSource;

    private final static MovieRowMapper ROW_MAPPER = new MovieRowMapper();

    @Override
    @SneakyThrows
    public List<Movie> findAll(PageRequest pageRequest) {
        return findAll(pageRequest, FIND_ALL_MOVIES_SQL);
    }

    @Override
    @SneakyThrows
    public List<Movie> findAllByRating(PageRequest pageRequest) {
        return findAll(pageRequest, FIND_ALL_MOVIES_ORDER_BY_RATING_DESC_SQL);
    }

    @Override
    @SneakyThrows
    public List<Movie> findAllByPrice(PageRequest pageRequest, SortEnum sort) {
        return findAll(pageRequest, FIND_ALL_MOVIES_ORDER_BY_PRICE_SQL.replaceAll(SORTING_REGEX, sort.name()));
    }

    @Override
    @SneakyThrows
    public List<Movie> findAllByGenre(Integer genreId, PageRequest pageRequest) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_MOVIES_BY_ID_IN)) {
            preparedStatement.setInt(1, genreId);
            preparedStatement.setInt(2, pageRequest.getCapacity());
            preparedStatement.setInt(3, pageRequest.getCapacity() * pageRequest.getPage());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Movie> movies = new ArrayList<>();
            while (resultSet.next()) {
                movies.add(ROW_MAPPER.mapRow((resultSet)));
            }
            return movies;
        }
    }

    @Override
    @SneakyThrows
    public Integer getCount() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_ROWS_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            resultSet.next();
            return resultSet.getInt("count");
        }
    }

    List<Movie> findAll(PageRequest pageRequest, String findAllMoviesSql) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findAllMoviesSql)) {
            preparedStatement.setInt(1, pageRequest.getCapacity());
            preparedStatement.setInt(2, pageRequest.getCapacity() * pageRequest.getPage());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Movie> movies = new ArrayList<>();
            while (resultSet.next()) {
                movies.add(ROW_MAPPER.mapRow((resultSet)));
            }
            return movies;
        }
    }

}

