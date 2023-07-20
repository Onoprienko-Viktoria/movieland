package com.onoprienko.movieland.repository.jpa;

import com.onoprienko.movieland.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaGenreRepository extends JpaRepository<Genre, Long> {
    @Query("select g from Genre g inner join g.movies movies where movies.id = ?1")
    List<Genre> findAllByMovieId(Long id);
}
