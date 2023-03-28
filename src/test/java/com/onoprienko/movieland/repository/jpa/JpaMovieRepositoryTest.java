package com.onoprienko.movieland.repository.jpa;

import com.onoprienko.movieland.common.SortTypeEnum;
import com.onoprienko.movieland.entity.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Testcontainers
@Sql(value = {"/create-tables-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-tables-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class JpaMovieRepositoryTest {
    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest");

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

    @Autowired
    private JpaMovieRepository repository;

    @Test
    void findAllReturnCorrectListOfMovieForPageZero() {
        List<Movie> all = repository.findAll(PageRequest.of(0, 3)).getContent();

        assertNotNull(all);

        assertEquals(all.size(), 3);

        assertEquals(1, all.get(0).getId());
        assertEquals(2, all.get(1).getId());
        assertEquals(3, all.get(2).getId());

        assertEquals(8.9, all.get(0).getRating());
        assertEquals(8.9, all.get(1).getRating());
        assertEquals(8.6, all.get(2).getRating());

        assertEquals(123.45, all.get(0).getPrice());
        assertEquals(134.67, all.get(1).getPrice());
        assertEquals(200.60, all.get(2).getPrice());

        assertEquals(1994, all.get(0).getYearOfRelease());
        assertEquals(1999, all.get(1).getYearOfRelease());
        assertEquals(1994, all.get(2).getYearOfRelease());

        assertNull(all.get(0).getPicturePath());
        assertNull(all.get(1).getPicturePath());
        assertNull(all.get(2).getPicturePath());

        assertEquals("Побег из Шоушенка", all.get(0).getNameRussian());
        assertEquals("Зеленая миля", all.get(1).getNameRussian());
        assertEquals("Форрест Гамп", all.get(2).getNameRussian());

        assertEquals("The Shawshank Redemption", all.get(0).getNameNative());
        assertEquals("The Green Mile", all.get(1).getNameNative());
        assertEquals("Forrest Gump", all.get(2).getNameNative());
    }

    @Test
    void findAllReturnCorrectListOfMovieForPageThatVoid() {
        List<Movie> all = repository.findAll(PageRequest.of(100000, 3)).getContent();

        assertNotNull(all);
        assertTrue(all.isEmpty());
    }

    @Test
    void findAllByRatingReturnCorrectValues() {
        List<Movie> all = repository.findAll(
                        PageRequest.of(0, 3,
                                Sort.by(SortTypeEnum.RATING.name().toLowerCase()).descending()))
                .getContent();


        assertNotNull(all);

        assertEquals(all.size(), 3);

        assertEquals(2, all.get(0).getId());
        assertEquals(1, all.get(1).getId());
        assertEquals(4, all.get(2).getId());

        assertEquals(8.9, all.get(0).getRating());
        assertEquals(8.9, all.get(1).getRating());
        assertEquals(8.7, all.get(2).getRating());

        assertEquals(134.67, all.get(0).getPrice());
        assertEquals(123.45, all.get(1).getPrice());
        assertEquals(150.5, all.get(2).getPrice());

        assertEquals(1999, all.get(0).getYearOfRelease());
        assertEquals(1994, all.get(1).getYearOfRelease());
        assertEquals(1993, all.get(2).getYearOfRelease());

        assertNull(all.get(0).getPicturePath());
        assertNull(all.get(1).getPicturePath());
        assertNull(all.get(2).getPicturePath());

        assertEquals("Зеленая миля", all.get(0).getNameRussian());
        assertEquals("Побег из Шоушенка", all.get(1).getNameRussian());
        assertEquals("Список Шиндлера", all.get(2).getNameRussian());

        assertEquals("The Green Mile", all.get(0).getNameNative());
        assertEquals("The Shawshank Redemption", all.get(1).getNameNative());
        assertEquals("Schindler's List", all.get(2).getNameNative());
    }


    @Test
    void findAllByPriceDescReturnCorrectList() {
        List<Movie> all = repository.findAll(PageRequest.of(0, 3,
                        Sort.by(SortTypeEnum.PRICE.name().toLowerCase()).descending()))
                .getContent();
        ;


        assertNotNull(all);

        assertEquals(all.size(), 3);

        assertEquals(3, all.get(0).getId());
        assertEquals(17, all.get(1).getId());
        assertEquals(9, all.get(2).getId());

        assertEquals(8.6, all.get(0).getRating());
        assertEquals(8.5, all.get(1).getRating());
        assertEquals(8.1, all.get(2).getRating());

        assertEquals(200.60, all.get(0).getPrice());
        assertEquals(199.99, all.get(1).getPrice());
        assertEquals(198.98, all.get(2).getPrice());

        assertEquals(1994, all.get(0).getYearOfRelease());
        assertEquals(2008, all.get(1).getYearOfRelease());
        assertEquals(1977, all.get(2).getYearOfRelease());

        assertNull(all.get(0).getPicturePath());
        assertNull(all.get(1).getPicturePath());
        assertNull(all.get(2).getPicturePath());
    }


    @Test
    void findAllByPriceAscReturnCorrectList() {
        List<Movie> all = repository.findAll(PageRequest.of(0, 3,
                        Sort.by(SortTypeEnum.PRICE.name().toLowerCase()).ascending()))
                .getContent();


        assertNotNull(all);

        assertEquals(all.size(), 3);


        assertEquals(23, all.get(0).getId());
        assertEquals(20, all.get(1).getId());
        assertEquals(8, all.get(2).getId());

        assertEquals(7.6, all.get(0).getRating());
        assertEquals(8.1, all.get(1).getRating());
        assertEquals(8.4, all.get(2).getRating());

        assertEquals(100.0, all.get(0).getPrice());
        assertEquals(100.5, all.get(1).getPrice());
        assertEquals(119.99, all.get(2).getPrice());

        assertEquals(1976, all.get(0).getYearOfRelease());
        assertEquals(1994, all.get(1).getYearOfRelease());
        assertEquals(1999, all.get(2).getYearOfRelease());

        assertNull(all.get(0).getPicturePath());
        assertNull(all.get(1).getPicturePath());
        assertNull(all.get(2).getPicturePath());

        assertEquals("Блеф", all.get(0).getNameRussian());
        assertEquals("Гран Торино", all.get(1).getNameRussian());
        assertEquals("Бойцовский клуб", all.get(2).getNameRussian());

        assertEquals("Bluff storia di truffe e di imbroglioni", all.get(0).getNameNative());
        assertEquals("Gran Torino", all.get(1).getNameNative());
        assertEquals("Fight Club", all.get(2).getNameNative());
    }


    @Test
    void findAllByGenreDramaReturnCorrectList() {
        List<Movie> all = repository.findByGenreId(1L,
                PageRequest.of(0, 3));

        assertNotNull(all);

        assertEquals(all.size(), 3);


        assertEquals(1, all.get(0).getId());
        assertEquals(2, all.get(1).getId());
        assertEquals(3, all.get(2).getId());

        assertEquals(8.9, all.get(0).getRating());
        assertEquals(8.9, all.get(1).getRating());
        assertEquals(8.6, all.get(2).getRating());

        assertEquals(123.45, all.get(0).getPrice());
        assertEquals(134.67, all.get(1).getPrice());
        assertEquals(200.60, all.get(2).getPrice());

        assertEquals(1994, all.get(0).getYearOfRelease());
        assertEquals(1999, all.get(1).getYearOfRelease());
        assertEquals(1994, all.get(2).getYearOfRelease());

        assertNull(all.get(0).getPicturePath());
        assertNull(all.get(1).getPicturePath());
        assertNull(all.get(2).getPicturePath());

        assertEquals("Побег из Шоушенка", all.get(0).getNameRussian());
        assertEquals("Зеленая миля", all.get(1).getNameRussian());
        assertEquals("Форрест Гамп", all.get(2).getNameRussian());

        assertEquals("The Shawshank Redemption", all.get(0).getNameNative());
        assertEquals("The Green Mile", all.get(1).getNameNative());
        assertEquals("Forrest Gump", all.get(2).getNameNative());
    }


    @Test
    void findAllByGenreWesternReturnCorrectList() {
        List<Movie> all = repository.findByGenreId(15L,
                PageRequest.of(0, 3));

        assertNotNull(all);

        assertEquals(all.size(), 3);

        assertEquals(21, all.get(0).getId());
        assertEquals(24, all.get(1).getId());
        assertEquals(25, all.get(2).getId());

        assertEquals(8.5, all.get(0).getRating());
        assertEquals(8.5, all.get(1).getRating());
        assertEquals(8.0, all.get(2).getRating());

        assertEquals(130.00, all.get(0).getPrice());
        assertEquals(170.00, all.get(1).getPrice());
        assertEquals(120.55, all.get(2).getPrice());

        assertEquals(1979, all.get(0).getYearOfRelease());
        assertEquals(2012, all.get(1).getYearOfRelease());
        assertEquals(1990, all.get(2).getYearOfRelease());

        assertNull(all.get(0).getPicturePath());
        assertNull(all.get(1).getPicturePath());
        assertNull(all.get(2).getPicturePath());

        assertEquals("Хороший, плохой, злой", all.get(0).getNameRussian());
        assertEquals("Джанго освобожденный", all.get(1).getNameRussian());
        assertEquals("Танцующий с волками", all.get(2).getNameRussian());

        assertEquals("Il buono, il brutto, il cattivo", all.get(0).getNameNative());
        assertEquals("Django Unchained", all.get(1).getNameNative());
        assertEquals("Dances with Wolves", all.get(2).getNameNative());
    }


    @Test
    void findAllByGenreThatDontExist() {
        List<Movie> all = repository.findByGenreId(1123L,
                PageRequest.of(0, 3));

        assertNotNull(all);
        assertTrue(all.isEmpty());
    }

    @Test
    void findAllByGenreReturnVoidListIfGenreIdMinusInt() {
        List<Movie> all = repository.findByGenreId(-1L,
                PageRequest.of(0, 3));

        assertNotNull(all);
        assertTrue(all.isEmpty());
    }

    @Test
    void findAllByGenreReturnVoidList() {
        List<Movie> all = repository.findByGenreId(12L,
                PageRequest.of(100, 3));

        assertNotNull(all);
        assertTrue(all.isEmpty());
    }
}