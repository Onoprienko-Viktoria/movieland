package com.onoprienko.movieland.common.request;

import com.onoprienko.movieland.common.CurrencyCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class MovieByIdRequest {
    private Long movieId;
    private CurrencyCode currencyCode;
}
