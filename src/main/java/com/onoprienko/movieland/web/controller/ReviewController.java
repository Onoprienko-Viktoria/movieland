package com.onoprienko.movieland.web.controller;

import com.onoprienko.movieland.common.request.AddReviewRequest;
import com.onoprienko.movieland.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public void add(@RequestBody AddReviewRequest request) {
        reviewService.add(request);
    }
}
