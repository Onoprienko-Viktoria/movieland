package com.onoprienko.movieland.service.impl;

import com.onoprienko.movieland.common.request.AddReviewRequest;
import com.onoprienko.movieland.dto.ReviewDto;
import com.onoprienko.movieland.entity.Movie;
import com.onoprienko.movieland.entity.Review;
import com.onoprienko.movieland.mapper.ReviewMapper;
import com.onoprienko.movieland.repository.jpa.JpaMovieRepository;
import com.onoprienko.movieland.repository.jpa.JpaReviewRepository;
import com.onoprienko.movieland.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultReviewService implements ReviewService {
    private final JpaReviewRepository reviewRepository;
    private final JpaMovieRepository movieRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public void add(AddReviewRequest request) {
        Optional<Movie> optionalMovie = movieRepository.findById(request.getMovieId());
        if (optionalMovie.isEmpty()) {
            throw new IllegalArgumentException("No movie with id " + request.getMovieId());
        }
        Movie movie = optionalMovie.get();
        Review review = Review.builder()
                .text(request.getText())
                .movie(movie)
                .build();
        reviewRepository.save(review);
    }

    @Override
    public List<ReviewDto> findByMovieId(Long movieId) {
//        for (int i = 0; i < 999999999; i++) {
//            i++;
//            for (int j = 0; j < 999999999; j++) {
//                j++;
//            }
//        }
        return reviewMapper.mapToReviewDtoList(reviewRepository.findByMovieId(movieId));
    }
}
