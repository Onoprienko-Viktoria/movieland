package com.onoprienko.movieland.repository.jpa;

import com.onoprienko.movieland.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaReviewRepository extends JpaRepository<Review, Long> {
    @Query("select r from Review r where r.movie.id = ?1")
    List<Review> findByMovieId(Long id);
}
