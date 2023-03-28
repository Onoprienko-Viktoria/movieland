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
    void findAllByPriceDescReturnCorrectList() {
        List<Movie> all = repository.findAll(PageRequest.of(0, 3,
                        Sort.by(SortTypeEnum.PRICE.name().toLowerCase()).descending()))
                .getContent();
        ;


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
        List<Movie> all = repository.findAll(PageRequest.of(0, 3,
                        Sort.by(SortTypeEnum.PRICE.name().toLowerCase()).ascending()))
                .getContent();


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
    void findAllByGenreDramaReturnCorrectList() {
        List<Movie> all = repository.findByGenreId(1L,
                PageRequest.of(0, 3));

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
        List<Movie> all = repository.findByGenreId(15L,
                PageRequest.of(0, 3));

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