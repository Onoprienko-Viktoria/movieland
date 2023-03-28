package com.onoprienko.movieland.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class MoviesRequest {
    private int page;
    private SortDirectionEnum ratingDirection;
    private SortDirectionEnum priceDirection;
    private long genreId;
}
