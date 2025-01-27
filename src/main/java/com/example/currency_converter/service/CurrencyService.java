package com.example.currency_converter.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CurrencyService {

    @Value("${exchange.api.base.url}")
    private String apiBaseUrl;

    private final RestTemplate restTemplate;

    public CurrencyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, Double> getExchangeRates(String baseCurrency) {
    String url = apiBaseUrl + baseCurrency;
    @SuppressWarnings("unchecked")
    Map<String, Object> response = restTemplate.getForObject(url, Map.class);
    @SuppressWarnings("unchecked")
    Map<String, Object> rates = (Map<String, Object>) response.get("rates");

    // Convert rates to Map<String, Double> to ensure consistent data types
    return rates.entrySet().stream()
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    entry -> ((Number) entry.getValue()).doubleValue() // Explicitly cast to Double
            ));
}


    public double convertCurrency(String from, String to, double amount) {
        Map<String, Double> rates = getExchangeRates(from);
        double exchangeRate = rates.get(to);
        return amount * exchangeRate;
    }
}
