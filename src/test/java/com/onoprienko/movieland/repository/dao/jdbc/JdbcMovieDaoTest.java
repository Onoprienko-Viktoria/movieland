package com.onoprienko.movieland.repository.dao.jdbc;

import com.onoprienko.movieland.entity.Movie;
import com.onoprienko.movieland.repository.dao.MovieDao;
import com.onoprienko.movieland.repository.dao.jdbc.utils.JdbcDaoTestUtil;
import com.onoprienko.movieland.service.entity.PageRequest;
import com.onoprienko.movieland.service.entity.SortEnum;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.postgresql.util.PSQLException;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcMovieDaoTest {
    private DataSource datasource;
    private MovieDao movieDao;

    @BeforeAll
    public void init() throws IOException, SQLException {
        JdbcDaoTestUtil.createTables();
        datasource = JdbcDaoTestUtil.getDatasource();
        movieDao = new JdbcMovieDao(datasource);
    }

    @Test
    void findAllReturnCorrectListOfMovieForPageZero() {
        List<Movie> all = movieDao.findAll(PageRequest.builder().page(0).capacity(3).build());

        assertNotNull(all);

        assertEquals(all.size(), 3);

        assertEquals(all.get(0).getId(), 1);
        assertEquals(all.get(1).getId(), 2);
        assertEquals(all.get(2).getId(), 3);

        assertEquals(all.get(0).getRating(), 8.9);
        assertEquals(all.get(1).getRating(), 8.9);
        assertEquals(all.get(2).getRating(), 8.6);

        assertEquals(all.get(0).getPrice(), 123.45);
        assertEquals(all.get(1).getPrice(), 134.67);
        assertEquals(all.get(2).getPrice(), 200.60);

        assertEquals(all.get(0).getYearOfRelease(), 1994);
        assertEquals(all.get(1).getYearOfRelease(), 1999);
        assertEquals(all.get(2).getYearOfRelease(), 1994);

        assertNull(all.get(0).getPicturePath());
        assertNull(all.get(1).getPicturePath());
        assertNull(all.get(2).getPicturePath());

        assertEquals(all.get(0).getNameRussian(), "Побег из Шоушенка");
        assertEquals(all.get(1).getNameRussian(), "Зеленая миля");
        assertEquals(all.get(2).getNameRussian(), "Форрест Гамп");

        assertEquals(all.get(0).getNameNative(), "The Shawshank Redemption");
        assertEquals(all.get(1).getNameNative(), "The Green Mile");
        assertEquals(all.get(2).getNameNative(), "Forrest Gump");
    }

    @Test
    void findAllReturnCorrectListOfMovieForPageThatVoid() {
        List<Movie> all = movieDao.findAll(PageRequest.builder().page(100000).capacity(3).build());

        assertNotNull(all);
        assertTrue(all.isEmpty());
    }

    @Test
    void findAllReturnCorrectListOfMovieForCapacityZero() {
        List<Movie> all = movieDao.findAll(PageRequest.builder().page(1).capacity(0).build());

        assertNotNull(all);
        assertTrue(all.isEmpty());
    }

    @Test
    void findAllReturnCorrectListOfMovieForNullPage() {
        assertThrows(NullPointerException.class, () -> movieDao.findAll(
                PageRequest.builder().page(null).capacity(3).build()));
    }

    @Test
    void findAllReturnCorrectListOfMovieForNullCapacity() {
        assertThrows(NullPointerException.class, () -> movieDao.findAll(
                PageRequest.builder().page(5).capacity(null).build()));
    }


    @Test
    void findAllReturnCorrectListOfMovieForNullRequest() {
        assertThrows(NullPointerException.class, () -> movieDao.findAll(null));
    }


    @Test
    void findAllByRatingReturnCorrectValues() {
        List<Movie> all = movieDao.findAllByRating(
                PageRequest.builder().page(0).capacity(3).build());


        assertNotNull(all);

        assertEquals(all.size(), 3);

        assertEquals(all.get(0).getId(), 2);
        assertEquals(all.get(1).getId(), 1);
        assertEquals(all.get(2).getId(), 4);

        assertEquals(all.get(0).getRating(), 8.9);
        assertEquals(all.get(1).getRating(), 8.9);
        assertEquals(all.get(2).getRating(), 8.7);

        assertEquals(all.get(0).getPrice(), 134.67);
        assertEquals(all.get(1).getPrice(), 123.45);
        assertEquals(all.get(2).getPrice(), 150.5);

        assertEquals(all.get(0).getYearOfRelease(), 1999);
        assertEquals(all.get(1).getYearOfRelease(), 1994);
        assertEquals(all.get(2).getYearOfRelease(), 1993);

        assertNull(all.get(0).getPicturePath());
        assertNull(all.get(1).getPicturePath());
        assertNull(all.get(2).getPicturePath());

        assertEquals(all.get(0).getNameRussian(), "Зеленая миля");
        assertEquals(all.get(1).getNameRussian(), "Побег из Шоушенка");
        assertEquals(all.get(2).getNameRussian(), "Список Шиндлера");

        assertEquals(all.get(0).getNameNative(), "The Green Mile");
        assertEquals(all.get(1).getNameNative(), "The Shawshank Redemption");
        assertEquals(all.get(2).getNameNative(), "Schindler's List");
    }


    @Test
    void findAllByRatingReturnExceptionOnNullRequest() {
        assertThrows(NullPointerException.class, () -> movieDao.findAllByRating(null));
    }

    @Test
    void findAllByRatingReturnExceptionOnNullCapacity() {
        assertThrows(NullPointerException.class, () -> movieDao.findAllByRating(PageRequest.builder().page(2).build()));
    }

    @Test
    void findAllByRatingReturnExceptionOnNullPage() {
        assertThrows(NullPointerException.class, () -> movieDao.findAllByRating(PageRequest.builder().capacity(2).build()));
    }


    @Test
    void findAllByRatingReturnVoidList() {
        List<Movie> all = movieDao.findAllByRating(
                PageRequest.builder().page(0).capacity(0).build());


        assertNotNull(all);

        assertTrue(all.isEmpty());
    }


    @Test
    void findAllByPriceDescReturnCorrectList() {
        List<Movie> all = movieDao.findAllByPrice(PageRequest.builder()
                .page(0).capacity(3).build(), SortEnum.DESC);


        assertNotNull(all);

        assertEquals(all.size(), 3);


        assertEquals(all.get(0).getId(), 3);
        assertEquals(all.get(1).getId(), 17);
        assertEquals(all.get(2).getId(), 9);

        assertEquals(all.get(0).getRating(), 8.6);
        assertEquals(all.get(1).getRating(), 8.5);
        assertEquals(all.get(2).getRating(), 8.1);

        assertEquals(all.get(0).getPrice(), 200.60);
        assertEquals(all.get(1).getPrice(), 199.99);
        assertEquals(all.get(2).getPrice(), 198.98);

        assertEquals(all.get(0).getYearOfRelease(), 1994);
        assertEquals(all.get(1).getYearOfRelease(), 2008);
        assertEquals(all.get(2).getYearOfRelease(), 1977);

        assertNull(all.get(0).getPicturePath());
        assertNull(all.get(1).getPicturePath());
        assertNull(all.get(2).getPicturePath());

        assertEquals(all.get(0).getNameRussian(), "Форрест Гамп");
        assertEquals(all.get(1).getNameRussian(), "Темный рыцарь");
        assertEquals(all.get(2).getNameRussian(), "Звёздные войны: Эпизод 4 – Новая надежда");

        assertEquals(all.get(0).getNameNative(), "Forrest Gump");
        assertEquals(all.get(1).getNameNative(), "The Dark Knight");
        assertEquals(all.get(2).getNameNative(), "Star Wars");
    }


    @Test
    void findAllByPriceAscReturnCorrectList() {
        List<Movie> all = movieDao.findAllByPrice(PageRequest.builder()
                .page(0).capacity(3).build(), SortEnum.ASC);


        assertNotNull(all);

        assertEquals(all.size(), 3);


        assertEquals(all.get(0).getId(), 23);
        assertEquals(all.get(1).getId(), 20);
        assertEquals(all.get(2).getId(), 8);

        assertEquals(all.get(0).getRating(), 7.6);
        assertEquals(all.get(1).getRating(), 8.1);
        assertEquals(all.get(2).getRating(), 8.4);

        assertEquals(all.get(0).getPrice(), 100.0);
        assertEquals(all.get(1).getPrice(), 100.5);
        assertEquals(all.get(2).getPrice(), 119.99);

        assertEquals(all.get(0).getYearOfRelease(), 1976);
        assertEquals(all.get(1).getYearOfRelease(), 1994);
        assertEquals(all.get(2).getYearOfRelease(), 1999);

        assertNull(all.get(0).getPicturePath());
        assertNull(all.get(1).getPicturePath());
        assertNull(all.get(2).getPicturePath());

        assertEquals(all.get(0).getNameRussian(), "Блеф");
        assertEquals(all.get(1).getNameRussian(), "Гран Торино");
        assertEquals(all.get(2).getNameRussian(), "Бойцовский клуб");

        assertEquals(all.get(0).getNameNative(), "Bluff storia di truffe e di imbroglioni");
        assertEquals(all.get(1).getNameNative(), "Gran Torino");
        assertEquals(all.get(2).getNameNative(), "Fight Club");
    }


    @Test
    void findAllByPriceReturnVoidList() {
        List<Movie> all = movieDao.findAllByPrice(PageRequest.builder()
                .page(0).capacity(0).build(), SortEnum.ASC);


        assertNotNull(all);

        assertEquals(all.size(), 0);
    }


    @Test
    void findAllByPriceReturnExceptionOnNullPage() {
        assertThrows(NullPointerException.class, () -> movieDao.findAllByPrice(PageRequest.builder()
                .page(null).capacity(0).build(), SortEnum.ASC));
    }

    @Test
    void findAllByPriceReturnExceptionOnNullCapacity() {
        assertThrows(NullPointerException.class, () -> movieDao.findAllByPrice(PageRequest.builder()
                .page(2).capacity(null).build(), SortEnum.ASC));
    }

    @Test
    void findAllByPriceReturnExceptionOnNullRequest() {
        assertThrows(NullPointerException.class, () -> movieDao.findAllByPrice(null, SortEnum.ASC));
    }

    @Test
    void findAllByPriceReturnExceptionOnNullSort() {
        assertThrows(NullPointerException.class, () -> movieDao.findAllByPrice(PageRequest.builder()
                .page(1).capacity(2).build(), null));
    }


    @Test
    void findAllByPriceReturnExceptionOnNoneSort() {
        assertThrows(PSQLException.class, () -> movieDao.findAllByPrice(PageRequest.builder()
                .page(1).capacity(2).build(), SortEnum.NONE));
    }

    @Test
    void findAllByGenreDramaReturnCorrectList() {
        List<Movie> all = movieDao.findAllByGenre(1, PageRequest.builder().page(0).capacity(3).build());

        assertNotNull(all);

        assertEquals(all.size(), 3);

        assertEquals(all.get(0).getId(), 1);
        assertEquals(all.get(1).getId(), 2);
        assertEquals(all.get(2).getId(), 3);

        assertEquals(all.get(0).getRating(), 8.9);
        assertEquals(all.get(1).getRating(), 8.9);
        assertEquals(all.get(2).getRating(), 8.6);

        assertEquals(all.get(0).getPrice(), 123.45);
        assertEquals(all.get(1).getPrice(), 134.67);
        assertEquals(all.get(2).getPrice(), 200.60);

        assertEquals(all.get(0).getYearOfRelease(), 1994);
        assertEquals(all.get(1).getYearOfRelease(), 1999);
        assertEquals(all.get(2).getYearOfRelease(), 1994);

        assertNull(all.get(0).getPicturePath());
        assertNull(all.get(1).getPicturePath());
        assertNull(all.get(2).getPicturePath());

        assertEquals(all.get(0).getNameRussian(), "Побег из Шоушенка");
        assertEquals(all.get(1).getNameRussian(), "Зеленая миля");
        assertEquals(all.get(2).getNameRussian(), "Форрест Гамп");

        assertEquals(all.get(0).getNameNative(), "The Shawshank Redemption");
        assertEquals(all.get(1).getNameNative(), "The Green Mile");
        assertEquals(all.get(2).getNameNative(), "Forrest Gump");
    }


    @Test
    void findAllByGenreWesternReturnCorrectList() {
        List<Movie> all = movieDao.findAllByGenre(15, PageRequest.builder().page(0).capacity(3).build());

        assertNotNull(all);

        assertEquals(all.size(), 3);

        assertEquals(all.get(0).getId(), 21);
        assertEquals(all.get(1).getId(), 24);
        assertEquals(all.get(2).getId(), 25);

        assertEquals(all.get(0).getRating(), 8.5);
        assertEquals(all.get(1).getRating(), 8.5);
        assertEquals(all.get(2).getRating(), 8.0);

        assertEquals(all.get(0).getPrice(), 130.00);
        assertEquals(all.get(1).getPrice(), 170.00);
        assertEquals(all.get(2).getPrice(), 120.55);

        assertEquals(all.get(0).getYearOfRelease(), 1979);
        assertEquals(all.get(1).getYearOfRelease(), 2012);
        assertEquals(all.get(2).getYearOfRelease(), 1990);

        assertNull(all.get(0).getPicturePath());
        assertNull(all.get(1).getPicturePath());
        assertNull(all.get(2).getPicturePath());

        assertEquals(all.get(0).getNameRussian(), "Хороший, плохой, злой");
        assertEquals(all.get(1).getNameRussian(), "Джанго освобожденный");
        assertEquals(all.get(2).getNameRussian(), "Танцующий с волками");

        assertEquals(all.get(0).getNameNative(), "Il buono, il brutto, il cattivo");
        assertEquals(all.get(1).getNameNative(), "Django Unchained");
        assertEquals(all.get(2).getNameNative(), "Dances with Wolves");
    }


    @Test
    void findAllByGenreThatDontExist() {
        List<Movie> all = movieDao.findAllByGenre(173, PageRequest.builder().page(0).capacity(3).build());

        assertNotNull(all);
        assertTrue(all.isEmpty());
    }


    @Test
    void findAllByGenreThrowExceptionIfGenreNull() {
        assertThrows(NullPointerException.class, () ->
                movieDao.findAllByGenre(null, PageRequest.builder().page(0).capacity(3).build()));
    }


    @Test
    void findAllByGenreReturnVoidListIfGenreIdMinusInt() {
        List<Movie> all = movieDao.findAllByGenre(-1, PageRequest.builder().page(0).capacity(3).build());

        assertNotNull(all);
        assertTrue(all.isEmpty());
    }


    @Test
    void findAllByGenreThrowExceptionIfPageNull() {
        assertThrows(NullPointerException.class, () ->
                movieDao.findAllByGenre(12, PageRequest.builder().page(null).capacity(3).build()));
    }


    @Test
    void findAllByGenreThrowExceptionIfCapacityNull() {
        assertThrows(NullPointerException.class, () ->
                movieDao.findAllByGenre(12, PageRequest.builder().page(1).capacity(null).build()));
    }

    @Test
    void findAllByGenreThrowExceptionIfRequestNull() {
        assertThrows(NullPointerException.class, () ->
                movieDao.findAllByGenre(12, PageRequest.builder().page(1).capacity(null).build()));
    }

    @Test
    void findAllByGenreReturnVoidList() {
        List<Movie> all = movieDao.findAllByGenre(12, PageRequest.builder().page(3).capacity(10).build());

        assertNotNull(all);
        assertTrue(all.isEmpty());
    }

    @Test
    void getCountReturnCorrectCount() {
        MovieDao movieDao = new JdbcMovieDao(datasource);

        Integer count = movieDao.getCount();

        assertNotNull(count);

        assertEquals(count, 25);
    }

    @Test
    void getCountReturnException() throws SQLException {
        DataSource datasource = Mockito.mock(DataSource.class);
        Mockito.when(datasource.getConnection()).thenThrow(new SQLException());
        MovieDao movieDaoMock = new JdbcMovieDao(datasource);

        assertThrows(SQLException.class, movieDaoMock::getCount);
    }

    @AfterAll
    public void afterAll() throws IOException, SQLException {
        JdbcDaoTestUtil.dropTables();
    }
}