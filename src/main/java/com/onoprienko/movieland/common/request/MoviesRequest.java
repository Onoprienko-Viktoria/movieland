package com.onoprienko.movieland.common.request;

import com.onoprienko.movieland.common.SortDirection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class MoviesRequest {
    private int page;
    private SortDirection ratingDirection;
    private SortDirection priceDirection;
    private long genreId;
}
