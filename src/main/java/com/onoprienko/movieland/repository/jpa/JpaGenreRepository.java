package com.onoprienko.movieland.repository.jpa;

import com.onoprienko.movieland.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface JpaGenreRepository extends JpaRepository<Genre, Long> {
    List<Genre> findByIdIn(Collection<Long> ids);

    @Query("select g from Genre g where g.movie.id = ?1")
    List<Genre> findByMovieId(Long id);
}
