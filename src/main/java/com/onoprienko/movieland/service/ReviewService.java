package com.onoprienko.movieland.service;

import com.onoprienko.movieland.common.request.AddReviewRequest;
import com.onoprienko.movieland.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    void add(AddReviewRequest request);

    List<ReviewDto> findByMovieId(Long movieId);
}
