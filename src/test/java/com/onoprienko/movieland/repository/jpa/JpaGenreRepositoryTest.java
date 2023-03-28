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
}