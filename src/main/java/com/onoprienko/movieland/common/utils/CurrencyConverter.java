package com.onoprienko.movieland.common.utils;


import com.onoprienko.movieland.common.CurrencyCode;

public interface CurrencyConverter {
    Double convertPriceFromUAH(Double amount, CurrencyCode currencyCode);
}
