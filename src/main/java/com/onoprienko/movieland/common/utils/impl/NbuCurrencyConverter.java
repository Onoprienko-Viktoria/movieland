package com.onoprienko.movieland.common.utils.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onoprienko.movieland.common.CurrencyCode;
import com.onoprienko.movieland.common.utils.CurrencyConverter;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@Slf4j
public class NbuCurrencyConverter implements CurrencyConverter {
    private ConcurrentMap<String, Double> currenciesCache;
    @Value("${currency.nbu.url}")
    private String nbuExchangeUrl;


    @Override
    public Double convertPriceFromUAH(Double amount, CurrencyCode currencyCode) {
        Double currencyAmountFromNbu = currenciesCache.get(currencyCode.name());
        return amount * currencyAmountFromNbu;
    }


    @PostConstruct
    @Scheduled(cron = "${currency.nbu.update.time}")
    private void getCurrencyAmountFromNbu() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(nbuExchangeUrl, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);
        ConcurrentHashMap<String, Double> currencies = new ConcurrentHashMap<>();

        if (rootNode.isArray()) {
            for (JsonNode currencyNode : rootNode) {
                String currencyCode = currencyNode.get("CurrencyCodeL").asText();
                double amount = currencyNode.get("Amount").asDouble();
                currencies.put(currencyCode, amount);
            }
        }
        log.info("Update currency cash");
        this.currenciesCache = currencies;
    }
}
