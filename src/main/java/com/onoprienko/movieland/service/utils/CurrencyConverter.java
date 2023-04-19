package com.onoprienko.movieland.service.utils;


import com.onoprienko.movieland.common.CurrencyCode;

public interface CurrencyConverter {
    Double convertPrice(Double movies, CurrencyCode currencyCode);
}
