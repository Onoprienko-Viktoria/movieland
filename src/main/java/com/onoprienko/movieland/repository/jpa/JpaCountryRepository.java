package com.onoprienko.movieland.repository.jpa;

import com.onoprienko.movieland.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface JpaCountryRepository extends JpaRepository<Country, Integer> {
    @Query("select c from Country c inner join c.movies movies where movies.id = ?1")
    List<Country> findByMovieId(Long id);

    List<Country> findByIdIn(Collection<Integer> ids);
}
