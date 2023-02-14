package com.onoprienko.movieland.repository.dao.jdbc;

import com.onoprienko.movieland.entity.Genre;
import com.onoprienko.movieland.repository.dao.GenreDao;
import com.onoprienko.movieland.repository.dao.jdbc.utils.JdbcDaoTestUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcGenreDaoTest {

    @BeforeAll
    public void init() throws IOException, SQLException {
        JdbcDaoTestUtil.createTables();
    }

    @Test
    void findAll() {
        DataSource datasource = JdbcDaoTestUtil.getDatasource();
        GenreDao genreDao = new JdbcGenreDao(datasource);

        List<Genre> all = genreDao.findAll();

        assertNotNull(all);

        assertEquals(all.size(), 15);

        assertEquals(all.get(0).getId(), 1);
        assertEquals(all.get(1).getId(), 2);
        assertEquals(all.get(2).getId(), 3);
        assertEquals(all.get(3).getId(), 4);
        assertEquals(all.get(4).getId(), 5);
        assertEquals(all.get(5).getId(), 6);
        assertEquals(all.get(6).getId(), 7);
        assertEquals(all.get(7).getId(), 8);
        assertEquals(all.get(8).getId(), 9);
        assertEquals(all.get(9).getId(), 10);
        assertEquals(all.get(10).getId(), 11);
        assertEquals(all.get(11).getId(), 12);
        assertEquals(all.get(12).getId(), 13);
        assertEquals(all.get(13).getId(), 14);
        assertEquals(all.get(14).getId(), 15);
        assertEquals(all.get(0).getName(), "драма");
        assertEquals(all.get(1).getName(), "криминал");
        assertEquals(all.get(2).getName(), "фэнтези");
        assertEquals(all.get(3).getName(), "детектив");
        assertEquals(all.get(4).getName(), "мелодрама");
        assertEquals(all.get(5).getName(), "биография");
        assertEquals(all.get(6).getName(), "комедия");
        assertEquals(all.get(7).getName(), "фантастика");
        assertEquals(all.get(8).getName(), "боевик");
        assertEquals(all.get(9).getName(), "триллер");
        assertEquals(all.get(10).getName(), "приключения");
        assertEquals(all.get(11).getName(), "аниме");
        assertEquals(all.get(12).getName(), "мультфильм");
        assertEquals(all.get(13).getName(), "семейный");
        assertEquals(all.get(14).getName(), "вестерн");
    }


    @Test
    void findAllReturnException() throws SQLException {
        DataSource datasource = Mockito.mock(DataSource.class);
        Mockito.when(datasource.getConnection()).thenThrow(new SQLException());
        GenreDao genreDao = new JdbcGenreDao(datasource);

        assertThrows(SQLException.class, genreDao::findAll);
    }

    @AfterAll
    public void afterAll() throws IOException, SQLException {
        JdbcDaoTestUtil.dropTables();
    }
}