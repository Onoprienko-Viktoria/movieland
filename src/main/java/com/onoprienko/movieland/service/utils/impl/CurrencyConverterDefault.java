package com.onoprienko.movieland.service.utils.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onoprienko.movieland.common.CurrencyCode;
import com.onoprienko.movieland.service.utils.CurrencyConverter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

@Service
public class CurrencyConverterDefault implements CurrencyConverter {
    private static final String NBU_EXCHANGE_URL = "https://bank.gov.ua/NBU_Exchange/exchange?json";

    @Override
    public Double convertPrice(Double moviePrice, CurrencyCode currencyCode) {
        Double currencyAmountFromNbu = getCurrencyAmountFromNbu(currencyCode);
        return moviePrice * currencyAmountFromNbu;
    }

    @SneakyThrows
    Double getCurrencyAmountFromNbu(CurrencyCode currencyCode) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(NBU_EXCHANGE_URL))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.body());
        if (rootNode.isArray()) {
            for (JsonNode currencyNode : rootNode) {
                if (Objects.equals(currencyNode.get("CurrencyCodeL").asText(), currencyCode.name())) {
                    return currencyNode.get("Amount").asDouble();
                }
            }
        }
        throw new IllegalArgumentException("Can not get currency for " + currencyCode);
    }
}
