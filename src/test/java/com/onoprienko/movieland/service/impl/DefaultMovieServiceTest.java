package com.onoprienko.movieland.service.impl;

import com.onoprienko.movieland.common.MoviesRequest;
import com.onoprienko.movieland.common.SortDirectionEnum;
import com.onoprienko.movieland.dto.MovieDto;
import com.onoprienko.movieland.entity.Movie;
import com.onoprienko.movieland.mapper.MovieMapper;
import com.onoprienko.movieland.repository.jpa.JpaMovieRepository;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DefaultMovieServiceTest {
    Movie testMovieOne = Movie.builder()
            .id(1L)
            .price(55.8)
            .rating(7.3)
            .nameRussian("Тест один")
            .nameNative("Test one")
            .yearOfRelease(1900)
            .picturePath("path")
            .build();
    Movie testMovieTwo = Movie.builder().id(2L)
            .price(111.1)
            .rating(9.8)
            .nameRussian("Тест два")
            .nameNative("Test two")
            .yearOfRelease(2000)
            .picturePath("path")
            .build();

    List<Movie> movies = List.of(testMovieOne, testMovieTwo);


    JpaMovieRepository repository = Mockito.mock(JpaMovieRepository.class);
    DefaultMovieService movieService = new DefaultMovieService(repository, Mappers.getMapper(MovieMapper.class));


    @Test
    void findAllReturnList() {
        Mockito.when(
                repository.findAll(Mockito.any(Pageable.class))).thenReturn(new PageImpl<>(movies));
        MoviesRequest moviesRequest = MoviesRequest.builder()
                .page(1).build();
        movieService.setDefaultPageSize(10);

        List<MovieDto> all = movieService.findAll(moviesRequest);

        assertNotNull(all);
        assertEquals(all.size(), 2);

        MovieDto movieOne = all.get(0);
        MovieDto movieTwo = all.get(1);


        assertEquals(1, movieOne.getId());
        assertEquals(55.8, movieOne.getPrice());
        assertEquals(7.3, movieOne.getRating());
        assertEquals("Test one", movieOne.getNameNative());
        assertEquals("Тест один", movieOne.getNameRussian());
        assertEquals(1900, movieOne.getYearOfRelease());
        assertEquals("path", movieOne.getPicturePath());

        assertEquals(2, movieTwo.getId());
        assertEquals(111.1, movieTwo.getPrice());
        assertEquals(9.8, movieTwo.getRating());
        assertEquals("Test two", movieTwo.getNameNative());
        assertEquals("Тест два", movieTwo.getNameRussian());
        assertEquals(2000, movieTwo.getYearOfRelease());
        assertEquals("path", movieTwo.getPicturePath());

        Mockito.verify(repository, Mockito.times(1))
                .findAll(Mockito.any(Pageable.class));
    }


    @Test
    void findAllWithRatingAscReturnList() {
        Mockito.when(
                repository.findAll(
                        Mockito.any(Pageable.class)
                )
        ).thenReturn(new PageImpl<>(List.of(testMovieTwo, testMovieOne)));
        MoviesRequest moviesRequest = MoviesRequest.builder()
                .page(1)
                .ratingDirection(SortDirectionEnum.ASC)
                .build();
        movieService.setDefaultPageSize(10);


        List<MovieDto> all = movieService.findAll(moviesRequest);

        assertNotNull(all);
        assertEquals(all.size(), 2);

        MovieDto movieOne = all.get(1);
        MovieDto movieTwo = all.get(0);


        assertEquals(1, movieOne.getId());
        assertEquals(55.8, movieOne.getPrice());
        assertEquals(7.3, movieOne.getRating());
        assertEquals("Test one", movieOne.getNameNative());
        assertEquals("Тест один", movieOne.getNameRussian());
        assertEquals(1900, movieOne.getYearOfRelease());
        assertEquals("path", movieOne.getPicturePath());

        assertEquals(2, movieTwo.getId());
        assertEquals(111.1, movieTwo.getPrice());
        assertEquals(9.8, movieTwo.getRating());
        assertEquals("Test two", movieTwo.getNameNative());
        assertEquals("Тест два", movieTwo.getNameRussian());
        assertEquals(2000, movieTwo.getYearOfRelease());
        assertEquals("path", movieTwo.getPicturePath());

        Mockito.verify(repository, Mockito.times(1))
                .findAll(Mockito.any(Pageable.class));
    }


    @Test
    void findAllWithPriceAscReturnList() {
        Mockito.when(
                repository.findAll(
                        Mockito.any(Pageable.class)
                )
        ).thenReturn(new PageImpl<>(movies));
        MoviesRequest moviesRequest = MoviesRequest.builder()
                .page(1)
                .priceDirection(SortDirectionEnum.ASC)
                .build();
        movieService.setDefaultPageSize(10);
        List<MovieDto> all = movieService.findAll(moviesRequest);

        assertNotNull(all);
        assertEquals(all.size(), 2);

        MovieDto movieOne = all.get(0);
        MovieDto movieTwo = all.get(1);


        assertEquals(1, movieOne.getId());
        assertEquals(55.8, movieOne.getPrice());
        assertEquals(7.3, movieOne.getRating());
        assertEquals("Test one", movieOne.getNameNative());
        assertEquals("Тест один", movieOne.getNameRussian());
        assertEquals(1900, movieOne.getYearOfRelease());
        assertEquals("path", movieOne.getPicturePath());

        assertEquals(2, movieTwo.getId());
        assertEquals(111.1, movieTwo.getPrice());
        assertEquals(9.8, movieTwo.getRating());
        assertEquals("Test two", movieTwo.getNameNative());
        assertEquals("Тест два", movieTwo.getNameRussian());
        assertEquals(2000, movieTwo.getYearOfRelease());
        assertEquals("path", movieTwo.getPicturePath());

        Mockito.verify(repository, Mockito.times(1))
                .findAll(Mockito.any(Pageable.class));
    }

    @Test
    void findAllWithPriceDescReturnList() {
        Mockito.when(
                repository.findAll(
                        Mockito.any(Pageable.class)
                )
        ).thenReturn(new PageImpl<>(List.of(testMovieTwo, testMovieOne)));
        MoviesRequest moviesRequest = MoviesRequest.builder()
                .page(1)
                .priceDirection(SortDirectionEnum.DESC)
                .build();
        movieService.setDefaultPageSize(10);

        List<MovieDto> all = movieService.findAll(moviesRequest);

        assertNotNull(all);
        assertEquals(all.size(), 2);

        MovieDto movieOne = all.get(1);
        MovieDto movieTwo = all.get(0);


        assertEquals(1, movieOne.getId());
        assertEquals(55.8, movieOne.getPrice());
        assertEquals(7.3, movieOne.getRating());
        assertEquals("Test one", movieOne.getNameNative());
        assertEquals("Тест один", movieOne.getNameRussian());
        assertEquals(1900, movieOne.getYearOfRelease());
        assertEquals("path", movieOne.getPicturePath());

        assertEquals(2, movieTwo.getId());
        assertEquals(111.1, movieTwo.getPrice());
        assertEquals(9.8, movieTwo.getRating());
        assertEquals("Test two", movieTwo.getNameNative());
        assertEquals("Тест два", movieTwo.getNameRussian());
        assertEquals(2000, movieTwo.getYearOfRelease());
        assertEquals("path", movieTwo.getPicturePath());

        Mockito.verify(repository, Mockito.times(1))
                .findAll(Mockito.any(Pageable.class));
    }


    @Test
    void findAllWithPriceAndRatingSortWillReturnRatingSortList() {
        Mockito.when(
                repository.findAll(
                        Mockito.any(Pageable.class)
                )
        ).thenReturn(new PageImpl<>(movies));
        MoviesRequest moviesRequest = MoviesRequest.builder()
                .page(1)
                .priceDirection(SortDirectionEnum.DESC)
                .ratingDirection(SortDirectionEnum.ASC)
                .build();
        movieService.setDefaultPageSize(10);

        List<MovieDto> all = movieService.findAll(moviesRequest);

        assertNotNull(all);
        assertEquals(all.size(), 2);

        MovieDto movieOne = all.get(0);
        MovieDto movieTwo = all.get(1);


        assertEquals(1, movieOne.getId());
        assertEquals(55.8, movieOne.getPrice());
        assertEquals(7.3, movieOne.getRating());
        assertEquals("Test one", movieOne.getNameNative());
        assertEquals("Тест один", movieOne.getNameRussian());
        assertEquals(1900, movieOne.getYearOfRelease());
        assertEquals("path", movieOne.getPicturePath());

        assertEquals(2, movieTwo.getId());
        assertEquals(111.1, movieTwo.getPrice());
        assertEquals(9.8, movieTwo.getRating());
        assertEquals("Test two", movieTwo.getNameNative());
        assertEquals("Тест два", movieTwo.getNameRussian());
        assertEquals(2000, movieTwo.getYearOfRelease());
        assertEquals("path", movieTwo.getPicturePath());

        Mockito.verify(repository, Mockito.times(1))
                .findAll(Mockito.any(Pageable.class));
    }

    @Test
    void findAllReturnEmptyList() {
        Mockito.when(
                repository.findAll(
                        Mockito.any(Pageable.class)
                )
        ).thenReturn(new PageImpl<>(new ArrayList<>()));
        MoviesRequest moviesRequest = MoviesRequest.builder()
                .page(1)
                .build();
        movieService.setDefaultPageSize(10);
        List<MovieDto> all = movieService.findAll(moviesRequest);

        assertNotNull(all);
        assertEquals(all.size(), 0);

        Mockito.verify(repository, Mockito.times(1))
                .findAll(Mockito.any(Pageable.class));
    }

    @Test
    void findAllReturnException() {
        Mockito.when(
                repository.findAll(
                        Mockito.any(Pageable.class)
                )
        ).thenThrow(new RuntimeException());
        MoviesRequest moviesRequest = MoviesRequest.builder()
                .page(1)
                .build();
        movieService.setDefaultPageSize(10);

        assertThrows(RuntimeException.class, () -> movieService.findAll(moviesRequest));

        Mockito.verify(repository, Mockito.times(1))
                .findAll(Mockito.any(Pageable.class));
    }

    @Test
    void findAllByGenreReturnListOfMovies() {
        Mockito.when(
                repository.findByGenreId(Mockito.anyLong(),
                        Mockito.any(Pageable.class)
                )
        ).thenReturn(movies);
        MoviesRequest moviesRequest = MoviesRequest.builder()
                .page(1)
                .genreId(1)
                .build();
        movieService.setDefaultPageSize(10);

        List<MovieDto> all = movieService.findByGenre(moviesRequest);

        assertNotNull(all);
        assertEquals(all.size(), 2);

        MovieDto movieOne = all.get(0);
        MovieDto movieTwo = all.get(1);


        assertEquals(1, movieOne.getId());
        assertEquals(55.8, movieOne.getPrice());
        assertEquals(7.3, movieOne.getRating());
        assertEquals("Test one", movieOne.getNameNative());
        assertEquals("Тест один", movieOne.getNameRussian());
        assertEquals(1900, movieOne.getYearOfRelease());
        assertEquals("path", movieOne.getPicturePath());

        assertEquals(2, movieTwo.getId());
        assertEquals(111.1, movieTwo.getPrice());
        assertEquals(9.8, movieTwo.getRating());
        assertEquals("Test two", movieTwo.getNameNative());
        assertEquals("Тест два", movieTwo.getNameRussian());
        assertEquals(2000, movieTwo.getYearOfRelease());
        assertEquals("path", movieTwo.getPicturePath());

        Mockito.verify(repository, Mockito.times(1))
                .findByGenreId(Mockito.anyLong(), Mockito.any(Pageable.class));
    }


    @Test
    void findAllByGenreReturnVoidList() {
        Mockito.when(
                repository.findByGenreId(Mockito.anyLong(),
                        Mockito.any(Pageable.class)
                )
        ).thenReturn(new ArrayList<>());
        MoviesRequest moviesRequest = MoviesRequest.builder()
                .page(133)
                .genreId(1)
                .build();
        movieService.setDefaultPageSize(10);
        List<MovieDto> all = movieService.findByGenre(moviesRequest);

        assertNotNull(all);
        assertEquals(all.size(), 0);

        Mockito.verify(repository, Mockito.times(1))
                .findByGenreId(Mockito.anyLong(), Mockito.any(Pageable.class));
    }

    @Test
    void findAllByGenreReturnException() {
        Mockito.when(
                repository.findByGenreId(Mockito.anyLong(),
                        Mockito.any(Pageable.class)
                )
        ).thenThrow(new RuntimeException());
        MoviesRequest moviesRequest = MoviesRequest.builder()
                .page(133)
                .genreId(1)
                .build();
        movieService.setDefaultPageSize(10);
        assertThrows(RuntimeException.class, () -> movieService.findByGenre(moviesRequest));

        Mockito.verify(repository, Mockito.times(1))
                .findByGenreId(Mockito.anyLong(), Mockito.any(Pageable.class));
    }


    @Test
    void findRandomWithSameValuesReturnCorrectResult() {
        Mockito.when(
                repository.findRandomMovies()
        ).thenReturn(List.of(testMovieOne, testMovieOne, testMovieOne));

        List<MovieDto> random = movieService.findRandom();
        assertNotNull(random);
        assertEquals(random.size(), 3);
        MovieDto movieOne = random.get(0);

        assertEquals(1, movieOne.getId());
        assertEquals(55.8, movieOne.getPrice());
        assertEquals(7.3, movieOne.getRating());
        assertEquals("Test one", movieOne.getNameNative());
        assertEquals("Тест один", movieOne.getNameRussian());
        assertEquals(1900, movieOne.getYearOfRelease());
        assertEquals("path", movieOne.getPicturePath());

        MovieDto movieTwo = random.get(1);

        assertEquals(1, movieOne.getId());
        assertEquals(55.8, movieOne.getPrice());
        assertEquals(7.3, movieOne.getRating());
        assertEquals("Test one", movieOne.getNameNative());
        assertEquals("Тест один", movieOne.getNameRussian());
        assertEquals(1900, movieOne.getYearOfRelease());
        assertEquals("path", movieOne.getPicturePath());
    }
}