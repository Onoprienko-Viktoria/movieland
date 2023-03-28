package com.onoprienko.movieland.repository.jpa;

import com.onoprienko.movieland.entity.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaMovieRepository extends JpaRepository<Movie, Long> {
    @Query("select movie from Movie movie inner join movie.genres genre where genre.id = ?1")
    List<Movie> findByGenreId(Long id, Pageable pageable);

    @Query(value = "SELECT * FROM movie ORDER BY RANDOM() LIMIT 3", nativeQuery = true)
    List<Movie> findRandomMovies();

}
