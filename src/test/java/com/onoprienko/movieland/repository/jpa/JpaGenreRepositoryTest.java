package com.onoprienko.movieland.repository.jpa;

import com.onoprienko.movieland.entity.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Testcontainers
@Sql(value = {"/create-tables-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-tables-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class JpaGenreRepositoryTest {
    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest");

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

    @Autowired
    private JpaGenreRepository genreRepository;

    @Test
    void findAll() {
        List<Genre> all = genreRepository.findAll();

        assertNotNull(all);

        assertEquals(all.size(), 15);

        assertEquals(1, all.get(0).getId());
        assertEquals(2, all.get(1).getId());
        assertEquals(3, all.get(2).getId());
        assertEquals(4, all.get(3).getId());
        assertEquals(5, all.get(4).getId());
        assertEquals(6, all.get(5).getId());
        assertEquals(7, all.get(6).getId());
        assertEquals(8, all.get(7).getId());
        assertEquals(9, all.get(8).getId());
        assertEquals(10, all.get(9).getId());
        assertEquals(11, all.get(10).getId());
        assertEquals(12, all.get(11).getId());
        assertEquals(13, all.get(12).getId());
        assertEquals(14, all.get(13).getId());
        assertEquals(15, all.get(14).getId());
        assertEquals("драма", all.get(0).getName());
        assertEquals("криминал", all.get(1).getName());
        assertEquals("фэнтези", all.get(2).getName());
        assertEquals("детектив", all.get(3).getName());
        assertEquals("мелодрама", all.get(4).getName());
        assertEquals("биография", all.get(5).getName());
        assertEquals("комедия", all.get(6).getName());
        assertEquals("фантастика", all.get(7).getName());
        assertEquals("боевик", all.get(8).getName());
        assertEquals("триллер", all.get(9).getName());
        assertEquals("приключения", all.get(10).getName());
        assertEquals("аниме", all.get(11).getName());
        assertEquals("мультфильм", all.get(12).getName());
        assertEquals("семейный", all.get(13).getName());
        assertEquals("вестерн", all.get(14).getName());
    }
}